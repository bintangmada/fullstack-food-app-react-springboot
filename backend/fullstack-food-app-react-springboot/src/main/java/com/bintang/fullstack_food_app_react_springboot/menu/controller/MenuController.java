package com.bintang.fullstack_food_app_react_springboot.menu.controller;

import com.bintang.fullstack_food_app_react_springboot.menu.dtos.MenuDto;
import com.bintang.fullstack_food_app_react_springboot.menu.service.MenuService;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import jakarta.mail.Multipart;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<MenuDto>> createMenu(
            @ModelAttribute @Valid MenuDto menuDto,
            @RequestPart(value = "imageFile", required = true) MultipartFile imageFile) {
        menuDto.setImageFile(imageFile);
        return ResponseEntity.ok(menuService.createMenu(menuDto));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<MenuDto>> updateMenu(
            @ModelAttribute @Valid MenuDto menuDto,
            @RequestPart(value = "imageFile", required = false) Multipart imageFile) {
        menuDto.setImageFile(menuDto.getImageFile());
        return ResponseEntity.ok(menuService.updateMenu(menuDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> getMenuById(@PathVariable("id") Long menuId){
        return ResponseEntity.ok(menuService.getMenuById(menuId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteMenuById(@PathVariable("id") Long menuId){
        return ResponseEntity.ok(menuService.deleteMenu(menuId));
    }

    @GetMapping
    public ResponseEntity<Response<List<MenuDto>>> getMenus(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search){
        return ResponseEntity.ok(menuService.getMenus(categoryId, search));
    }

}
