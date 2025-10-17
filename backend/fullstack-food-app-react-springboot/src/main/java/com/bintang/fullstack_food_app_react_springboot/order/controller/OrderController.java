package com.bintang.fullstack_food_app_react_springboot.order.controller;

import com.bintang.fullstack_food_app_react_springboot.order.dto.OrderDto;
import com.bintang.fullstack_food_app_react_springboot.order.service.OrderService;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Response<?>> checkout(){
        return ResponseEntity.ok(orderService.placeOrderFromCart());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<Response<List<OrderDto>>> getMyOrders(){
        return ResponseEntity.ok(orderService.getOrdersOfUser());
    }

}
