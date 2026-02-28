package com.irestaurant.iPortalAPI.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom security annotation for STOMP WebSocket controllers.
 * Enforces JWT validation and optional role-based access control.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireJwt {
    /**
     * Optional role required to access the method.
     * If empty, any valid authenticated user is allowed.
     */
    String role() default "";
}
