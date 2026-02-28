package com.irestaurant.iPortalAPI.controller;

import com.irestaurant.iPortalAPI.dto.AuthResponse;
import com.irestaurant.iPortalAPI.dto.ForgotPasswordRequest;
import com.irestaurant.iPortalAPI.dto.LoginRequest;
import com.irestaurant.iPortalAPI.dto.RegisterRequest;
import com.irestaurant.iPortalAPI.dto.ResetPasswordRequest;
import com.irestaurant.iPortalAPI.exception.InvalidCredentials_1101;
import com.irestaurant.iPortalAPI.exception.InvalidToken_1201;
import com.irestaurant.iPortalAPI.exception.TokenExpired_1301;
import com.irestaurant.iPortalAPI.exception.UserFoundException_1001;
import com.irestaurant.iPortalAPI.model.DbRole;
import com.irestaurant.iPortalAPI.model.DbUser;
import com.irestaurant.iPortalAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.irestaurant.iPortalAPI.security.RequireJwt;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;

/**
 * Controller for handling authentication via STOMP/WebSocket messages.
 */
@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    /**
     * Handle user registration via STOMP message.
     * Messages sent to /app/auth.register will be processed here.
     * The response is automatically sent to /user/queue/auth.
     * 
     * @param request
     * @param headerAccessor
     */
    @MessageMapping("/auth.register")
    @RequireJwt(role = "User")
    @Async(value = "backgroundTaskExecutor")
    public void register(@Valid @Payload RegisterRequest request, SimpMessageHeaderAccessor headerAccessor,
            Principal principal) {
        String sessionId = headerAccessor.getSessionId();
        logger.info("Secured registration by user: {}", principal != null ? principal.getName() : "anonymous");
        try {
            DbUser user = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword()).get();
            Set<String> roleNames = user.getRoles().stream()
                                                   .map(DbRole::getName)
                                                   .collect(Collectors.toSet());
            messagingTemplate.convertAndSend("/queue/register",
                    new AuthResponse("1", null, "Registration successful", roleNames, null));
        } catch (Exception e) {
            int code = -1;
            if (e.getCause() instanceof UserFoundException_1001) {
                code = ((UserFoundException_1001) e.getCause()).getCode();
            }
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/register",
                    new AuthResponse("-1", null, "Error: " + e.getMessage(), null, null));
        }
    }

    /**
     * Handle user login via STOMP message.
     * Messages sent to /app/auth.login will be processed here.
     * The response is automatically sent to /user/queue/auth.
     * 
     * @param request
     * @param headerAccessor
     */
    @MessageMapping("/auth.login")
    @Async(value = "backgroundTaskExecutor")
    public void login(@Valid @Payload LoginRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            String token = userService.loginUser(request.getEmail(), request.getPassword()).get();
            var authorities = userService.loadUserByEmail(request.getEmail())
                    .getAuthorities();
            Set<String> roleNames = authorities.stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.toSet());
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/login", // "/topic/auth",
                    new AuthResponse("1", token, "Login successful", roleNames, null));

        } catch (Exception e) {
            int code = -1;
            if (e.getCause() instanceof InvalidCredentials_1101) {
                code = ((InvalidCredentials_1101) e.getCause()).getCode();
            }
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/login", // "/topic/auth",
                    new AuthResponse(String.valueOf(code), null, "Error: " + e.getMessage(), null, null));
        }
    }

    /**
     * Handle forgot password request via STOMP message.
     * Messages sent to /app/auth.forgot-password will be processed here.
     * 
     * @param request
     * @param headerAccessor
     */
    @MessageMapping("/auth.forgot-password")
    @Async(value = "backgroundTaskExecutor")
    public void forgotPassword(@Valid @Payload ForgotPasswordRequest request,
            SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            userService.processForgotPassword(request.getEmail(), request.getLanguage());
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/forgot-password", // "/topic/auth",
                    new AuthResponse("1", null, "Reset link sent if email exists.", null, null));
        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/forgot-password", // "/topic/auth",
                    new AuthResponse("-1", null, "Error: " + e.getMessage(), null, null));
        }
    }

    /**
     * Handle reset password request via STOMP message.
     * Messages sent to /app/auth.reset-password will be processed here.
     * 
     * @param request
     * @param headerAccessor
     */
    @MessageMapping("/auth.reset-password")
    @Async(value = "backgroundTaskExecutor")
    public void resetPassword(@Valid @Payload ResetPasswordRequest request,
            SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        try {
            userService.processResetPassword(request.getToken(), request.getNewPassword());
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/reset-password",
                    new AuthResponse("1", null, "Password reset successful.", null, null));
        } catch (Exception e) {
            int code = -1;
            if (e.getCause() instanceof InvalidToken_1201) {
                code = ((InvalidToken_1201) e.getCause()).getCode();
            } else if (e.getCause() instanceof TokenExpired_1301) {
                code = ((TokenExpired_1301) e.getCause()).getCode();
            }
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/reset-password",
                    new AuthResponse(String.valueOf(code), null, "Error: " + e.getMessage(), null, null));
        }
    }
}
