package com.irestaurant.iPortalAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordRequest {
    
    @JsonProperty("email")
    @NotBlank
    @Column(length = 255, nullable = false)
    private String email;
    
    @JsonProperty("language")
    @NotBlank
    @Column(length = 10, nullable = false)
    private String language;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
