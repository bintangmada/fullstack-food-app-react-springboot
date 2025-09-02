package com.bintang.fullstack_food_app_react_springboot.payment.repository;

import com.bintang.fullstack_food_app_react_springboot.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
