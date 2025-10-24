package com.bintang.fullstack_food_app_react_springboot.payment.service;

import com.bintang.fullstack_food_app_react_springboot.email_notification.entity.Notification;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderRepository;
import com.bintang.fullstack_food_app_react_springboot.payment.dto.PaymentDto;
import com.bintang.fullstack_food_app_react_springboot.payment.repository.PaymentRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final NotificationService notificationService;
    private final TemplateEngine templateEngine;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Value("${stripe.api.secret.key}")
    private String secretKey;

    @Value("${frontend.base.url}")
    private String frontEndBaseUrl;

    @Override
    public Response<?> initializePayment(PaymentDto paymentDto) {
        return null;
    }

    @Override
    public void updatePaymentForOrder(PaymentDto paymentDto) {

    }

    @Override
    public Response<List<PaymentDto>> getAllPayments() {
        return null;
    }

    @Override
    public Response<PaymentDto> getPaymentById(Long paymentId) {
        return null;
    }
}
