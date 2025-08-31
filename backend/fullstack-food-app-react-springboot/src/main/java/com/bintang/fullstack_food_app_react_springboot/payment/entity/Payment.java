package com.bintang.fullstack_food_app_react_springboot.payment.entity;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.enums.PaymentGateway;
import com.bintang.fullstack_food_app_react_springboot.enums.PaymentStatus;
import com.bintang.fullstack_food_app_react_springboot.order.entity.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String transactionId;

    @Enumerated(EnumType.STRING)
    private PaymentGateway paymentGateway;

    private String failureReason;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Payment(Long id, Order order, BigDecimal amount, PaymentStatus paymentStatus, String transactionId, PaymentGateway paymentGateway, String failureReason, User user) {
        this.id = id;
        this.order = order;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.paymentGateway = paymentGateway;
        this.failureReason = failureReason;
        this.user = user;
    }

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
