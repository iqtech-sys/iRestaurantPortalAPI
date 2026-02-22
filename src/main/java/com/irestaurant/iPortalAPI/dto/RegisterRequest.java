package com.irestaurant.iPortalAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    
    @JsonProperty("username")
    @NotBlank
    @Column(length = 255, nullable = false)
    private String username;
    
    @JsonProperty("email")
    @NotBlank
    @Column(length = 255, nullable = false)
    private String email;
    
    @JsonProperty("password")
    @NotBlank
    @Column(length = 50, nullable = false)
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
