package com.bintang.fullstack_food_app_react_springboot.order.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.repository.UserRepository;
import com.bintang.fullstack_food_app_react_springboot.auth_users.service.UserService;
import com.bintang.fullstack_food_app_react_springboot.cart.entity.Cart;
import com.bintang.fullstack_food_app_react_springboot.cart.entity.CartItem;
import com.bintang.fullstack_food_app_react_springboot.cart.repository.CartRepository;
import com.bintang.fullstack_food_app_react_springboot.cart.service.CartService;
import com.bintang.fullstack_food_app_react_springboot.email_notification.dtos.NotificationDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final TemplateEngine templateEngine;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Value("${base.payment.link}")
    private String basePaymentLink;

    @Override
    public Response<?> placeOrderFromCart() {

        log.info("Inside placeOrderFromCart()");

        User customer = userService.getCurrentLoggedInUser();
        String deliveryAddress = customer.getAddress();
        if (deliveryAddress == null) {
            throw new NotFoundException("Delivery address not found for the user");
        }

        Cart cart = cartRepository.findByUser_Id(customer.getId())
                .orElseThrow(() -> new NotFoundException("Cart not found for the user"));

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
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

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order is not found"));

        OrderDto orderDto = modelMapper.map(order, OrderDto.class);

        return Response.<OrderDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Order retrieved successfully")
                .data(orderDto)
                .build();

    }

    @Override
    public Response<Page<OrderDto>> getAllOrders(OrderStatus orderStatus, int page, int size) {
        log.info("Inside getAllOrders()");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<Order> orderPage;

        if(orderStatus != null){
            orderPage = orderRepository.findByOrderStatus(orderStatus, pageable);
        }else{
            orderPage = orderRepository.findAll(pageable);
        }

        Page<OrderDto> orderDtoPage = orderPage.map(order -> {
            OrderDto dto = modelMapper.map(order, OrderDto.class);
            dto.getOrderItems().forEach(orderItemDto -> orderItemDto.getMenu().setReviews(null));
            return dto;
        });

        return Response.<Page<OrderDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Orders retrieved successfully")
                .data(orderDtoPage)
                .build();
    }

    @Override
    public Response<List<OrderDto>> getOrdersOfUser() {
        log.info("Inside getOrdersOfUser()");

        User customer = userService.getCurrentLoggedInUser();
        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(customer);

        List<OrderDto> orderDtos = orders
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();

        orderDtos.forEach(orderItem ->{
            orderItem.setUser(null);
            orderItem.getOrderItems().forEach(item -> item.getMenu().setReviews(null));
        });

        return Response.<List<OrderDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Order for user retrieved successfully")
                .data(orderDtos)
                .build();
    }

    @Override
    public Response<OrderItemDto> getOrderItemById(Long orderItemId) {
        log.info("Inside getOrderItemById()");

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item is not found"));

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

    private void sendOrderConfirmationEmail(User customer, OrderDto orderDto) {
        String subject = "Your Order Confirmation - Order #" + orderDto.getId();

        // CREATE A THYMELEAF CONTEXT AND SET VARIABLES. IMPORT THE CONTEXT FROM THYMELEAF
        Context context = new Context(Locale.getDefault());
        context.setVariable("customerName", customer.getName());
        context.setVariable("orderId", String.valueOf(orderDto.getId()));
        context.setVariable("orderDate", orderDto.getOrderDate());
        context.setVariable("totalAmount", orderDto.getTotalAmount());

        // FORMAT DELIVERY ADDRESS
        String deliveryAddress = orderDto.getUser().getAddress();
        context.setVariable("deliveryAddress", deliveryAddress);

        context.setVariable("currentYear", java.time.Year.now());

        // BUILD THE ORDER ITEMS HTML USING STRING BUILDER
        StringBuilder orderItemsHtml = new StringBuilder();
        for (OrderItemDto item : orderDto.getOrderItems()) {
            orderItemsHtml
                    .append("<div class =\"order-item\">")
                    .append("<p>").append(item.getMenu().getName()).append(" x").append(item.getQuantity()).append("</p>")
                    .append("<p> $").append(item.getSubTotal()).append("</p>")
                    .append("</div>");
        }
        context.setVariable("orderItemsHtml", orderItemsHtml.toString());
        context.setVariable("totalItems", orderDto.getOrderItems().size());

        String paymentLink = basePaymentLink + orderDto.getId() + "&amount=" + orderDto.getTotalAmount();
        context.setVariable("paymentLink", paymentLink);

        String emailBody = templateEngine.process("order-confirmation", context);

        notificationService.sendEmail(NotificationDto.builder()
                .recipient(customer.getEmail())
                .subject(subject)
                .body(emailBody)
                .isHtml(true)
                .build());

    }
}
