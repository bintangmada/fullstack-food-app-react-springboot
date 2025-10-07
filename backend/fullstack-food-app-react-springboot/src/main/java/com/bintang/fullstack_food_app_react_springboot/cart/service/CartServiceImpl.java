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
import java.util.List;
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
        } else {
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

        User user = userService.getCurrentLoggedInUser();
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart is not found"));

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getMenu().getId().equals(menuId))
                .findFirst().orElseThrow(() -> new NotFoundException("Menu is not found"));

        int newQuantity = cartItem.getQuantity() + 1;

        cartItem.setQuantity(newQuantity);
        cartItem.setSubTotal(cartItem.getPricePerUnit().multiply(BigDecimal.valueOf(newQuantity)));
        cartItemRepository.save(cartItem);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("item quantity incremented successfully")
                .build();
    }

    @Override
    public Response<?> decrementItem(Long menuId) {
        log.info("Inside decrementItem()");

        User user = userService.getCurrentLoggedInUser();
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart is not found"));

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getMenu().getId().equals(menuId))
                .findFirst().orElseThrow(() -> new NotFoundException("Menu is not found"));

        int newQuantity = cartItem.getQuantity() - 1;

        if (newQuantity > 0) {
            cartItem.setQuantity(newQuantity);
            cartItem.setSubTotal(cartItem.getPricePerUnit().multiply(BigDecimal.valueOf(newQuantity)));
            cartItemRepository.save(cartItem);
        } else {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("item quantity decremented successfully")
                .build();
    }

    @Override
    public Response<?> removeItem(Long cartItemId) {
        log.info("Inside removeItem()");

        User user = userService.getCurrentLoggedInUser();

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart is not found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("Cart item is not found"));

        if(!cart.getCartItems().contains(cartItem)){
            throw new NotFoundException("Cart item does not belong to this user's cart");
        }

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("item removed from cart successfully")
                .build();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Response<CartDto> getShoppingCart() {
        log.info("Inside getShoppingCart()");

        User user = userService.getCurrentLoggedInUser();

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart is not found"));

        List<CartItem> cartItems = cart.getCartItems();

        CartDto cartDto = modelMapper.map(cart, CartDto.class);

        BigDecimal totalAmount = BigDecimal.ZERO;
        if(cartItems != null){
            for(CartItem item : cartItems){
                totalAmount = totalAmount.add(item.getSubTotal());
            }
        }

        cartDto.setTotalAmount(totalAmount);

        // remove reviews from response
        if(cartDto.getCartItems() != null){
            cartDto.getCartItems()
                    .forEach(item -> item.getMenuDto().setReviews(null));
        }

        return Response.<CartDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Shopping cart retrieved successfully")
                .data(cartDto)
                .build();
    }

    @Override
    public Response<?> clearShoppingCart() {
        log.info("Inside clearShoppingCart()");

        User user = userService.getCurrentLoggedInUser();

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart is not found"));

        cartItemRepository.deleteAll(cart.getCartItems());

        cart.getCartItems().clear();

        cartRepository.save(cart);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Shopping cart cleared successfully")
                .build();
    }
}
