package com.bintang.fullstack_food_app_react_springboot.exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }
}
