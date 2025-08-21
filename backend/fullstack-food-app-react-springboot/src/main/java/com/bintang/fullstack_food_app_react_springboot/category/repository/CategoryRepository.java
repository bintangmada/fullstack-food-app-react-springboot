package com.bintang.fullstack_food_app_react_springboot.category.repository;

import com.bintang.fullstack_food_app_react_springboot.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
