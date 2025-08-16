package com.bintang.fullstack_food_app_react_springboot.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.io.Serializable;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private int statusCode;
    private String message;
    private T data;
    private Map<String, Serializable> meta;

    public Response(int statusCode, String message, T data, Map<String, Serializable> meta) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.meta = meta;
    }

    public Response() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Serializable> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Serializable> meta) {
        this.meta = meta;
    }
}
