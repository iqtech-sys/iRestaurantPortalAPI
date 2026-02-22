package com.irestaurant.iPortalAPI.dto;

import java.util.Set;

public class AuthResponse extends ApiResponse {
    
    private String token;
    private Set<String> roles;

    public AuthResponse() {
    }

    public AuthResponse(String code,String token, String message, Set<String> roles) {
        this.token = token;
        this.roles = roles;
        super.message = message;
        super.code = code;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
