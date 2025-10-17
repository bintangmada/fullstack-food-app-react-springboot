package com.bintang.fullstack_food_app_react_springboot.order.service;

import com.bintang.fullstack_food_app_react_springboot.enums.OrderStatus;
import com.bintang.fullstack_food_app_react_springboot.order.dto.OrderDto;
import com.bintang.fullstack_food_app_react_springboot.order.dto.OrderItemDto;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Response<?> placeOrderFromCart();
    Response<OrderDto> getOrderById(Long orderId);
    Response<Page<OrderDto>> getAllOrders(OrderStatus orderStatus, int page, int size);
    Response<List<OrderDto>> getOrdersOfUser();
    Response<OrderItemDto> getOrderItemById(Long orderItemId);
    Response<OrderDto> updateOrderStatus(OrderDto orderDto);
    Response<Long> countUniqueCustomers();
}
