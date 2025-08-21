package com.bintang.fullstack_food_app_react_springboot.cart.repository;

import com.bintang.fullstack_food_app_react_springboot.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
