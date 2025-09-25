package com.bintang.fullstack_food_app_react_springboot.category.repository;

import com.bintang.fullstack_food_app_react_springboot.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.name = :name")
    String findCategoryByName(@Param("name") String name);
}
