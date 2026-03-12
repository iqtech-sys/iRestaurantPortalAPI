package com.irestaurant.iPortalAPI.controller;

import java.util.List;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import com.irestaurant.iPortalAPI.dto.AuthResponse;
import com.irestaurant.iPortalAPI.dto.BranchComparisonDTO;
import com.irestaurant.iPortalAPI.dto.BranchComparisonRequest;
import com.irestaurant.iPortalAPI.dto.DbRequest;
import com.irestaurant.iPortalAPI.dto.RecentOrderDTO;
import com.irestaurant.iPortalAPI.dto.RecentOrdersRequest;
import com.irestaurant.iPortalAPI.dto.TopItemDTO;
import com.irestaurant.iPortalAPI.dto.TopItemsRequest;
import com.irestaurant.iPortalAPI.security.RequireJwt;
import com.irestaurant.iPortalAPI.service.OrderService;
import com.irestaurant.iPortalAPI.util.JwtUtil;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;

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
    @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/order-branches", description = "Response channel for unique branch IDs"))
    public void getUniqueBranchIds(@Valid @Payload DbRequest request, SimpMessageHeaderAccessor headerAccessor) {
        logger.error("***************** getUniqueBranchIds started *****************");
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
            logger.error("Error retrieving the unique branches: {}", e.getMessage(), e);
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
     * 
     * <pre>
     * {
     *   "branchName": "branch-id-001",
     *   "limit":      20
     * }
     * </pre>
     * 
     * @param request        the STOMP payload containing branchName and limit
     * @param headerAccessor provides the session ID and Authorization header
     */
    @MessageMapping("/order.getRecentOrders")
    @RequireJwt(role = "User")
    @Async(value = "backgroundTaskExecutor")
    @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/recent-orders", description = "Response channel for recent orders"))
    public void getRecentOrders(@Valid @Payload RecentOrdersRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            // Extract email from the JWT "email" claim — same token the aspect already
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));// strip "Bearer "
            //
            List<RecentOrderDTO> recentOrders = orderService.getRecentOrders(email, request.getBranchName(),
                    request.getLimit());
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

    @MessageMapping("/order.getTopItems")
    @RequireJwt(role = "User")
    @Async(value = "backgroundTaskExecutor")
    @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/top-items", description = "Response channel for top items"))
    public void getTopItems(@Valid @Payload TopItemsRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            // Extract email from the JWT "email" claim
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));

            List<TopItemDTO> topItems = orderService.getTopItems(email, request.getBranchName(), request.getStartDate(),
                    request.getEndDate(), request.getTopX());

            messagingTemplate.convertAndSendToUser(sessionId, "/queue/top-items",
                    new AuthResponse<>("1", null, "", null, topItems));

        } catch (Exception e) {
            logger.error("Error retrieving top items for branch '{}': {}",
                    request.getBranchName(), e.getMessage(), e);
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/top-items",
                    new AuthResponse<>("-1", null, "Error retrieving top items", null, null));
        }
    }

    @MessageMapping("/order.getBranchComparison")
    @RequireJwt(role = "User")
    @Async(value = "backgroundTaskExecutor")
    @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/branch-comparison", description = "Response channel for branch comparison"))
    public void getBranchComparison(@Valid @Payload BranchComparisonRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            // Extract email from the JWT "email" claim
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));
            List<BranchComparisonDTO> comparison = orderService.getBranchComparison(email, request.getBranchName(), request.getStartDate(), request.getEndDate());
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/branch-comparison",
                                                   new AuthResponse<>("1", null, "", null, comparison));

        } catch (Exception e) {
            logger.error("Error retrieving branch comparison for branch '{}': {}",
                    request.getBranchName(), e.getMessage(), e);
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/branch-comparison",
                                                   new AuthResponse<>("-1", null, "Error retrieving branch comparison", null, null));
        }
    }
}
