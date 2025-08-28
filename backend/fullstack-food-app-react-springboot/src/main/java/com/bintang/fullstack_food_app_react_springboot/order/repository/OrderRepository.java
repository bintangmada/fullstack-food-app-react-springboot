package com.bintang.fullstack_food_app_react_springboot.order.repository;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.enums.OrderStatus;
import com.bintang.fullstack_food_app_react_springboot.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    List<Order> findByUserOrderByOrderDateDesc(User user);

    
}
