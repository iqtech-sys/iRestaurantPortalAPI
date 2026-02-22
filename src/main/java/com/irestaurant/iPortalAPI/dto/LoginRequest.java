package com.irestaurant.iPortalAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @JsonProperty("email")
    @NotBlank
    @Column(length = 255, nullable = false)
    private String email;
    
    @JsonProperty("password")
    @NotBlank
    @Column(length = 50, nullable = false)
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
