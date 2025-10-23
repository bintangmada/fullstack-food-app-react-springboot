package com.bintang.fullstack_food_app_react_springboot.payment.service;

import com.bintang.fullstack_food_app_react_springboot.payment.dto.PaymentDto;
import com.bintang.fullstack_food_app_react_springboot.response.Response;

import java.util.List;

public interface PaymentService {

    Response<?> initializePayment(PaymentDto paymentDto);
    void updatePaymentForOrder(PaymentDto paymentDto);
    Response<List<PaymentDto>> getAllPayments();
    Response<PaymentDto> getPaymentById(Long paymentId);

}
