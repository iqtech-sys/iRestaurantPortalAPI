package com.irestaurant.iPortalAPI.service;

import com.irestaurant.iPortalAPI.dto.MostDeliveriesDTO;
import com.irestaurant.iPortalAPI.dto.TimeConsumedOrderDTO;
import com.irestaurant.iPortalAPI.dto.TimeConsumedOrderItemDTO;
import com.irestaurant.iPortalAPI.dto.TopPerformingWaiterDTO;
import com.irestaurant.iPortalAPI.objectbox.model.Delivery;
import com.irestaurant.iPortalAPI.objectbox.model.Order;
import com.irestaurant.iPortalAPI.objectbox.model.Order_;
import com.irestaurant.iPortalAPI.objectbox.model.OrderEntry;
import com.irestaurant.iPortalAPI.objectbox.model.OrderEntry_;
import com.irestaurant.iPortalAPI.objectbox.model.OrderItem;
import com.irestaurant.iPortalAPI.objectbox.model.OrderItem_;
import com.irestaurant.iPortalAPI.objectbox.model.Waiter;
import com.irestaurant.iPortalAPI.util.SyncManager;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;
import io.objectbox.query.QueryBuilder.StringOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderEntryService {

    public List<MostDeliveriesDTO> getTopMostDeliveries(String email, String branchName, Date startDate, Date endDate, int topX) {
        BoxStore store = SyncManager.init(email);
        Box<OrderEntry> orderEntryBox = store.boxFor(OrderEntry.class);
        Box<Delivery> deliveryBox = store.boxFor(Delivery.class);

        QueryBuilder<OrderEntry> qb = orderEntryBox.query();
        if (branchName != null && !branchName.isBlank()) {
            qb = qb.equal(OrderEntry_.branchId, branchName, StringOrder.CASE_INSENSITIVE);
        }
        if (startDate != null && endDate != null) {
            qb = qb.between(OrderEntry_.dateTime, startDate, endDate);
        }

        List<OrderEntry> allEntries = qb.build().find();

        List<Map.Entry<Long, Long>> counts = allEntries.stream()
                                                       // 1. Filter valid entries
                                                       .filter(oe -> oe.getDelivery() != null && oe.getDelivery().getTargetId() > 0)
                                                       // 2. Group and count into a Map<TargetId, Count>
                                                       .collect(Collectors.groupingBy(oe -> oe.getDelivery().getTargetId(), Collectors.counting()))
                                                       // 3. Stream the Map entries
                                                       .entrySet().stream()
                                                       // 4. Sort by value (the count) in descending order
                                                       .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                                                       // 5. Limit to topX
                                                       .limit(topX)
                                                       // 6. Collect to a List
                                                       .collect(Collectors.toList());

        List<MostDeliveriesDTO> result = new ArrayList<>();
        //
        for(int i = 0; i < counts.size(); i++) {
            Map.Entry<Long, Long> entry = counts.get(i);
            long deliveryId = entry.getKey();
            long count = entry.getValue();

            Delivery delivery = deliveryBox.get(deliveryId);
            String deliveryName = "Unknown";
            if (delivery != null && delivery.getName() != null && !delivery.getName().isBlank()) {
                deliveryName = delivery.getName();
            }

            result.add(new MostDeliveriesDTO(deliveryName, count));
        }
        // Sort by count descending and limit to topX
        result.sort((a, b) -> Long.compare(b.getNumberOfOrders(), a.getNumberOfOrders()));
        //
        return result.stream().limit(topX).collect(Collectors.toList());
    }
    
    public List<TopPerformingWaiterDTO> getTopPerformingWaiters(String email, String branchName, Date startDate, Date endDate, int topX) {
        BoxStore store = SyncManager.init(email);
        Box<OrderEntry> orderEntryBox = store.boxFor(OrderEntry.class);
        Box<Waiter> waiterBox = store.boxFor(Waiter.class);

        QueryBuilder<OrderEntry> qb = orderEntryBox.query();
        if (branchName != null && !branchName.isBlank()) {
            qb = qb.equal(OrderEntry_.branchId, branchName, StringOrder.CASE_INSENSITIVE);
        }
        if (startDate != null && endDate != null) {
            qb = qb.between(OrderEntry_.dateTime, startDate, endDate);
        }

        List<OrderEntry> allEntries = qb.build().find();

        List<Map.Entry<Long, Long>> counts = allEntries.stream()
                                                       // 1. Filter valid entries (must have a linked waiter)
                                                       .filter(oe -> oe.getWaiter() != null && oe.getWaiter().getTargetId() > 0)
                                                       // 2. Group and count by waiterId
                                                       .collect(Collectors.groupingBy(oe -> oe.getWaiter().getTargetId(), Collectors.counting()))
                                                       // 3. Stream Map entries
                                                       .entrySet().stream()
                                                       // 4. Sort by count descending
                                                       .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                                                       // 5. Limit to topX
                                                       .limit(topX)
                                                       // 6. Collect
                                                       .collect(Collectors.toList());

        List<TopPerformingWaiterDTO> result = new ArrayList<>();
        for (int i = 0; i < counts.size(); i++) {
            Map.Entry<Long, Long> entry = counts.get(i);
            long waiterId = entry.getKey();
            long count = entry.getValue();

            Waiter waiter = waiterBox.get(waiterId);
            String waiterName = "Unknown";
            if (waiter != null && waiter.getName() != null && !waiter.getName().isBlank()) {
                waiterName = waiter.getName();
            }

            result.add(new TopPerformingWaiterDTO(waiterName, count));
        }
        return result;
    }
    
    public List<TimeConsumedOrderDTO> getTopMostTimeConsumedOrders(String email, String branchName, Date startDate, Date endDate, int topX) {
        BoxStore store = SyncManager.init(email);
        Box<Order> orderBox = store.boxFor(Order.class);
        Box<OrderItem> orderItemBox = store.boxFor(OrderItem.class);

        // Build query — only include orders that have both createdDate and deliveredDate
        QueryBuilder<Order> qb = orderBox.query().notNull(Order_.deliveredDate).notNull(Order_.createdDate);

        if (branchName != null && !branchName.isBlank()) {
            qb = qb.equal(Order_.branchId, branchName, StringOrder.CASE_INSENSITIVE);
        }
        if (startDate != null && endDate != null) {
            qb = qb.between(Order_.createdDate, startDate, endDate);
        }

        List<Order> orders = qb.build().find();

        // Compute time consumed per order, sort descending, limit to topX
        List<TimeConsumedOrderDTO> result = orders.stream()
                                                  .filter(o -> o.getDeliveredDate() != null && o.getCreatedDate() != null)
                                                  .map(o -> {
                                                        long timeConsumedMs = o.getDeliveredDate().getTime() - o.getCreatedDate().getTime();

                                                        // Resolve order items via direct Box query (avoids ToMany lazy-load issues)
                                                        List<OrderItem> items = orderItemBox
                                                                .query(OrderItem_.orderId.equal(o.getId()))
                                                                .build().find();

                                                        List<TimeConsumedOrderItemDTO> itemDTOs = new ArrayList<>();
                                                        for (OrderItem item : items) {
                                                            itemDTOs.add(new TimeConsumedOrderItemDTO(
                                                                    item.getId(),
                                                                    item.getSnapshot_title(),
                                                                    item.getSnapshot_price(),
                                                                    item.getSnapshot_quantity(),
                                                                    item.getSnapshot_discount(),
                                                                    item.getSnapshot_currency()
                                                            ));
                                                        }

                                                        return new TimeConsumedOrderDTO(o.getId(), o.getOrderNumber(), timeConsumedMs, itemDTOs);
                                                  })
                                                  .sorted((a, b) -> Long.compare(b.getTimeConsumedMs(), a.getTimeConsumedMs()))
                                                  .limit(topX)
                                                  .collect(Collectors.toList());

        return result;
    }
}
