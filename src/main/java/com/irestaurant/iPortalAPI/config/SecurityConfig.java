package com.irestaurant.iPortalAPI.config;

import com.irestaurant.iPortalAPI.service.UserService;
import com.irestaurant.iPortalAPI.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/ws/**",
                    "/springwolf/**",
                    "/asyncapi-docs/**",
                    "/webjars/**",
                    "/springwolf/stomp/publish"
                ).permitAll()
                .requestMatchers("/error").permitAll() 
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .formLogin(form -> form.permitAll());

        return http.build();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    
                    // Extract JWT token from Authorization header
                    String token = accessor.getFirstNativeHeader("Authorization");
                    
                    // Try to validate JWT and set authentication
                    if (token != null && token.startsWith("Bearer ")) {
                        Authentication auth = validateToken(token.substring(7));
                        if (auth != null) {
                            accessor.setUser(auth);
                            SecurityContextHolder.getContext().setAuthentication(auth);
                            return message;
                        }
                    }
                    
                    // No JWT - create a temporary user based on session ID
                    // This allows @SendToUser to work for unauthenticated users (e.g., during registration)
                    String sessionId = accessor.getSessionId();
                    if (sessionId != null) {
                        // Create a temporary principal using session ID
                        Authentication tempAuth = new UsernamePasswordAuthenticationToken(
                            sessionId,  // Use sessionId as the user identifier
                            null, 
                            null
                        );
                        accessor.setUser(tempAuth);
                        
                        // Store session ID in session attributes for later retrieval
                        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                        if (sessionAttributes == null) {
                            sessionAttributes = new HashMap<>();
                            accessor.setSessionAttributes(sessionAttributes);
                        }
                        sessionAttributes.put("stompSessionId", sessionId);
                        
                        System.out.println("Registered temporary user for session: " + sessionId);
                    }
                }
                return message;
            }
        });
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/ws/**");
    }

    private Authentication validateToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }
        } catch (Exception e) {
            // Token invalid
        }
        return null;
    }
}
