package com.bintang.fullstack_food_app_react_springboot.cart.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.service.UserService;
import com.bintang.fullstack_food_app_react_springboot.cart.dtos.CartDto;
import com.bintang.fullstack_food_app_react_springboot.cart.repository.CartItemRepository;
import com.bintang.fullstack_food_app_react_springboot.cart.repository.CartRepository;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.menu.entity.Menu;
import com.bintang.fullstack_food_app_react_springboot.menu.repository.MenuRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Response<?> addItemToCart(CartDto cartDto) {
        log.info("Inside addItemToCart()");

        Long menuId = cartDto.getMenuId();
        int quantity = cartDto.getQuantity();

        User user = userService.getCurrentLoggedInUser();

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu is not found"));

        // LANJUT LAGI

        return null;
    }

    @Override
    public Response<?> incrementItem(Long menuId) {
        log.info("Inside incrementItem()");
        return null;
    }

    @Override
    public Response<?> decrementItem(Long menuId) {
        log.info("Inside decrementItem()");
        return null;
    }

    @Override
    public Response<?> removeItem(Long cartItemId) {
        log.info("Inside removeItem()");
        return null;
    }

    @Override
    public Response<CartDto> getShoppingCart() {
        log.info("Inside getShoppingCart()");
        return null;
    }

    @Override
    public Response<?> clearShoppingCart() {
        log.info("Inside clearShoppingCart()");
        return null;
    }
}
