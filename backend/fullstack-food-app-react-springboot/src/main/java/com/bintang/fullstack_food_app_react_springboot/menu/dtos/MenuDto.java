package com.bintang.fullstack_food_app_react_springboot.menu.dtos;

import com.bintang.fullstack_food_app_react_springboot.review.dtos.ReviewDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    private String description;
    @NotNull
    @Positive(message = "price must be positive")
    private BigDecimal price;
    private String imageUrl;
    @NotNull(message = "category id is required")
    private Long categoryId;
    private MultipartFile imageFile;
    private List<ReviewDto> reviews;

}
