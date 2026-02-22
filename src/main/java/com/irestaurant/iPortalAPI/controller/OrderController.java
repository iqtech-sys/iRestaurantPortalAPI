package com.irestaurant.iPortalAPI.controller;

import java.util.List;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import com.irestaurant.iPortalAPI.dto.AuthResponse;
import com.irestaurant.iPortalAPI.dto.DbRequest;
import com.irestaurant.iPortalAPI.service.OrderService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    /**
     * Handle user UniqueBranchIds via STOMP message.
     * Messages sent to /order.branchIds will be processed here.
     * The response is automatically sent to /user/topic/auth.
     * 
     * @param request
     * @param headerAccessor
    */
    @MessageMapping("/order.branches")
    @Async
    public void getUniqueBranchIds(@Valid @Payload DbRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            List<String> branchIds = orderService.getUniqueBranchIds(request.getEmail());
            messagingTemplate.convertAndSend("/queue/order-branches", 
                    new AuthResponse("1", null, "", null));
        } catch (Exception e) {
            messagingTemplate.convertAndSend("/queue/order-branches",
                    new AuthResponse("-1", null, "Error retrieving branch IDs from orders", null));
        }
    }
}
