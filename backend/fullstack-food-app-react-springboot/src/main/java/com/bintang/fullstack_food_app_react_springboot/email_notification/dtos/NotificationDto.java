package com.bintang.fullstack_food_app_react_springboot.email_notification.dtos;

import com.bintang.fullstack_food_app_react_springboot.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDto {

    private Long id;

    @NotBlank(message = "recipient is required")
    private String recipient;

    private String subject;

    private String body;

    private NotificationType type;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private boolean isHtml;

    public NotificationDto(Long id, String recipient, String subject, String body, NotificationType type, boolean isHtml) {
        this.id = id;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.type = type;
        this.isHtml = isHtml;
    }

    public NotificationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }
}
