package com.bintang.fullstack_food_app_react_springboot.menu.service;

import com.bintang.fullstack_food_app_react_springboot.menu.dtos.MenuDto;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
    @Override
    public Response<MenuDto> addMenu(MenuDto menuDto) {
        return null;
    }

    @Override
    public Response<MenuDto> updateMenu(MenuDto menuDto) {
        return null;
    }

    @Override
    public Response<MenuDto> getMenuById(Long menuId) {
        return null;
    }

    @Override
    public Response<?> deleteMenu(Long menuId) {
        return null;
    }

    @Override
    public Response<List<MenuDto>> getMenus(Long categoryId, String search) {
        return null;
    }
}
