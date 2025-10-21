package com.bintang.fullstack_food_app_react_springboot.review.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.service.UserService;
import com.bintang.fullstack_food_app_react_springboot.enums.OrderStatus;
import com.bintang.fullstack_food_app_react_springboot.exceptions.BadRequestException;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.menu.entity.Menu;
import com.bintang.fullstack_food_app_react_springboot.menu.repository.MenuRepository;
import com.bintang.fullstack_food_app_react_springboot.order.entity.Order;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderItemRepository;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.review.dtos.ReviewDto;
import com.bintang.fullstack_food_app_react_springboot.review.entity.Review;
import com.bintang.fullstack_food_app_react_springboot.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        if(reviewDto.getOrderId() == null || reviewDto.getMenuId() == null){
            throw new BadRequestException("Order Id and Menu Item Id is required");
        }

        Menu menu = menuRepository.findById(reviewDto.getMenuId())
                .orElseThrow(() -> new NotFoundException("Menu item is not found"));

        Order order = orderRepository.findById(reviewDto.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order is not found"));

        if(!order.getUser().getId().equals(user.getId())){
            throw new BadRequestException("This order does not belong to you");
        }

        if(order.getOrderStatus() != OrderStatus.DELIVERED){
            throw new BadRequestException("You can only review items from delivered orders");
        }

        boolean itemInOrder = orderItemRepository.existsByOrderIdAndMenuId(reviewDto.getOrderId(), reviewDto.getMenuId());
        if(!itemInOrder){
            throw new BadRequestException("This menu item was not part of specified order");
        }

        if(reviewRepository.existsByUserIdAndMenuIdAndOrderId(user.getId(), reviewDto.getMenuId(), reviewDto.getMenuId())){
            throw new BadRequestException("You have already reviewed this item from this order");
        }

        Review review = Review.builder()
                .user(user)
                .menu(menu)
                .orderId(reviewDto.getOrderId())
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(review);

        // RETURN RESPONSE WITH REVIEW DATA
        ReviewDto responseDto = modelMapper.map(savedReview, ReviewDto.class);
        responseDto.setUserName(user.getName());
        responseDto.setMenuName(menu.getName());

        return Response.<ReviewDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Review addedd successfully")
                .data(responseDto)
                .build();
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
