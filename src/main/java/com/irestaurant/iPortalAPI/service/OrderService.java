package com.irestaurant.iPortalAPI.service;

import com.irestaurant.iPortalAPI.converter.OrderStatusesConverter;
import com.irestaurant.iPortalAPI.dto.RecentOrderDTO;
import com.irestaurant.iPortalAPI.dto.TopItemDTO;
//import com.irestaurant.iPortalAPI.enumerators.OrderStatuses;
import com.irestaurant.iPortalAPI.objectbox.model.Category;
import com.irestaurant.iPortalAPI.objectbox.model.Order;
import com.irestaurant.iPortalAPI.objectbox.model.Order_;
import com.irestaurant.iPortalAPI.objectbox.model.OrderItem;
import com.irestaurant.iPortalAPI.objectbox.model.Customer;
import com.irestaurant.iPortalAPI.objectbox.model.OrderItem_;
import com.irestaurant.iPortalAPI.objectbox.model.Product;
import com.irestaurant.iPortalAPI.util.SyncManager;
import com.irestaurant.iPortalAPI.util.AccountUtil;
import io.objectbox.Box;
import io.objectbox.BoxStore;
//import io.objectbox.query.OrderFlags;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderService {
    
    @Autowired
    OrderStatusesConverter orderStatusesConverter;

    public double calculateSubtotal(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            return 0.0;
        }

        double subtotal = 0.0;
        for (OrderItem item : orderItems) {
            // (snapshot_price - snapshot_discount) × snapshot_quantity
            // mirrors Flutter: (item.product.target!.price - item.discount) × item.quantity
            double lineTotal = (item.getSnapshot_price() - item.getSnapshot_discount()) * item.getSnapshot_quantity();
            subtotal += lineTotal;
        }

        return AccountUtil.round(subtotal);
    }

    public List<String> getUniqueBranchIds(String email) {
        BoxStore store = SyncManager.init(email);
        Box<Order> orderBox = store.boxFor(Order.class);
        // Get all orders and extract unique branchIds
        List<Order> allOrders = orderBox.getAll();
        List<String> uniqueBranchIds = new ArrayList<>();
        //
        for (Order order : allOrders) {
            String branchId = order.getBranchId();
            if (branchId != null && !uniqueBranchIds.contains(branchId)) {
                uniqueBranchIds.add(branchId);
            }
        }
        return uniqueBranchIds;
    }

    public List<RecentOrderDTO> getRecentOrders(String email, String branchName, int limit) {
        BoxStore store = SyncManager.init(email);
        Box<Order> orderBox = store.boxFor(Order.class);
        Box<Customer> customerBox = store.boxFor(Customer.class); // direct FK lookup
        Box<OrderItem> orderItemBox = store.boxFor(OrderItem.class); // direct FK lookup
        //
        boolean filterByBranch = branchName != null && !branchName.isBlank();
        // Build query: optionally filter by branchId, always sort newest first,
        // paginate
        Query<Order> query = (filterByBranch ? orderBox.query(Order_.branchId.equal(branchName)) // filter: specific
                : orderBox.query()) // no filter: all branches
                .orderDesc(Order_.createdDate) // sort: newest first
                .build();
        List<Order> orders = query.find(0, limit); // offset=0, count=limit
        List<RecentOrderDTO> result = new ArrayList<>(orders.size());
        //
        for (Order order : orders) {
            // ── Resolve customer name via direct Box lookup ────────────────
            // We do NOT use order.getCustomer().getTarget() because ToOne.getTarget()
            // relies on the ObjectBox-injected __boxStore field. If code generation
            // hasn't run that injection, it throws NoSuchFieldException: __boxStore.
            // Instead we read the FK id and fetch directly from customerBox.
            long customerId = order.getCustomer().getTargetId();
            Customer customer = (customerId > 0) ? customerBox.get(customerId) : null;
            String customerName = (customer != null) ? customer.getName() : "";
            // ── Fetch order items via direct Box query ──────────────────────────
            // We do NOT use order.getOrderItems() because ToMany.getListFactory()
            // also relies on the __boxStore injection — same NoSuchFieldException risk.
            // Instead, query OrderItem by its orderId FK directly.
            Query<OrderItem> itemQuery = orderItemBox.query(OrderItem_.orderId.equal(order.getId())).build();
            List<OrderItem> orderItems = itemQuery.find();
            // ── Compute total amount from order items ──────────────────────
            double subTotal = calculateSubtotal(orderItems);
            // taxRate: read snapshot_taxRate from the first item (same logic as //
            // getTaxRate())
            double taxRate = orderItems.isEmpty() ? 0.0 : orderItems.get(0).getSnapshot_taxRate();
            double tax = AccountUtil.calculateTax(subTotal, taxRate);
            double totalAmount = AccountUtil.calculateTotal(subTotal, tax);
            //
            result.add(new RecentOrderDTO(order.getId(),
                                          order.getOrderNumber(),
                                          order.getBranchId(),
                                          customerName, totalAmount,
                                          orderStatusesConverter.convertToEntityAttribute((int)order.getOrderStatus()).name(),// order.getOrderStatus(),
                                          order.getCreatedDate()));
        }
        //
        return result;

    }

    public List<TopItemDTO> getTopItems(String email, String branch, Date startDate, Date endDate, int topX) {
        BoxStore store = SyncManager.init(email);
        // Box<Order> orderBox = store.boxFor(Order.class);
        Box<OrderItem> orderItemBox = store.boxFor(OrderItem.class);
        Box<Product> productBox = store.boxFor(Product.class);
        Box<Category> categoryBox = store.boxFor(Category.class);
        //
        // Extract filtered items based on linked matched Order conditions directly in
        // DB!
        QueryBuilder<OrderItem> itemQb = orderItemBox.query().order(OrderItem_.quantity, 1);
        QueryBuilder<Order> orderQb = itemQb.link(OrderItem_.order);

        if (branch != null && !branch.isBlank()) {
            orderQb.equal(Order_.branchId, branch, io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        if (startDate != null && endDate != null) {
            orderQb.between(Order_.createdDate, startDate, endDate);
        }
        //
        List<OrderItem> allItems = itemQb.build().find(0, topX);
        Map<String, List<OrderItem>> groupedItems = allItems.stream()
                                                            .filter(item -> item.getSnapshot_title() != null && !item.getSnapshot_title().isBlank())
                                                            .collect(Collectors.groupingBy(OrderItem::getSnapshot_title));
        //
        //   List<OrderItem> orderItemsInOrderQb = itemQb.build().find(0, topX);
        //   List<OrderItem> intersectedOrderItems = orderItemsInOrderQb.stream()
        //                                                              .filter(item -> allItems.stream().anyMatch(a -> a.getId() == item.getId()))
        //                                                              .collect(Collectors.toList());

//        Get the result properties required from all orders items.
//        Map<String, List<OrderItem>> groupedItems = intersectedOrderItems.stream()
//                                                                         .filter(item -> item.getSnapshot_title() != null && !item.getSnapshot_title().isBlank())
//                                                                         .collect(Collectors.groupingBy(OrderItem::getSnapshot_title));
//
        List<TopItemDTO> result = groupedItems.entrySet().stream().map(entry -> {
            String name = entry.getKey();
            List<OrderItem> items = entry.getValue();

            // Sum quantity sold
            long qtySold = items.stream().mapToLong(OrderItem::getSnapshot_quantity).sum();

            // Calculate total revenue (price * quantity - discounts), using the existing
            // written function
            double taxRate = items.isEmpty() ? 0.0 : items.get(0).getSnapshot_taxRate();
            double revenueSubTotal = calculateSubtotal(items);
            double tax =  AccountUtil.calculateTax(revenueSubTotal, taxRate);
            double totalAmount =  AccountUtil.calculateTotal(revenueSubTotal, tax);

            String categoryName = "N/A";
            double price = 0.0;

            // Get price, category.
            if (!items.isEmpty()) {
                OrderItem firstItem = items.get(0);
                price = firstItem.getSnapshot_price(); // Reference price from first item

                // Read Category through Product via direct Box logic to avoid ToOne lazy
                // loading issues
                long productId = firstItem.getProduct().getTargetId();
                if (productId > 0) {
                    Product product = productBox.get(productId);
                    if (product != null) {
                        long categoryId = product.getCategory().getTargetId();
                        if (categoryId > 0) {
                            Category category = categoryBox.get(categoryId);
                            if (category != null && category.getTitle() != null) {
                                categoryName = category.getTitle();
                            }
                        }
                    }
                }
            }

            return new TopItemDTO(name, categoryName, price, qtySold, revenueSubTotal, totalAmount);
        })
                .sorted((a, b) -> Long.compare(b.getQtySold(), a.getQtySold()))
                .limit(topX)
                .collect(Collectors.toList());

        return result;
    }
}
