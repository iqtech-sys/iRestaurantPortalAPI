package com.irestaurant.iPortalAPI.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${iPortalApi.email.from}")
    private String fromEmail;

    @Value("${iPortalApi.email.subject}")
    private String emailSubject;

    @Value("${iPortalApi.email.resetLink}")
    private String resetLinkBase;

    @Override
    @Async
    public void sendEmail(String to, String token, String language) {
        String resetLink = resetLinkBase + token;
        logger.info(">>> Preparing to send password reset email to: {} (Language: {})", to, language);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(emailSubject);

            String htmlContent = loadEmailTemplate(resetLink, language);
            helper.setText(htmlContent, true); // true = isHtml

            mailSender.send(message);

            logger.info(">>> Email sent successfully to: {}", to);
        } catch (MessagingException | IOException e) {
            logger.error(">>> Failed to send email to {}: {}", to, e.getMessage());
            // Depending on requirements, we might want to rethrow or just log
        }
    }

    private String loadEmailTemplate(String resetLink, String language) throws IOException {
        String templateName = "reset-password.html";
        if ("ar".equalsIgnoreCase(language)) {
            templateName = "reset-password-ar.html";
        }

        Resource resource = resourceLoader.getResource("classpath:email-templates/" + templateName);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            String template = FileCopyUtils.copyToString(reader);
            // Simple string replacement for the placeholder
            return template.replace("{{resetLink}}", resetLink);
        }
    }
}
