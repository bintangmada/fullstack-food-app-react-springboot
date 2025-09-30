package com.bintang.fullstack_food_app_react_springboot.menu.service;

import com.bintang.fullstack_food_app_react_springboot.menu.dtos.MenuDto;
import com.bintang.fullstack_food_app_react_springboot.menu.entity.Menu;
import com.bintang.fullstack_food_app_react_springboot.response.Response;

import java.util.List;

public interface MenuService {

    Response<MenuDto> createMenu(MenuDto menuDto);
    Response<MenuDto> updateMenu(MenuDto menuDto);
    Response<MenuDto> getMenuById(Long menuId);
    Response<?> deleteMenu(Long menuId);
    Response<List<MenuDto>> getMenus(Long categoryId, String search);
}
