package com.bintang.fullstack_food_app_react_springboot.auth_users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RegistrationRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 4, message = "password must be at least 4 characters long")
    private String password;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "phone number is required")
    private String phoneNumber;

    private List<String> roles;

    public RegistrationRequest(String name, String email, String password, String address, String phoneNumber, List<String> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public RegistrationRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
