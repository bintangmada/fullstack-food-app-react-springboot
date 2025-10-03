package com.bintang.fullstack_food_app_react_springboot.cart.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.service.UserService;
import com.bintang.fullstack_food_app_react_springboot.cart.dtos.CartDto;
import com.bintang.fullstack_food_app_react_springboot.cart.entity.Cart;
import com.bintang.fullstack_food_app_react_springboot.cart.entity.CartItem;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

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

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> optionalCartItem = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getMenu().getId().equals(menuId))
                .findFirst();

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setSubTotal(cartItem.getPricePerUnit().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        }else{
            // IF NOT PRESENT, THEN ADD
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .menu(menu)
                    .quantity(quantity)
                    .pricePerUnit(menu.getPrice())
                    .subTotal(menu.getPrice().multiply(BigDecimal.valueOf(quantity)))
                    .build();
            cart.getCartItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
        }

//        cartRepository.save(cart);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("item added to cart successfully")
                .build();
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
