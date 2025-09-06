package com.bintang.fullstack_food_app_react_springboot.exceptions;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException(String message){
        super(message);
    }
}
