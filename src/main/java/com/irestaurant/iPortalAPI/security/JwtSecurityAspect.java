package com.irestaurant.iPortalAPI.security;

import com.irestaurant.iPortalAPI.dto.AuthResponse;
import com.irestaurant.iPortalAPI.service.UserService;
import com.irestaurant.iPortalAPI.util.JwtUtil;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Aspect to enforce JWT security on methods annotated with @RequireJwt.
 */
@Aspect
@Component
public class JwtSecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(JwtSecurityAspect.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Before("@annotation(requireJwt)")
    public void authorize(JoinPoint joinPoint, RequireJwt requireJwt) {
        StompHeaderAccessor accessor = findAccessor(joinPoint);

        try {
            if (accessor == null) {
                logger.error("No accessor found in method arguments for @RequireJwt");
                throw new AccessDeniedException(
                        "Method secured with @RequireJwt must include a SimpMessageHeaderAccessor parameter.");
            }

            String token = accessor.getFirstNativeHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                throw new AccessDeniedException("JWT token is missing from the Authorization header.");
            }
            String jwt = token.substring(7);

            String username = jwtUtil.extractUsername(jwt);

            UserDetails userDetails = userService.loadUserByUsername(username);

            if (!jwtUtil.validateToken(jwt, userDetails)) {
                throw new AccessDeniedException("Invalid or expired JWT token.");
            }

            // Role Validation
            if (!requireJwt.role().isEmpty()) {
                String requiredRole = requireJwt.role();
                boolean hasRole = userDetails.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equalsIgnoreCase(requiredRole) ||
                                a.getAuthority().equalsIgnoreCase(requiredRole));

                if (!hasRole) {
                    throw new AccessDeniedException(
                            "Access denied: User does not have the required role: " + requiredRole);
                }
            }

            // Authentication context population
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());

            // 1. Set for the current thread execution
            SecurityContextHolder.getContext().setAuthentication(auth);

            // 2. Set in the accessor so Principal param in controller is updated
            if (accessor.isMutable()) {
                accessor.setUser(auth);
            } else {
                logger.debug("Accessor is immutable, skipping accessor.setUser().");
            }

        } catch (Exception e) {
            logger.error("Security validation failed: {}", e.getMessage());
            if (e instanceof AccessDeniedException) {
                String sessionId = accessor.getSessionId();
                if (sessionId != null) {
                    messagingTemplate.convertAndSendToUser(sessionId, "/queue/access-denied",
                            new AuthResponse<>(String.valueOf(HttpStatus.UNAUTHORIZED.value()), null,
                                    "Access Denied: " + e.getMessage(), null, null));
                }
            }
        }
    }

    private StompHeaderAccessor findAccessor(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof SimpMessageHeaderAccessor) {
                // If it's already an accessor, we can wrap it or try to get the original
                // message
                return MessageHeaderAccessor.getAccessor(((SimpMessageHeaderAccessor) arg).getMessageHeaders(),
                        StompHeaderAccessor.class);
            }
        }
        return null;
    }
}
