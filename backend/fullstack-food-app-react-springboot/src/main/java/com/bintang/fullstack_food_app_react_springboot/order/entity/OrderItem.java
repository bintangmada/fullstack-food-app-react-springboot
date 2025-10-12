package com.bintang.fullstack_food_app_react_springboot.order.entity;

import com.bintang.fullstack_food_app_react_springboot.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int quantity;

    private BigDecimal pricePerUnit;
    private BigDecimal subTotal;

    public OrderItem(Long id, Order order, Menu menu, int quantity, BigDecimal pricePerUnit, BigDecimal subTotal) {
        this.id = id;
        this.order = order;
        this.menu = menu;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.subTotal = subTotal;
    }

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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
