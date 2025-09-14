package com.bintang.fullstack_food_app_react_springboot.role.service;

import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.role.dtos.RoleDto;

import java.util.List;

public interface RoleService {

    Response<RoleDto> createRole(RoleDto roleDto);
    Response<RoleDto> updateRole(RoleDto roleDto);
    Response<List<RoleDto>> getAllRoles();
    Response<?> deleteRole(Long roleId);
}
