package com.bintang.fullstack_food_app_react_springboot.order.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.service.UserService;
import com.bintang.fullstack_food_app_react_springboot.cart.repository.CartRepository;
import com.bintang.fullstack_food_app_react_springboot.cart.service.CartService;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.enums.OrderStatus;
import com.bintang.fullstack_food_app_react_springboot.order.dto.OrderDto;
import com.bintang.fullstack_food_app_react_springboot.order.dto.OrderItemDto;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderItemRepository;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final TemplateEngine templateEngine;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Override
    public Response<?> placeOrderFromCart() {
        log.info("Inside placeOrderFromCart()");

        return null;
    }

    @Override
    public Response<OrderDto> getOrderById(Long orderId) {
        log.info("Inside getOrderById()");
        return null;
    }

    @Override
    public Response<Page<OrderDto>> getAllOrders(OrderStatus orderStatus, int page, int size) {
        log.info("Inside getAllOrders()");
        return null;
    }

    @Override
    public Response<List<OrderDto>> getOrdersOfUser() {
        log.info("Inside getOrdersOfUser()");
        return null;
    }

    @Override
    public Response<OrderItemDto> getOrderItemById(Long orderItemId) {
        log.info("Inside getOrderItemById()");
        return null;
    }

    @Override
    public Response<OrderDto> updateOrderStatus(OrderDto orderDto) {
        log.info("Inside updateOrderStatus()");
        return null;
    }

    @Override
    public Response<Long> couuntUniqueCustomers() {
        log.info("Inside couuntUniqueCustomers()");
        return null;
    }
}
