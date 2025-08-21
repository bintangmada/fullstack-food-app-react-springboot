package com.bintang.fullstack_food_app_react_springboot.cart.repository;

import com.bintang.fullstack_food_app_react_springboot.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser_Id(Long userId);
}
