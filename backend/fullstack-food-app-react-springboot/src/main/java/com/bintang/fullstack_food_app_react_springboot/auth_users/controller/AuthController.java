package com.bintang.fullstack_food_app_react_springboot.auth_users.controller;

import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.LoginRequest;
import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.RegistrationRequest;
import com.bintang.fullstack_food_app_react_springboot.auth_users.service.AuthService;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<?>> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authService.register(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
