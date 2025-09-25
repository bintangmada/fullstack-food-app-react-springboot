package com.bintang.fullstack_food_app_react_springboot.category.service;

import com.bintang.fullstack_food_app_react_springboot.category.dtos.CategoryDto;
import com.bintang.fullstack_food_app_react_springboot.category.entity.Category;
import com.bintang.fullstack_food_app_react_springboot.category.repository.CategoryRepository;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response<CategoryDto> addCategory(CategoryDto categoryDto) {

        log.info("Inside addCategory()");

        Category category = modelMapper.map(categoryDto, Category.class);

        // OPTIONAL
//        String existingCategoryName = categoryRepository.findCategoryByName(category.getName());
//        if(existingCategoryName != null && !existingCategoryName.isEmpty()){
//            throw new EntityExistsException("Category is exists");
//        }

        categoryRepository.save(category);

        return Response.<CategoryDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response<CategoryDto> updateCategory(CategoryDto categoryDto) {

        log.info("inside updateCategory()");

        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException("Category is not found"));

        if(categoryDto.getName() != null && !categoryDto.getName().isEmpty()){
            category.setName(categoryDto.getName());
        }

        if(categoryDto.getDescription() != null && !categoryDto.getDescription().isEmpty()){
            category.setDescription(categoryDto.getDescription());
        }

        categoryRepository.save(category);

        return Response.<CategoryDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category updated successfully")
                .build();
    }

    @Override
    public Response<List<CategoryDto>> getAllCategories() {

        log.info("Inside getAllCategories()");

        List<Category> categories = categoryRepository.findAll();

        if(categories == null){
            throw new NotFoundException("categories is empty");
        }

        List<CategoryDto> categoryDtos = categories
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();

        return Response.<List<CategoryDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success get all categories")
                .data(categoryDtos)
                .build();
    }

    @Override
    public Response<CategoryDto> getCategoryById(Long categoryId) {

        log.info("Inside getCategoryById()");

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category does not exists"));

        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

        return Response.<CategoryDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category updated successfully")
                .data(categoryDto)
                .build();
    }

    @Override
    public Response<?> deleteCategory(Long categoryId) {

        log.info("Inside deleteCategory()");

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category is not found"));

        categoryRepository.deleteById(categoryId);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category deleted successfully")
                .build();
    }
}
