package com.irestaurant.iPortalAPI.controller;

import java.util.List;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import com.irestaurant.iPortalAPI.dto.AuthResponse;
import com.irestaurant.iPortalAPI.dto.DbRequest;
import com.irestaurant.iPortalAPI.dto.RecentOrderDTO;
import com.irestaurant.iPortalAPI.dto.RecentOrdersRequest;
import com.irestaurant.iPortalAPI.security.RequireJwt;
import com.irestaurant.iPortalAPI.service.OrderService;
import com.irestaurant.iPortalAPI.util.JwtUtil;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

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
    @RequireJwt(role = "User")
    @Async(value = "backgroundTaskExecutor")
    public void getUniqueBranchIds(@Valid @Payload DbRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            // Extract email from the JWT "email" claim — same token the aspect already
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));// strip "Bearer "
            //
            List<String> branchIds = orderService.getUniqueBranchIds(email);
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/order-branches",
                                                   new AuthResponse<List<String>>("1", null, "", null, branchIds));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/order-branches",
                                                   new AuthResponse("-1", null, "Error retrieving branch IDs from orders", null, null));
        }
    }

    /**
     * Retrieves a paginated list of recent orders for a given branch via STOMP.
     * <p>
     * Client sends to: {@code /app/orders.getRecent}
     * </p>
     * <p>
     * Response delivered to: {@code /user/queue/recent-orders}
     * </p>
     * <p>
     * Request payload ({@link RecentOrdersRequest}):
     * </p>
     * <pre>
     * {
     *   "branchName": "branch-id-001",
     *   "limit":      20
     * }
     * </pre>
     * @param request the STOMP payload containing branchName and limit
     * @param headerAccessor provides the session ID and Authorization header
     */
    @MessageMapping("/order.getRecentOrders")
    @RequireJwt(role = "User")
    @Async(value = "backgroundTaskExecutor")
    public void getRecentOrders(@Valid @Payload RecentOrdersRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            // Extract email from the JWT "email" claim — same token the aspect already
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));// strip "Bearer "
            //
            List<RecentOrderDTO> recentOrders = orderService.getRecentOrders(email, request.getBranchName(), request.getLimit());
            //
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/recent-orders",
                                                   new AuthResponse<>("1", null, "", null, recentOrders));

        } catch (Exception e) {
            logger.error("Error retrieving recent orders for branch '{}': {}",
                         request.getBranchName(), e.getMessage(), e);

            messagingTemplate.convertAndSendToUser(sessionId, "/queue/recent-orders",
                                                   new AuthResponse<>("-1", null, "Error retrieving recent orders", null, null));
        }
    }
}
