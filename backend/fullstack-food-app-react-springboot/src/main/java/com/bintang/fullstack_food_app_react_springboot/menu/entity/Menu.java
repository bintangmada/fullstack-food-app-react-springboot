package com.bintang.fullstack_food_app_react_springboot.menu.entity;

import com.bintang.fullstack_food_app_react_springboot.category.entity.Category;
import com.bintang.fullstack_food_app_react_springboot.order.entity.OrderItem;
import com.bintang.fullstack_food_app_react_springboot.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "menus")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Review> reviews;

}
