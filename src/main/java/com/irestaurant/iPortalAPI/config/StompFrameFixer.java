package com.irestaurant.iPortalAPI.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import java.util.regex.Pattern;

/**
 * WebSocketHandlerDecorator that fixes STOMP frame issues:
 * 1. Appends null octet (\u0000) terminator if missing
 * 2. Strips content-length header to prevent parser issues
 * 
 * This fixes StompConversionException when receiving frames from.
 */
public class StompFrameFixer extends WebSocketHandlerDecorator {
    
    private static final char NULL_OCTET = '\u0000';
    
    // Regex pattern to match content-length header in STOMP frame
    // Matches: content-length:\s*\d+\r\n
    private static final Pattern CONTENT_LENGTH_PATTERN = 
        Pattern.compile("content-length:\\s*\\d+\r\n", Pattern.CASE_INSENSITIVE);
    
    public StompFrameFixer(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void handleMessage(org.springframework.web.socket.WebSocketSession session, 
                              WebSocketMessage<?> message) throws Exception {
        
        if (message instanceof TextMessage) {
            String payload = ((TextMessage) message).getPayload();
            
            // Strip content-length header if present (forces parser to use null octet)
            payload = CONTENT_LENGTH_PATTERN.matcher(payload).replaceAll("");
            
            // Append null terminator if missing
            if (!payload.endsWith(String.valueOf(NULL_OCTET))) {
                payload = payload + NULL_OCTET;
            }
            
            // Create new message with fixed payload
            message = new TextMessage(payload);
        }
        
        super.handleMessage(session, message);
    }
}
