package com.bintang.fullstack_food_app_react_springboot.email_notification.services;

import com.bintang.fullstack_food_app_react_springboot.email_notification.dtos.NotificationDto;

public interface NotificationService {

    void sendEmail(NotificationDto notificationDto);
}
