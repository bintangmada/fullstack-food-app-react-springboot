package com.bintang.fullstack_food_app_react_springboot.cart.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {

    private Long id;
    private List<CartItemDto> cartItems;
    private Long menuId;
    private int quantity;
    private BigDecimal totalAmount;

    public CartDto(Long id, List<CartItemDto> cartItems, Long menuId, int quantity, BigDecimal totalAmount) {
        this.id = id;
        this.cartItems = cartItems;
        this.menuId = menuId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public CartDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
