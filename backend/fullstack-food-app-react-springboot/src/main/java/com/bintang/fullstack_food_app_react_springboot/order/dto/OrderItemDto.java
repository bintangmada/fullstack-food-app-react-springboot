package com.bintang.fullstack_food_app_react_springboot.order.dto;

import com.bintang.fullstack_food_app_react_springboot.menu.dtos.MenuDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemDto {

    private Long id;
    private Long quantity;
    private Long menuId;
    private MenuDto menu;
    private BigDecimal pricePerUnit;
    private BigDecimal subTotal;

    public OrderItemDto(Long id, Long quantity, Long menuId, MenuDto menu, BigDecimal pricePerUnit, BigDecimal subTotal) {
        this.id = id;
        this.quantity = quantity;
        this.menuId = menuId;
        this.menu = menu;
        this.pricePerUnit = pricePerUnit;
        this.subTotal = subTotal;
    }

    public OrderItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public MenuDto getMenu() {
        return menu;
    }

    public void setMenu(MenuDto menu) {
        this.menu = menu;
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
