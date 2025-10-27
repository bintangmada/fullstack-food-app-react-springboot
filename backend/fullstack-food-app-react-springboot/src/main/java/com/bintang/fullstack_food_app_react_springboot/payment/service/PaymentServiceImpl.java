package com.bintang.fullstack_food_app_react_springboot.payment.service;

import com.bintang.fullstack_food_app_react_springboot.email_notification.entity.Notification;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.enums.PaymentStatus;
import com.bintang.fullstack_food_app_react_springboot.exceptions.BadRequestException;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.order.entity.Order;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderRepository;
import com.bintang.fullstack_food_app_react_springboot.payment.dto.PaymentDto;
import com.bintang.fullstack_food_app_react_springboot.payment.repository.PaymentRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.math.BigDecimal;
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
    public Response<?> initializePayment(PaymentDto paymentRequest) {
        log.info("Inside initializePayment()");

        Stripe.apiKey = secretKey;

        Long orderId = paymentRequest.getOrderId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if(order.getPaymentStatus() == PaymentStatus.COMPLETED){
            throw new BadRequestException("Payment is already completed for this order");
        }

        if(paymentRequest.getAmount() == null){
            throw new BadRequestException("Amount you are passing in is null");
        }

        if(order.getTotalAmount().compareTo(paymentRequest.getAmount()) != 0){
            throw new BadRequestException("Payment amount does not tally. Please contact our customer support agent");
        }

        try{
            PaymentIntentCreateParams params = PaymentIntentCreateParams
                    .builder()
                    .setAmount(paymentRequest.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                    .setCurrency("usd")
                    .putMetadata("orderId", String.valueOf(orderId))
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            String uniqueTransactionId = intent.getClientSecret();

            return Response.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("success")
                    .data(uniqueTransactionId)
                    .build();
        }catch (Exception e){
            throw new RuntimeException("Error creating payment unique transaction id");
        }

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
