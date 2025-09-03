package com.bintang.fullstack_food_app_react_springboot.review.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDto {

    private Long id;
    private Long menuId;
    private Long orderId;
    private String userName;
    @NotNull(message = "Rating is required")
    @Min(1)
    @Max(10)
    private Integer rating;
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;
    private String menuName;
    private LocalDateTime createdAt;

    public ReviewDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReviewDto(Long id, Long menuId, Long orderId, String userName, Integer rating, String comment, String menuName, LocalDateTime createdAt) {
        this.id = id;
        this.menuId = menuId;
        this.orderId = orderId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.menuName = menuName;
        this.createdAt = createdAt;
    }
}
