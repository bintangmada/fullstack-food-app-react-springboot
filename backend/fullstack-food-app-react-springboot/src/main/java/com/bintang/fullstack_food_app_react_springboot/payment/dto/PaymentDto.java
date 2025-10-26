package com.bintang.fullstack_food_app_react_springboot.payment.dto;

import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.UserDto;
import com.bintang.fullstack_food_app_react_springboot.enums.PaymentGateway;
import com.bintang.fullstack_food_app_react_springboot.enums.PaymentStatus;
import com.bintang.fullstack_food_app_react_springboot.order.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private PaymentGateway paymentGateway;
    private String failureReason;
    private boolean success;
    private LocalDateTime paymentDate;
    private OrderDto order;
    private UserDto user;

}
