package com.irestaurant.iPortalAPI.service;

public interface EmailService {
    void sendEmail(String to, String token, String language);
}
