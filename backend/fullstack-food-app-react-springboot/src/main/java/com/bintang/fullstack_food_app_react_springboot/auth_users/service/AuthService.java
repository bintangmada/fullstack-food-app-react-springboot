package com.bintang.fullstack_food_app_react_springboot.auth_users.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.LoginRequest;
import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.RegistrationRequest;
import com.bintang.fullstack_food_app_react_springboot.response.Response;

public interface AuthService {

    Response<?> register(RegistrationRequest registrationRequest);
    Response<?> login(LoginRequest loginRequest);
}
