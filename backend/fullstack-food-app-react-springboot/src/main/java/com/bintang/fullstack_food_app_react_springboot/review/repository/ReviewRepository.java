package com.bintang.fullstack_food_app_react_springboot.review.repository;

import com.bintang.fullstack_food_app_react_springboot.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
