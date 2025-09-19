package com.bintang.fullstack_food_app_react_springboot.aws;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface AwsS3Service {
    URL uploadFile(String keyName, MultipartFile file);
    void deleteFile(String keyName);
}
