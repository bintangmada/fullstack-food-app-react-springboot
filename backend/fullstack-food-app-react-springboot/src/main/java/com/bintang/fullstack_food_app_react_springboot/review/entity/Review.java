package com.bintang.fullstack_food_app_react_springboot.review.entity;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // customer yang ngasih review

    private Integer rating; // misalnya 1 sampai 10

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime createdAt;

    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Review() {
    }

    public Review(Long id, User user, Integer rating, String comment, LocalDateTime createdAt, Long orderId, Menu menu) {
        this.id = id;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.orderId = orderId;
        this.menu = menu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
