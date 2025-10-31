package com.bintang.fullstack_food_app_react_springboot.payment.controller;

import com.bintang.fullstack_food_app_react_springboot.payment.dto.PaymentDto;
import com.bintang.fullstack_food_app_react_springboot.payment.service.PaymentService;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<Response<?>> initializePayment(@RequestBody @Valid PaymentDto paymentRequest){
        return ResponseEntity.ok(paymentService.initializePayment(paymentRequest));
    }

    @PutMapping("/update")
    public void updatePaymentForOrder(@RequestBody PaymentDto paymentRequest){
        paymentService.updatePaymentForOrder(paymentRequest);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<PaymentDto>>> getAllPayments(){
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Response<PaymentDto>> getPaymentById(@PathVariable Long paymentId){
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }
}

"chart.js,
fontawesome, solid svg icons
