package com.bintang.fullstack_food_app_react_springboot.cart.service;

import com.bintang.fullstack_food_app_react_springboot.cart.dtos.CartDto;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
    @Override
    public Response<?> addItemToCart(CartDto cartDto) {
        return null;
    }

    @Override
    public Response<?> incrementItem(Long menuId) {
        return null;
    }

    @Override
    public Response<?> decrementItem(Long menuId) {
        return null;
    }

    @Override
    public Response<?> removeItem(Long cartItemId) {
        return null;
    }

    @Override
    public Response<CartDto> getShoppingCart() {
        return null;
    }

    @Override
    public Response<?> clearShoppingCart() {
        return null;
    }
}
