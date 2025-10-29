package com.bintang.fullstack_food_app_react_springboot.payment.service;

import com.bintang.fullstack_food_app_react_springboot.email_notification.dtos.NotificationDto;
import com.bintang.fullstack_food_app_react_springboot.email_notification.entity.Notification;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.enums.OrderStatus;
import com.bintang.fullstack_food_app_react_springboot.enums.PaymentGateway;
import com.bintang.fullstack_food_app_react_springboot.enums.PaymentStatus;
import com.bintang.fullstack_food_app_react_springboot.exceptions.BadRequestException;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.order.entity.Order;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderRepository;
import com.bintang.fullstack_food_app_react_springboot.payment.dto.PaymentDto;
import com.bintang.fullstack_food_app_react_springboot.payment.entity.Payment;
import com.bintang.fullstack_food_app_react_springboot.payment.repository.PaymentRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

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

        if (order.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new BadRequestException("Payment is already completed for this order");
        }

        if (paymentRequest.getAmount() == null) {
            throw new BadRequestException("Amount you are passing in is null");
        }

        if (order.getTotalAmount().compareTo(paymentRequest.getAmount()) != 0) {
            throw new BadRequestException("Payment amount does not tally. Please contact our customer support agent");
        }

        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Error creating payment unique transaction id");
        }

    }

    @Override
    public void updatePaymentForOrder(PaymentDto paymentDto) {
        log.info("Inside updatePaymentForOrder()");

        Long orderId = paymentDto.getOrderId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order is not found"));

        Payment payment = new Payment();
        payment.setPaymentGateway(PaymentGateway.STRIPE);
        payment.setAmount(paymentDto.getAmount());
        payment.setTransactionId(paymentDto.getTransactionId());
        payment.setPaymentStatus(paymentDto.isSuccess() ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setOrder(order);

        if (!paymentDto.isSuccess()) {
            payment.setFailureReason(paymentDto.getFailureReason());
        }

        paymentRepository.save(payment);

        Context context = new Context(Locale.getDefault());
        context.setVariable("customerName", order.getUser().getName());
        context.setVariable("orderId", order.getId());
        context.setVariable("currentYear", Year.now().getValue());
        context.setVariable("amount", "$" + paymentDto.getAmount());

        if (paymentDto.isSuccess()) {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
            order.setOrderStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);

            context.setVariable("transactionId", paymentDto.getTransactionId());
            context.setVariable("paymentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")));
            context.setVariable("frontendBaseUrl", this.frontEndBaseUrl);

            String emailBody = templateEngine.process("payment-success", context);

            notificationService.sendEmail(NotificationDto
                    .builder()
                    .recipient(order.getUser().getEmail())
                    .subject("Payment successful - Order #" + order.getId())
                    .body(emailBody)
                    .isHtml(true)
                    .build());
        } else {
            order.setPaymentStatus(PaymentStatus.FAILED);
            order.setOrderStatus(OrderStatus.CANCELED);
            orderRepository.save(order);

            context.setVariable("failureReason", paymentDto.getFailureReason());

            String emailBody = templateEngine.process("payment-failed", context);

            notificationService.sendEmail(NotificationDto
                    .builder()
                    .recipient(order.getUser().getEmail())
                    .subject("Payment failed - Order #" + order.getId())
                    .body(emailBody)
                    .isHtml(true)
                    .build());
        }

    }

    @Override
    public Response<List<PaymentDto>> getAllPayments() {
        log.info("Inside getAllPayments()");

        List<Payment> paymentList = paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<PaymentDto> paymentDtoList = modelMapper.map(paymentList, new TypeToken<List<PaymentDto>>(){}.getType());

        return null;
    }

    @Override
    public Response<PaymentDto> getPaymentById(Long paymentId) {
        return null;
    }
}
