package com.bintang.fullstack_food_app_react_springboot;

import com.bintang.fullstack_food_app_react_springboot.email_notification.dtos.NotificationDto;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@RequiredArgsConstructor
public class FullstackFoodAppReactSpringbootApplication {

	private final NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(FullstackFoodAppReactSpringbootApplication.class, args);
		System.out.println("\nSERVER BACKEND IS RUNNING");
	}

	@Bean
	CommandLineRunner runner(){
		return args -> {
			NotificationDto notificationDto = NotificationDto.builder()
					.recipient("your_email_recipient")
					.subject("EMAIL TESTING")
					.body("Hallo from the other world")
					.type(NotificationType.EMAIL)
					.build();

			notificationService.sendEmail(notificationDto);
		};
	}

}
