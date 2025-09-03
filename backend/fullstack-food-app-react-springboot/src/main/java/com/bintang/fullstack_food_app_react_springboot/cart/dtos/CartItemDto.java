package com.bintang.fullstack_food_app_react_springboot.cart.dtos;

import com.bintang.fullstack_food_app_react_springboot.menu.dtos.MenuDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemDto {

    private Long id;
    private MenuDto menuDto;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal subTotal;

    public CartItemDto(Long id, MenuDto menuDto, int quantity, BigDecimal pricePerUnit, BigDecimal subTotal) {
        this.id = id;
        this.menuDto = menuDto;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.subTotal = subTotal;
    }

    public CartItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MenuDto getMenuDto() {
        return menuDto;
    }

    public void setMenuDto(MenuDto menuDto) {
        this.menuDto = menuDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}
