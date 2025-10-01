package com.bintang.fullstack_food_app_react_springboot.cart.service;

import com.bintang.fullstack_food_app_react_springboot.cart.dtos.CartDto;
import com.bintang.fullstack_food_app_react_springboot.response.Response;

public interface CartService {

    Response<?> addItemToCart(CartDto cartDto);
    Response<?> incrementItem(Long menuId);
    Response<?> decrementItem(Long menuId);
    Response<?> removeItem(Long cartItemId);
    Response<CartDto> getShoppingCart();
    Response<?> clearShoppingCart();
}
