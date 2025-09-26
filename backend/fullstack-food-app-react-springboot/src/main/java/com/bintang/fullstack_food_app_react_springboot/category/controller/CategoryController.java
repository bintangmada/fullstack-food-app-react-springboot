package com.bintang.fullstack_food_app_react_springboot.category.controller;

import com.bintang.fullstack_food_app_react_springboot.category.dtos.CategoryDto;
import com.bintang.fullstack_food_app_react_springboot.category.service.CategoryService;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<CategoryDto>> createCategory(@RequestBody @Valid CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<CategoryDto>> updateCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDto>> getCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<CategoryDto>>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }


}
