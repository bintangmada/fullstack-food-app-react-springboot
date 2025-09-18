package com.bintang.fullstack_food_app_react_springboot.auth_users.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.UserDto;
import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.repository.UserRepository;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // AMBIL EMAIL DARI SECURITY CONTEXT HOLDER
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email not found"));

    }

    @Override
    public Response<List<UserDto>> getAllUsers() {
        log.info("insife getAllUsers()");
        List<User> listUser = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<UserDto> listUserDto = modelMapper.map(listUser, new TypeToken<List<UserDto>>(){}.getType());

        return Response.<List<UserDto>>builder()
                .message("All user are retrieved")
                .statusCode(HttpStatus.OK.value())
                .data(listUserDto)
                .build();
    }

    @Override
    public Response<UserDto> getOwnAccountDetails() {
        log.info("Inside getOwnAccountDetails()");
        User currentUserLogin = getCurrentLoggedInUser();

        UserDto currentUserLoginDto = modelMapper.map(currentUserLogin, UserDto.class);

        return Response.<UserDto>builder()
                .message("Own Account Details")
                .statusCode(HttpStatus.OK.value())
                .data(currentUserLoginDto)
                .build();
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
