package com.bintang.fullstack_food_app_react_springboot.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException (String message){
        super(message);
    }
}
