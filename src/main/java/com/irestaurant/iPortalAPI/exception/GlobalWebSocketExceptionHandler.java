package com.irestaurant.iPortalAPI.exception;

import com.irestaurant.iPortalAPI.dto.AuthResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalWebSocketExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalWebSocketExceptionHandler.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageExceptionHandler(RequestNotPermitted.class)
    public void handleRateLimitException(RequestNotPermitted ex, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        logger.warn("Rate limit exceeded for session {} - Method: {}", sessionId, ex.getMessage());
        
        if (sessionId != null) {
            // Because Stomp mapping destinations are varied, sending an explicit error to a global /queue/error
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/error",
                    new AuthResponse<>("-1", null, "Rate limit exceeded. Please wait and try again later.", null, null));
        }
    }
}
