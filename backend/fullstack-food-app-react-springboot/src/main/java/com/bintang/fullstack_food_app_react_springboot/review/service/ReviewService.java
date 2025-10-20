package com.bintang.fullstack_food_app_react_springboot.review.service;

import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.review.dtos.ReviewDto;

import java.util.List;

public interface ReviewService {

    Response<ReviewDto> createReview(ReviewDto reviewDto);
    Response<List<ReviewDto>> getReviewsForMenu(Long menuId);


}
