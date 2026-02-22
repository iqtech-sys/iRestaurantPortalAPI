package com.irestaurant.iPortalAPI.service;

import com.irestaurant.iPortalAPI.objectbox.model.Order;
import com.irestaurant.iPortalAPI.util.ObjectboxManager;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.reactive.DataObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    /**
     * Retrieves a unique list of all branchId values currently stored in the Order
     * table.
     * 
     * @param email
     * @return List of unique branchIds
     */
    public List<String> getUniqueBranchIds(String email) {
        ObjectboxManager.init(email);
        BoxStore store = ObjectboxManager.get();
        Box<Order> orderBox = store.boxFor(Order.class);
        //
           //         store.subscribe(Order.class)
           //              .observer(new DataObserver<Class<Order>>() {
           //                @Override
           //                public void onData(Class<Order> entityClass) {
           //                    List<Order> latestOrders = orderBox.getAll();
           //                    System.out.println("Sync updated! New count: " + latestOrders.size());
           //                }
           //              });
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
}
