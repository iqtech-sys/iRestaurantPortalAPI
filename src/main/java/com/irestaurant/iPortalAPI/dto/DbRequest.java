package com.irestaurant.iPortalAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;


public class DbRequest {
    
    @JsonProperty("email")
    @NotBlank
    @Column(length = 255, nullable = false)
    private String email;
    
    
    public DbRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }   
}
