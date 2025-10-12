package com.bintang.fullstack_food_app_react_springboot.order.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.service.UserService;
import com.bintang.fullstack_food_app_react_springboot.cart.entity.Cart;
import com.bintang.fullstack_food_app_react_springboot.cart.entity.CartItem;
import com.bintang.fullstack_food_app_react_springboot.cart.repository.CartRepository;
import com.bintang.fullstack_food_app_react_springboot.cart.service.CartService;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.enums.OrderStatus;
import com.bintang.fullstack_food_app_react_springboot.enums.PaymentStatus;
import com.bintang.fullstack_food_app_react_springboot.exceptions.BadRequestException;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.order.dto.OrderDto;
import com.bintang.fullstack_food_app_react_springboot.order.dto.OrderItemDto;
import com.bintang.fullstack_food_app_react_springboot.order.entity.Order;
import com.bintang.fullstack_food_app_react_springboot.order.entity.OrderItem;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderItemRepository;
import com.bintang.fullstack_food_app_react_springboot.order.repository.OrderRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final TemplateEngine templateEngine;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Override
    public Response<?> placeOrderFromCart() {

        log.info("Inside placeOrderFromCart()");

        User customer = userService.getCurrentLoggedInUser();
        String deliveryAddress = customer.getAddress();
        if(deliveryAddress == null){
            throw new NotFoundException("Delivery address not found for the user");
        }

        Cart cart = cartRepository.findByUser_Id(customer.getId())
                .orElseThrow(() -> new NotFoundException("Cart not found for the user"));

        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems == null || cartItems.isEmpty()){
            throw new BadRequestException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(CartItem cartItem : cartItems){
            OrderItem orderItem = OrderItem.builder()
                    .menu(cartItem.getMenu())
                    .quantity(cartItem.getQuantity())
                    .pricePerUnit(cartItem.getPricePerUnit())
                    .subTotal(cartItem.getSubTotal())
                    .build();
            orderItems.add(orderItem);
            totalAmount = totalAmount.add(cartItem.getSubTotal());
        }

        Order order = Order.builder()
                .user(customer)
                .orderItems(orderItems)
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.INITIALIZED)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);
        orderItems.forEach(orderItem -> orderItem.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItems);

        cartService.clearShoppingCart();

        OrderDto orderDto = modelMapper.map(savedOrder, OrderDto.class);

        // send email notifications
        sendOrderConfirmationEmail(customer, orderDto);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Your order has been received! We've sent a secure payment link to your email. Please proceed for payment to confirm your order.")
                .build();
    }

    @Override
    public Response<OrderDto> getOrderById(Long orderId) {
        log.info("Inside getOrderById()");
        return null;
    }

    @Override
    public Response<Page<OrderDto>> getAllOrders(OrderStatus orderStatus, int page, int size) {
        log.info("Inside getAllOrders()");
        return null;
    }

    @Override
    public Response<List<OrderDto>> getOrdersOfUser() {
        log.info("Inside getOrdersOfUser()");
        return null;
    }

    @Override
    public Response<OrderItemDto> getOrderItemById(Long orderItemId) {
        log.info("Inside getOrderItemById()");
        return null;
    }

    @Override
    public Response<OrderDto> updateOrderStatus(OrderDto orderDto) {
        log.info("Inside updateOrderStatus()");
        return null;
    }

    @Override
    public Response<Long> couuntUniqueCustomers() {
        log.info("Inside couuntUniqueCustomers()");
        return null;
    }
}
