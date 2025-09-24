package com.bintang.fullstack_food_app_react_springboot.category.service;

import com.bintang.fullstack_food_app_react_springboot.category.dtos.CategoryDto;
import com.bintang.fullstack_food_app_react_springboot.response.Response;

import java.util.List;

public interface CategoryService {

    Response<CategoryDto> addCategory(CategoryDto categoryDto);
    Response<CategoryDto> updateCategory(CategoryDto categoryDto);
    Response<List<CategoryDto>> getAllCategories();
    Response<CategoryDto> getCategoryById(Long categoryId);
    Response<?> deleteCategory(Long categoryId);
}
