package com.bintang.fullstack_food_app_react_springboot.category.service;

import com.bintang.fullstack_food_app_react_springboot.category.dtos.CategoryDto;
import com.bintang.fullstack_food_app_react_springboot.category.entity.Category;
import com.bintang.fullstack_food_app_react_springboot.category.repository.CategoryRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return null;
    }

    @Override
    public Response<List<CategoryDto>> getAllCategories() {
        return null;
    }

    @Override
    public Response<CategoryDto> getCategoryById(Long categoryId) {
        return null;
    }

    @Override
    public Response<?> deleteCategory(Long categoryId) {
        return null;
    }
}
