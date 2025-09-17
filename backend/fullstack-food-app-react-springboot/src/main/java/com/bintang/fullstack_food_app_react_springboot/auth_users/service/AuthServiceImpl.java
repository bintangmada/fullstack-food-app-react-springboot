package com.bintang.fullstack_food_app_react_springboot.auth_users.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.LoginRequest;
import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.LoginResponse;
import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.RegistrationRequest;
import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.repository.UserRepository;
import com.bintang.fullstack_food_app_react_springboot.exceptions.BadRequestException;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.role.entity.Role;
import com.bintang.fullstack_food_app_react_springboot.role.repository.RoleRepository;
import com.bintang.fullstack_food_app_react_springboot.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {

        log.info("inside register()");

        // CARI USER BERDASARKAN EMAIL
        if(userRepository.existsByEmail(registrationRequest.getEmail())){
            throw new BadRequestException("Email already exists");
        }

        // MAPPING LIST ROLE DTO KE LIST ROLE ENTITY
        List<Role> listRoles;
        if(registrationRequest.getRoles() != null && !registrationRequest.getRoles().isEmpty()){
            listRoles = registrationRequest.getRoles()
                    .stream()
                    .map(roleName -> roleRepository.findByName(roleName.toUpperCase())
                            .orElseThrow(() -> new NotFoundException("Role name is not found")))
                    .toList();
        }else{
            Role defaultRole = roleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new NotFoundException("Role name is not found"));
            listRoles = List.of(defaultRole);
        }

        // SET DATA YANG ADA DI DTO KE USER ENTITY
        User savedUser = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .address(registrationRequest.getAddress())
                .isActive(true)
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .roles(listRoles)
                .createdAt(LocalDateTime.now())
                .build();

        // SAVE USER
        userRepository.save(savedUser);
        log.info("User created successfully");

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User created successfully")
                .build();



    }

    @Override
    public Response<?> login(LoginRequest loginRequest) {

        // CARI USER BERDASARKAN EMAIL
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("Email invalid"));


        // CEK APAKAH USER AKTIF ATAU TIDAK
        if(!user.isActive()){
            throw new BadRequestException("User is inactive");
        }

        // CEK PASSWORD USER YANG LOGIN
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Password invalid");
        }

        // BIKIN TOKEN
        String token = jwtUtils.generateToken(loginRequest.getEmail());

        // AMBIL SEMUA ROLE YANG ADA DI USER ENTITY
        List<String> roleName = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        // BIKIN LOGIN RESPONSE
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleName);

        // KEMBALIKAN RESPONSE
        return Response.builder()
                .message("Login successfully")
                .data(loginResponse)
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}
