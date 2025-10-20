package com.bintang.fullstack_food_app_react_springboot.review.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.service.UserService;
import com.bintang.fullstack_food_app_react_springboot.menu.repository.MenuRepository;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderItemRepository;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.review.dtos.ReviewDto;
import com.bintang.fullstack_food_app_react_springboot.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    @Transactional
    public Response<ReviewDto> createReview(ReviewDto reviewDto) {
        log.info("inside createReview()");

        // GET CURRENT USER
        User user = userService.getCurrentLoggedInUser();

        return null;
    }

    @Override
    public Response<List<ReviewDto>> getReviewsForMenu(Long menuId) {
        log.info("inside getReviewsForMenu()");
        return null;
    }

    @Override
    public Response<Double> getAverageRating(Long menuId) {
        log.info("inside getAverageRating()");
        return null;
    }
}
