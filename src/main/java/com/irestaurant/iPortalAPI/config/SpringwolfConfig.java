package com.irestaurant.iPortalAPI.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Springwolf configuration for AsyncAPI documentation (STOMP/WebSocket endpoints).
 * Compatible with Spring Boot 3.x and Springwolf 1.21.0.
 * 
 * Note: AsyncAPI configuration is now managed via application.properties:
 * - springwolf.docket.info.title
 * - springwolf.docket.info.version
 * - springwolf.docket.base-package
 */
@Configuration
@ConditionalOnProperty(name = "springwolf.enabled", havingValue = "true", matchIfMissing = false)
public class SpringwolfConfig {
    // Configuration moved to application.properties to avoid conflicts
}
