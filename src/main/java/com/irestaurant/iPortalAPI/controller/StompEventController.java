package com.irestaurant.iPortalAPI.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * Controller to handle STOMP events and ensure sessions are properly
 * registered.
 * This enables convertAndSendToUser() to work correctly.
 */
@Controller
public class StompEventController {

    private static final Logger logger = LoggerFactory.getLogger(StompEventController.class);

    /**
     * Handle session subscribe events.
     * This is called when a client subscribes to a destination.
     * Sessions that subscribe to /topic/auth will receive messages
     * sent via convertAndSendToUser(sessionId, "/queue/auth", payload).
     */
    @EventListener
    public void handleSessionSubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String destination = accessor.getDestination();
        //logger.info("Session {} subscribed to: {}", sessionId, destination);
        // Log session registration for user messaging
    //  if (destination != null && destination.contains("/topic/")) {
    //    logger.debug("Session {} is now registered for topic messaging", sessionId);
    //  }
    }
}
