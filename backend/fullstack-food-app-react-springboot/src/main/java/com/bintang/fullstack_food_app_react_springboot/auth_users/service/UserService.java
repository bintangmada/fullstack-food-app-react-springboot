package com.bintang.fullstack_food_app_react_springboot.auth_users.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.UserDto;
import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.response.Response;

import java.util.List;

public interface UserService {

    User getCurrentLoggedInUser();

    Response<List<UserDto>> getAllUsers();

    Response<UserDto> getOwnAccountDetails();
    Response<?> updateOwnAccount(UserDto userDto);
    Response<?> deactivateOwnAccount();

}
