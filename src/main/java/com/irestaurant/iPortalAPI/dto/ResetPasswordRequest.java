package com.irestaurant.iPortalAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class ResetPasswordRequest {
    
    private String token;
    
    @JsonProperty("newPassword")
    @NotBlank
    @Column(length = 50, nullable = false)
    private String newPassword;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String token, String newPassword) {
        this.token = token;
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
