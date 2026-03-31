package com.irestaurant.iPortalAPI.controller;

import com.irestaurant.iPortalAPI.dto.AuthResponse;
import com.irestaurant.iPortalAPI.dto.SalesByCategoryDTO;
import com.irestaurant.iPortalAPI.dto.SalesByCategoryRequest;
import com.irestaurant.iPortalAPI.security.RequireJwt;
import com.irestaurant.iPortalAPI.service.CategoryService;
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
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/category.salesByCategory")
    @RequireJwt(role = "User")
    @RateLimiter(name = "orderEntry")
    @Async(value = "backgroundTaskExecutor")
    @AsyncPublisher(operation = @AsyncOperation(channelName = "/user/queue/sales-by-category", description = "Response channel for sales by category"))
    public void getSalesByCategory(@Valid @Payload SalesByCategoryRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            String email = jwtUtil.extractEmail(jwtUtil.pureJWT(token));

            List<SalesByCategoryDTO> result = categoryService.getSalesByCategory(
                    email,
                    request.getBranchName(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getTopX()
            );

            messagingTemplate.convertAndSendToUser(
                    sessionId,
                    "/queue/sales-by-category",
                    new AuthResponse<>("1", null, "", null, result)
            );

        } catch (Exception e) {
            logger.error("Error retrieving sales by category for branch '{}': {}", request.getBranchName(), e.getMessage(), e);
            messagingTemplate.convertAndSendToUser(
                    sessionId,
                    "/queue/sales-by-category",
                    new AuthResponse<>("-1", null, "Error retrieving sales by category", null, null)
            );
        }
    }
}
