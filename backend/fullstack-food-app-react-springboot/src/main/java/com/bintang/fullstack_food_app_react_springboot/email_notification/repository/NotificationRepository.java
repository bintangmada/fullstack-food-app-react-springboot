package com.bintang.fullstack_food_app_react_springboot.email_notification.repository;

import com.bintang.fullstack_food_app_react_springboot.email_notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
