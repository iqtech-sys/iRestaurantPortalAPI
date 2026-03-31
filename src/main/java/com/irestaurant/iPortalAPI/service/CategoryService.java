package com.irestaurant.iPortalAPI.service;

import com.irestaurant.iPortalAPI.dto.SalesByCategoryDTO;
import com.irestaurant.iPortalAPI.objectbox.model.Category;
import com.irestaurant.iPortalAPI.objectbox.model.OrderItem;
import com.irestaurant.iPortalAPI.objectbox.model.OrderItem_;
import com.irestaurant.iPortalAPI.objectbox.model.Order;
import com.irestaurant.iPortalAPI.objectbox.model.Product;
import com.irestaurant.iPortalAPI.util.SyncManager;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder.StringOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    /**
     * Returns sales grouped by product category, sorted by revenue descending, limited to topX entries.
     * Each entry contains: category name, units sold, revenue, and percent of total sales.
     *
     * @param email      authenticated user email (used to resolve the BoxStore)
     * @param branchName optional branch filter (null/blank = all branches)
     * @param startDate  optional start of date range (applied on OrderItem.branchId via snapshot)
     * @param endDate    optional end of date range
     * @param topX       number of top categories to return
     * @return 
     */
    public List<SalesByCategoryDTO> getSalesByCategory(String email, String branchName, Date startDate, Date endDate, int topX) {
        BoxStore store = SyncManager.init(email);
        Box<OrderItem> orderItemBox = store.boxFor(OrderItem.class);
        Box<Order> orderBox = store.boxFor(Order.class);
        Box<Product> productBox = store.boxFor(Product.class);
        Box<Category> categoryBox = store.boxFor(Category.class);

        // Build the OrderItem query
        io.objectbox.query.QueryBuilder<OrderItem> qb = orderItemBox.query();

        if (branchName != null && !branchName.isBlank()) {
            qb = qb.equal(OrderItem_.branchId, branchName, StringOrder.CASE_INSENSITIVE);
        }

        List<OrderItem> items = qb.build().find();

        // Optionally filter by date range using the linked Order's createdDate
        if (startDate != null && endDate != null) {
            final Date start = startDate;
            final Date end   = endDate;
            items = items.stream()
                         .filter(item -> {
                             // Filter via the linked Order's createdDate
                             long orderId = item.getOrder().getTargetId();
                             if (orderId > 0) {
                                 Order order = orderBox.get(orderId);
                                 if (order != null) {
                                     Date orderDate = order.getCreatedDate();
                                     return orderDate != null
                                            && !orderDate.before(start)
                                            && !orderDate.after(end);
                                 }
                             }
                             return false;
                         })
                         .collect(Collectors.toList());
        }

        // Accumulate per-category stats
        // Key: category title, Value: [unitsSold, revenue, cogs]
        Map<String, long[]>   unitMap    = new HashMap<>();
        Map<String, double[]> revenueMap = new HashMap<>();
        Map<String, double[]> cogsMap    = new HashMap<>();

        for (OrderItem item : items) {
            // Resolve category via Product → Category relation
            String categoryName = "Uncategorized";
            long productId = item.getProduct().getTargetId();
            if (productId > 0) {
                Product product = productBox.get(productId);
                if (product != null) {
                    long categoryId = product.getCategory().getTargetId();
                    if (categoryId > 0) {
                        Category cat = categoryBox.get(categoryId);
                        if (cat != null && cat.getTitle() != null && !cat.getTitle().isBlank()) {
                            categoryName = cat.getTitle();
                        }
                    }
                }
            }

            long   qty        = item.getSnapshot_quantity() > 0 ? item.getSnapshot_quantity() : item.getQuantity();
            double price       = item.getSnapshot_price();
            double discount    = item.getSnapshot_discount(); // treated as an amount
            double costPrice   = item.getSnapshot_costPrice(); // COGS per unit (snapshotted at order time)
            double lineRevenue = (price - discount) * qty;
            double lineCogs    = costPrice * qty;

            unitMap.computeIfAbsent(categoryName, k -> new long[]{0})[0]     += qty;
            revenueMap.computeIfAbsent(categoryName, k -> new double[]{0})[0] += lineRevenue;
            cogsMap.computeIfAbsent(categoryName, k -> new double[]{0})[0]    += lineCogs;
        }

        // Calculate grand total revenue for percentage
        double totalRevenue = revenueMap.values().stream()
                                        .mapToDouble(arr -> arr[0])
                                        .sum();

        // Build result list, sort by revenue descending, limit to topX
        List<SalesByCategoryDTO> result = new ArrayList<>();
        for (String cat : unitMap.keySet()) {
            long   units   = unitMap.get(cat)[0];
            double revenue = revenueMap.get(cat)[0];
            double cogs    = cogsMap.getOrDefault(cat, new double[]{0})[0];
            double percent      = (totalRevenue > 0) ? (revenue / totalRevenue) * 100.0 : 0.0;
            double grossMargin  = (revenue > 0)      ? ((revenue - cogs) / revenue) * 100.0 : 0.0;
            result.add(new SalesByCategoryDTO(
                    cat,
                    units,
                    revenue,
                    Math.round(percent * 100.0) / 100.0,
                    Math.round(grossMargin * 100.0) / 100.0
            ));
        }

        result.sort((a, b) -> Double.compare(b.getRevenue(), a.getRevenue()));

        return result.stream().limit(topX).collect(Collectors.toList());
    }
}
