package com.bintang.fullstack_food_app_react_springboot.review.controller;

import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.review.dtos.ReviewDto;
import com.bintang.fullstack_food_app_react_springboot.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ResponseEntity<Response<ReviewDto>> createReview(@RequestBody @Valid ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.createReview(reviewDto));
    }

}
