package com.bintang.fullstack_food_app_react_springboot.auth_users.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.UserDto;
import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.repository.UserRepository;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
//    private final AWS

    @Override
    public User getCurrentLoggedInUser() {
        return null;
    }

    @Override
    public Response<List<UserDto>> getAllUsers() {
        return null;
    }

    @Override
    public Response<UserDto> getOwnAccountDetails() {
        return null;
    }

    @Override
    public Response<?> updateOwnAccount(UserDto userDto) {
        return null;
    }

    @Override
    public Response<?> deactivateOwnAccount() {
        return null;
    }
}
