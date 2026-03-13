package com.irestaurant.iPortalAPI.controller;

import com.irestaurant.iPortalAPI.dto.AuthResponse;
import com.irestaurant.iPortalAPI.dto.PerformanceHeatmapDTO;
import com.irestaurant.iPortalAPI.dto.PerformanceHeatmapRequest;
import com.irestaurant.iPortalAPI.dto.SalesGadgetsDTO;
import com.irestaurant.iPortalAPI.dto.SalesGadgetsRequest;
import com.irestaurant.iPortalAPI.security.RequireJwt;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import com.irestaurant.iPortalAPI.service.AccountService;
import com.irestaurant.iPortalAPI.util.JwtUtil;
import java.util.List;

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
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;

@Controller
public class AccountController {

        private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

        @Autowired
        private AccountService accountService;

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        @MessageMapping("/account.salesGadgets")
        @RequireJwt(role = "User")
        @RateLimiter(name = "account")
        @Async(value = "backgroundTaskExecutor")
        @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/sales-gadgets", description = "Response channel for sales gadgets"))
        public void getSalesGadgets(@Valid @Payload SalesGadgetsRequest request, SimpMessageHeaderAccessor headerAccessor) {
                String sessionId = headerAccessor.getSessionId();
                try {
                        // Extract email from the JWT "email" claim
                        String token = headerAccessor.getFirstNativeHeader("Authorization");
                        String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));

                        double expenses = accountService.getExpenses(email, request.getBranchName(),
                                        request.getStartDate(),
                                        request.getEndDate());
                        double profit = accountService.getProfit(email, request.getBranchName(), request.getStartDate(),
                                        request.getEndDate());
                        double revenue = accountService.getRevenue(email, request.getBranchName(),
                                        request.getStartDate(),
                                        request.getEndDate());

                        SalesGadgetsDTO gadgets = new SalesGadgetsDTO(expenses, profit, revenue);

                        messagingTemplate.convertAndSendToUser(sessionId, "/queue/sales-gadgets",
                                        new AuthResponse<>("1", null, "", null, gadgets));

                } catch (Exception e) {
                        logger.error("Error retrieving sales gadgets for branch '{}': {}",
                                        request.getBranchName(), e.getMessage(), e);
                        messagingTemplate.convertAndSendToUser(sessionId, "/queue/sales-gadgets",
                                        new AuthResponse<>("-1", null, "Error retrieving sales gadgets", null, null));
                }
        }

        @MessageMapping("/account.performanceHeatmap")
        @RequireJwt(role = "User")
        @RateLimiter(name = "account")
        @Async(value = "backgroundTaskExecutor")
        @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/performance-heatmap", description = "Response channel for performance heatmap"))
        public void getPerformanceHeatmap(@Valid @Payload PerformanceHeatmapRequest request, SimpMessageHeaderAccessor headerAccessor) {
                String sessionId = headerAccessor.getSessionId();
                try {
                        // Extract email from the JWT "email" claim
                        String token = headerAccessor.getFirstNativeHeader("Authorization");
                        String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));

                        List<PerformanceHeatmapDTO> heatmapData = accountService.getPerformanceHeatmap(email,
                                        request.getBranchName(), request.getStartDate(), request.getEndDate());

                        messagingTemplate.convertAndSendToUser(sessionId, "/queue/performance-heatmap",
                                        new AuthResponse<>("1", null, "", null, heatmapData));

                } catch (Exception e) {
                        logger.error("Error retrieving performance heatmap for branch '{}': {}",
                                        request.getBranchName(), e.getMessage(), e);
                        messagingTemplate.convertAndSendToUser(sessionId, "/queue/performance-heatmap",
                                        new AuthResponse<>("-1", null, "Error retrieving performance heatmap", null,
                                                        null));
                }
        }
}
