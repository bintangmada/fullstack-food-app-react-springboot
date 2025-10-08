package com.bintang.fullstack_food_app_react_springboot.cart.controller;

import com.bintang.fullstack_food_app_react_springboot.cart.dtos.CartDto;
import com.bintang.fullstack_food_app_react_springboot.cart.service.CartService;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<Response<?>> addItemToCart(@RequestBody CartDto cartDto){
        return ResponseEntity.ok(cartService.addItemToCart(cartDto));
    }

    @PutMapping("/items/increment/{menuId}")
    public ResponseEntity<Response<?>> incrementItem(@PathVariable("menuId") Long menuId){
        return ResponseEntity.ok(cartService.incrementItem(menuId));
    }

    @PutMapping("/items/decrement/{menuId}")
    public ResponseEntity<Response<?>> decrementItem(@PathVariable("menuId") Long menuId){
        return ResponseEntity.ok(cartService.decrementItem(menuId));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Response<?>> removeItem(@PathVariable("cartItemId") Long cartItemId){
        return ResponseEntity.ok(cartService.removeItem(cartItemId));
    }

    @GetMapping
    public ResponseEntity<Response<?>> getShoppingCart(){
        return ResponseEntity.ok(cartService.getShoppingCart());
    }

    @DeleteMapping
    public ResponseEntity<Response<?>> clearShoppingCart(){
        return ResponseEntity.ok(cartService.clearShoppingCart());
    }


}
