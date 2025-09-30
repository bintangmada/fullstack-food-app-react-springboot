package com.bintang.fullstack_food_app_react_springboot.menu.controller;

import com.bintang.fullstack_food_app_react_springboot.menu.dtos.MenuDto;
import com.bintang.fullstack_food_app_react_springboot.menu.service.MenuService;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<MenuDto>> createMenu(
            @ModelAttribute @Valid MenuDto menuDto,
            @RequestPart(value = "imageFile", required = true)MultipartFile imageFile){
        menuDto.setImageFile(imageFile);
        return ResponseEntity.ok(menuService.createMenu(menuDto));
    }
}
