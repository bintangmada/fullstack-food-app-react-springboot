package com.bintang.fullstack_food_app_react_springboot.review.controller;

import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.review.dtos.ReviewDto;
import com.bintang.fullstack_food_app_react_springboot.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Response<ReviewDto>> createReview(@RequestBody @Valid ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.createReview(reviewDto));
    }

    @GetMapping("/menu-item/{menuId}")
    public ResponseEntity<Response<List<ReviewDto>>> getReviewsForMenu(@PathVariable("menuId") Long menuId){
        return ResponseEntity.ok(reviewService.getReviewsForMenu(menuId));
    }

    @GetMapping("/menu-item/average/{menuId}")
    public ResponseEntity<Response<Double>> getAverageRatingForMenu(@PathVariable("menuId") Long menuId){
        return ResponseEntity.ok(reviewService.getAverageRating(menuId));
    }

}
