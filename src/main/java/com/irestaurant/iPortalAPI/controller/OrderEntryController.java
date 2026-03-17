package com.irestaurant.iPortalAPI.controller;

import com.irestaurant.iPortalAPI.dto.AuthResponse;
import com.irestaurant.iPortalAPI.dto.MostDeliveriesDTO;
import com.irestaurant.iPortalAPI.dto.MostDeliveriesRequest;
import com.irestaurant.iPortalAPI.dto.TimeConsumedOrderDTO;
import com.irestaurant.iPortalAPI.dto.TopPerformingWaiterDTO;
import com.irestaurant.iPortalAPI.dto.TopPerformingWaitersRequest;
import com.irestaurant.iPortalAPI.dto.TopTimeConsumedRequest;
import com.irestaurant.iPortalAPI.security.RequireJwt;
import com.irestaurant.iPortalAPI.service.OrderEntryService;
import com.irestaurant.iPortalAPI.util.JwtUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderEntryController {

    private static final Logger logger = LoggerFactory.getLogger(OrderEntryController.class);

    @Autowired
    private OrderEntryService orderEntryService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/orderEntry.topMostDeliveries")
    @RequireJwt(role = "User")
    @RateLimiter(name = "orderEntry")
    @Async(value = "backgroundTaskExecutor")
    @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/top-most-deliveries", description = "Response channel for top most deliveries"))
    public void getTopMostDeliveries(@Valid @Payload MostDeliveriesRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));
            
            List<MostDeliveriesDTO> result = orderEntryService.getTopMostDeliveries(email, request.getBranchName(), request.getStartDate(), request.getEndDate(), request.getTopX());
            
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/top-most-deliveries", new AuthResponse<>("1", null, "", null, result));

        } catch (Exception e) {
            logger.error("Error retrieving top most deliveries for branch '{}': {}", request.getBranchName(), e.getMessage(), e);
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/top-most-deliveries",
                                                   new AuthResponse<>("-1", null, "Error retrieving top most deliveries", null, null));
        }
    }
    
    @MessageMapping("/orderEntry.topPerformingWaiters")
    @RequireJwt(role = "User")
    @RateLimiter(name = "orderEntry")
    @Async(value = "backgroundTaskExecutor")
    @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/top-performing-waiters", description = "Response channel for top performing waiters"))
    public void getTopPerformingWaiters(@Valid @Payload TopPerformingWaitersRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));

            List<TopPerformingWaiterDTO> result = orderEntryService.getTopPerformingWaiters(email, request.getBranchName(), request.getStartDate(), request.getEndDate(), request.getTopX());

            messagingTemplate.convertAndSendToUser(sessionId, "/queue/top-performing-waiters", new AuthResponse<>("1", null, "", null, result));

        } catch (Exception e) {
            logger.error("Error retrieving top performing waiters for branch '{}': {}", request.getBranchName(), e.getMessage(), e);
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/top-performing-waiters",
                    new AuthResponse<>("-1", null, "Error retrieving top performing waiters", null, null));
        }
    }
    
    @MessageMapping("/orderEntry.topMostTimeConsumedOrders")
    @RequireJwt(role = "User")
    @RateLimiter(name = "orderEntry")
    @Async(value = "backgroundTaskExecutor")
    @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/top-most-time-consumed-orders", description = "Response channel for top most time consumed orders"))
    public void getTopMostTimeConsumedOrders(@Valid @Payload TopTimeConsumedRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));

            List<TimeConsumedOrderDTO> result = orderEntryService.getTopMostTimeConsumedOrders(
                    email, request.getBranchName(), request.getStartDate(), request.getEndDate(), request.getTopX());

            messagingTemplate.convertAndSendToUser(sessionId, "/queue/top-most-time-consumed-orders",
                    new AuthResponse<>("1", null, "", null, result));

        } catch (Exception e) {
            logger.error("Error retrieving top most time consumed orders for branch '{}': {}", request.getBranchName(), e.getMessage(), e);
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/top-most-time-consumed-orders",
                    new AuthResponse<>("-1", null, "Error retrieving top most time consumed orders", null, null));
        }
    }
}
