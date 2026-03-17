package com.irestaurant.iPortalAPI.service;

import com.irestaurant.iPortalAPI.converter.OrderStatusesConverter;
import com.irestaurant.iPortalAPI.dto.RecentOrderDTO;
import com.irestaurant.iPortalAPI.dto.TopItemDTO;
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
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.irestaurant.iPortalAPI.dto.BranchComparisonDTO;
import com.irestaurant.iPortalAPI.dto.BestPerformingBranchDTO;
import com.irestaurant.iPortalAPI.dto.StandardComplianceMetricsDTO;
import com.irestaurant.iPortalAPI.dto.CentralizedMenuPerformanceDTO;
import com.irestaurant.iPortalAPI.dto.MostDeliveriesDTO;
import com.irestaurant.iPortalAPI.objectbox.model.Invoice;
import com.irestaurant.iPortalAPI.objectbox.model.Invoice_;
import com.irestaurant.iPortalAPI.objectbox.model.OrderEntry;
import com.irestaurant.iPortalAPI.objectbox.model.OrderEntry_;
import com.irestaurant.iPortalAPI.objectbox.model.Delivery;
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
                    orderStatusesConverter.convertToEntityAttribute((int) order.getOrderStatus()).name(), // order.getOrderStatus(),
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
        // List<OrderItem> orderItemsInOrderQb = itemQb.build().find(0, topX);
        // List<OrderItem> intersectedOrderItems = orderItemsInOrderQb.stream()
        // .filter(item -> allItems.stream().anyMatch(a -> a.getId() == item.getId()))
        // .collect(Collectors.toList());

        // Get the result properties required from all orders items.
        // Map<String, List<OrderItem>> groupedItems = intersectedOrderItems.stream()
        // .filter(item -> item.getSnapshot_title() != null &&
        // !item.getSnapshot_title().isBlank())
        // .collect(Collectors.groupingBy(OrderItem::getSnapshot_title));
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
            double tax = AccountUtil.calculateTax(revenueSubTotal, taxRate);
            double totalAmount = AccountUtil.calculateTotal(revenueSubTotal, tax);

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

    public List<BranchComparisonDTO> getBranchComparison(String email, String branchName, Date startDate, Date endDate) {
        BoxStore store = SyncManager.init(email);
        Box<Order> orderBox = store.boxFor(Order.class);
        Box<Invoice> invoiceBox = store.boxFor(Invoice.class);
        Box<OrderItem> orderItemBox = store.boxFor(OrderItem.class);
        // Filter Orders
        QueryBuilder<Order> orderQb = orderBox.query();
        if (startDate != null && endDate != null) {
            orderQb = orderQb.between(Order_.createdDate, startDate, endDate);
        }
        if (branchName != null && !branchName.isBlank()) {
            orderQb = orderQb.equal(Order_.branchId, branchName,
                    io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        List<Order> allOrders = orderQb.build().find();

        // Calculate count of Orders per branch
        Map<String, Long> branchOrderCount = allOrders.stream()
                .filter(o -> o.getBranchId() != null && !o.getBranchId().isBlank())
                .collect(Collectors.groupingBy(Order::getBranchId, Collectors.counting()));

        // We also need currency per branch. We can take it from one OrderItem per
        // branch.
        Map<String, String> branchCurrency = new HashMap<>();
        for (Order o : allOrders) {
            String bId = o.getBranchId();
            if (bId != null && !bId.isBlank() && !branchCurrency.containsKey(bId)) {
                // Find first OrderItem to extract currency
                List<OrderItem> items = orderItemBox.query(OrderItem_.orderId.equal(o.getId())).build().find(0, 1);
                if (!items.isEmpty()) {
                    String currency = items.get(0).getSnapshot_currency();
                    if (currency != null && !currency.isBlank()) {
                        branchCurrency.put(bId, currency);
                    }
                }
            }
        }

        // Filter Invoices
        QueryBuilder<Invoice> invoiceQb = invoiceBox.query();
        if (startDate != null && endDate != null) {
            invoiceQb = invoiceQb.between(Invoice_.createdDate, startDate, endDate);
        }
        if (branchName != null && !branchName.isBlank()) {
            invoiceQb = invoiceQb.equal(Invoice_.branchId, branchName,
                    io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        List<Invoice> allInvoices = invoiceQb.build().find();

        Map<String, Double> branchRevenue = new HashMap<>();
        Map<String, Double> branchExpenses = new HashMap<>();

        for (Invoice invoice : allInvoices) {
            String bId = invoice.getBranchId();
            if (bId == null || bId.isBlank())
                continue;

            double amount = invoice.getAmountTo();
            String invNum = invoice.getInvNum() != null ? invoice.getInvNum() : "";
            boolean isExpense = AccountUtil.isAddedInvoice(invNum);

            branchRevenue.merge(bId, amount, Double::sum);
            if (isExpense) {
                branchExpenses.merge(bId, amount, Double::sum);
            }
        }

        Set<String> uniqueBranches = new HashSet<>();
        uniqueBranches.addAll(branchOrderCount.keySet());
        uniqueBranches.addAll(branchRevenue.keySet());
        //
        List<BranchComparisonDTO> result = new ArrayList<>();
        for (String bId : uniqueBranches) {
            double revenue = AccountUtil.round(branchRevenue.getOrDefault(bId, 0.0));
            double expenses = branchExpenses.getOrDefault(bId, 0.0);
            double profit = AccountUtil.round(revenue - expenses);
            String currency = branchCurrency.getOrDefault(bId, "");
            long ordersCount = branchOrderCount.getOrDefault(bId, 0L);

            result.add(new BranchComparisonDTO(bId, revenue, profit, currency, ordersCount));
        }

        return result;
    }

    public List<BestPerformingBranchDTO> getBestPerformingBranch(String email, String branchName, Date startDate, Date endDate) {
        BoxStore store = SyncManager.init(email);
        Box<Order> orderBox = store.boxFor(Order.class);
        Box<Invoice> invoiceBox = store.boxFor(Invoice.class);
        Box<OrderItem> orderItemBox = store.boxFor(OrderItem.class);

        // Filter Orders for current period
        QueryBuilder<Order> orderQb = orderBox.query();
        if (startDate != null && endDate != null) {
            orderQb = orderQb.between(Order_.createdDate, startDate, endDate);
        }
        if (branchName != null && !branchName.isBlank()) {
            orderQb = orderQb.equal(Order_.branchId, branchName, io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        List<Order> currentOrders = orderQb.build().find();

        // Unique Customers per branch (Current Period)
        Map<String, Set<Long>> branchCustomers = new HashMap<>();
        Map<String, String> branchCurrency = new HashMap<>();

        for (Order o : currentOrders) {
            String bId = o.getBranchId();
            if (bId == null || bId.isBlank())
                continue;

            branchCustomers.computeIfAbsent(bId, k -> new HashSet<>());
            long customerId = o.getCustomer().getTargetId();
            if (customerId > 0) {
                branchCustomers.get(bId).add(customerId);
            } else {
                branchCustomers.get(bId).add(-o.getId()); // each walkin is a unique customer for count purpose
            }

            if (!branchCurrency.containsKey(bId)) {
                List<OrderItem> items = orderItemBox.query(OrderItem_.orderId.equal(o.getId())).build().find(0, 1);
                if (!items.isEmpty()) {
                    String currency = items.get(0).getSnapshot_currency();
                    if (currency != null && !currency.isBlank()) {
                        branchCurrency.put(bId, currency);
                    }
                }
            }
        }

        // Current period Invoices
        QueryBuilder<Invoice> invoiceQb = invoiceBox.query();
        if (startDate != null && endDate != null) {
            invoiceQb = invoiceQb.between(Invoice_.createdDate, startDate, endDate);
        }
        if (branchName != null && !branchName.isBlank()) {
            invoiceQb = invoiceQb.equal(Invoice_.branchId, branchName,
                    io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        List<Invoice> currentInvoices = invoiceQb.build().find();

        Map<String, Double> currentRevenue = new HashMap<>();
        Map<String, Double> currentExpenses = new HashMap<>();

        for (Invoice invoice : currentInvoices) {
            String bId = invoice.getBranchId();
            if (bId == null || bId.isBlank())
                continue;

            double amount = invoice.getAmountTo();
            String invNum = invoice.getInvNum() != null ? invoice.getInvNum() : "";
            boolean isExpense = AccountUtil.isAddedInvoice(invNum);

            currentRevenue.merge(bId, amount, Double::sum);
            if (isExpense) {
                currentExpenses.merge(bId, amount, Double::sum);
            }
        }

        // Previous period calc
        Map<String, Double> previousRevenue = new HashMap<>();
        if (startDate != null && endDate != null) {
            long duration = endDate.getTime() - startDate.getTime(); // in milliseconds
            long durationPlusDay = duration + 86400000L;
            Date prevStartDate = new Date(startDate.getTime() - durationPlusDay);
            Date prevEndDate = new Date(startDate.getTime() - 1000L); // 1 sec before

            QueryBuilder<Invoice> prevInvoiceQb = invoiceBox.query().between(Invoice_.createdDate, prevStartDate, prevEndDate);
            if (branchName != null && !branchName.isBlank()) {
                prevInvoiceQb = prevInvoiceQb.equal(Invoice_.branchId, branchName,
                        io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
            }
            List<Invoice> prevInvoices = prevInvoiceQb.build().find();

            for (Invoice invoice : prevInvoices) {
                String bId = invoice.getBranchId();
                if (bId == null || bId.isBlank())
                    continue;

                previousRevenue.merge(bId, invoice.getAmountTo(), Double::sum);
            }
        }

        Set<String> uniqueBranches = new HashSet<>();
        uniqueBranches.addAll(branchCustomers.keySet());
        uniqueBranches.addAll(currentRevenue.keySet());

        List<BestPerformingBranchDTO> result = new ArrayList<>();
        for (String bId : uniqueBranches) {
            double revenue = AccountUtil.round(currentRevenue.getOrDefault(bId, 0.0));
            double expenses = currentExpenses.getOrDefault(bId, 0.0);
            double profit = AccountUtil.round(revenue - expenses);

            long customerCount = 0;
            if (branchCustomers.containsKey(bId)) {
                customerCount = branchCustomers.get(bId).size();
            }

            String currency = branchCurrency.getOrDefault(bId, "");

            double prevRev = previousRevenue.getOrDefault(bId, 0.0);
            double growthRate = 0.0;
            if (prevRev > 0) {
                growthRate = AccountUtil.round(((revenue - prevRev) / prevRev) * 100);
            } else if (prevRev <= 0 && revenue > 0) {
                growthRate = 100.0;
            }

            result.add(new BestPerformingBranchDTO(bId, revenue, profit, currency, customerCount, growthRate));
        }

        // Sort descending by highest profit
        result.sort((b1, b2) -> Double.compare(b2.getProfit(), b1.getProfit()));

        return result;
    }

    public List<StandardComplianceMetricsDTO> getStandardComplianceMetrics(String email, String branchName, Date startDate, Date endDate) {
        BoxStore store = SyncManager.init(email);
        Box<Order> orderBox = store.boxFor(Order.class);

        QueryBuilder<Order> orderQb = orderBox.query();
        if (startDate != null && endDate != null) {
            orderQb = orderQb.between(Order_.createdDate, startDate, endDate);
        }
        if (branchName != null && !branchName.isBlank()) {
            orderQb = orderQb.equal(Order_.branchId, branchName, io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        List<Order> allOrders = orderQb.build().find();

        Map<String, List<Order>> branchOrdersMap = allOrders.stream()
                                                            .filter(o -> o.getBranchId() != null && !o.getBranchId().isBlank())
                                                            .collect(Collectors.groupingBy(Order::getBranchId));

        List<StandardComplianceMetricsDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<Order>> entry : branchOrdersMap.entrySet()) {
            String bId = entry.getKey();
            List<Order> bOrders = entry.getValue();

            long totalPreparationTime = 0;
            long totalDeliveryTime = 0;
            int prepTimeCount = 0;
            int deliveryTimeCount = 0;

            for (Order o : bOrders) {
                // Average orders time (Preparation time mapping, converted appropriately to milliseconds if needed)
                if (o.getPreparationTime() > 0) {
                    totalPreparationTime += o.getPreparationTime();
                    prepTimeCount++;
                }

                if (o.getCreatedDate() != null && o.getDeliveredDate() != null) {
                    long deliveryDuration = o.getDeliveredDate().getTime() - o.getCreatedDate().getTime();
                    if (deliveryDuration > 0) {
                        totalDeliveryTime += deliveryDuration;
                        deliveryTimeCount++;
                    }
                }
            }

            long avgPreparationTime = prepTimeCount > 0 ? (totalPreparationTime / prepTimeCount) : 0;
            long avgDeliveryTime = deliveryTimeCount > 0 ? (totalDeliveryTime / deliveryTimeCount) : 0;

            // Compliance means does the average orders deliveredDate were less or equals to the average orders time
            // Compare millisecond scale or minute scale (Depends on preparationTime storage unit in DB). 
            // Assuming preparationTime is in milliseconds like Date time. If not, multiply by 60000.
            // Adjusting based on standard milliseconds assumption. Adjust if preparationTime is stored in minutes.
            boolean compliance = false;
            
            // Assuming getPreparationTime is in Minutes because it's a typical configuration property
            long avgPreparationTimeMs = avgPreparationTime * 60000L;

            // Alternatively, strictly translating the user's logic: 
            // "average orders deliveredDate were less or equals to the average orders time"
            // Let's compare directly avgDeliveryTime <= avgPreparationTimeMs
            if (avgPreparationTime > 0 && avgDeliveryTime > 0) {
                 if (avgDeliveryTime <= avgPreparationTimeMs) {
                     compliance = true;
                 }
            }

            result.add(new StandardComplianceMetricsDTO(bId, avgPreparationTime, compliance));
        }

        return result;
    }

    public List<CentralizedMenuPerformanceDTO> getCetralizedMenuPerformance(String email, String branchName, Date startDate, Date endDate) {
        BoxStore store = SyncManager.init(email);
        Box<OrderItem> orderItemBox = store.boxFor(OrderItem.class);

        QueryBuilder<OrderItem> itemQb = orderItemBox.query();
        QueryBuilder<Order> orderQb = itemQb.link(OrderItem_.order);

        if (branchName != null && !branchName.isBlank()) {
            orderQb.equal(Order_.branchId, branchName, io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        if (startDate != null && endDate != null) {
            orderQb.between(Order_.createdDate, startDate, endDate);
        }
        
        List<OrderItem> allItems = itemQb.build().find();
        
        // Group by product title
        Map<String, List<OrderItem>> groupedByTitle = allItems.stream()
                                                              .filter(item -> item.getSnapshot_title() != null && !item.getSnapshot_title().isBlank())
                                                              .collect(Collectors.groupingBy(OrderItem::getSnapshot_title));

        List<CentralizedMenuPerformanceDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<OrderItem>> titleEntry : groupedByTitle.entrySet()) {
            String menuItem = titleEntry.getKey();
            List<OrderItem> titleItems = titleEntry.getValue();
            
            Map<String, List<OrderItem>> itemsByBranch = titleItems.stream()
                                                                   .filter(i -> i.getBranchId() != null && !i.getBranchId().isBlank())
                                                                   .collect(Collectors.groupingBy(OrderItem::getBranchId));

            double overallTotalSales = 0.0;
            String bestBranch = "N/A";
            String worstBranch = "N/A";
            double maxSales = -1.0;
            double minSales = Double.MAX_VALUE;

            for (Map.Entry<String, List<OrderItem>> branchEntry : itemsByBranch.entrySet()) {
                 String bId = branchEntry.getKey();
                 
                 double branchSales = calculateSubtotal(branchEntry.getValue());
                 double taxR = branchEntry.getValue().isEmpty() ? 0.0 : branchEntry.getValue().get(0).getSnapshot_taxRate();
                 double bTax = AccountUtil.calculateTax(branchSales, taxR);
                 double bTotal = AccountUtil.calculateTotal(branchSales, bTax);
                 
                 overallTotalSales += bTotal;
                 
                 if (bTotal > maxSales) { maxSales = bTotal; bestBranch = bId; }
                 if (bTotal < minSales) { minSales = bTotal; worstBranch = bId; }
            }
            
            if (minSales == Double.MAX_VALUE) {
                minSales = 0.0;
            }

            result.add(new CentralizedMenuPerformanceDTO(menuItem, overallTotalSales, bestBranch, worstBranch));
        }

        // Sort by total sales descending
        result.sort((a, b) -> Double.compare(b.getTotalSales(), a.getTotalSales()));

        return result;
    }
}
