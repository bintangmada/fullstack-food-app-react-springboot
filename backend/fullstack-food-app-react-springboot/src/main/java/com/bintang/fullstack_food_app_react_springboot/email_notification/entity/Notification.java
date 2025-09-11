package com.bintang.fullstack_food_app_react_springboot.email_notification.entity;

import com.bintang.fullstack_food_app_react_springboot.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "recipient is required")
    private String recipient;

    private String subject;

    @Lob
    private String body;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime createdAt;

    private boolean isHtml;

    public Notification() {
    }

    public Notification(Long id, String recipient, String subject, String body, NotificationType type, LocalDateTime createdAt, boolean isHtml) {
        this.id = id;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.type = type;
        this.createdAt = createdAt;
        this.isHtml = isHtml;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
