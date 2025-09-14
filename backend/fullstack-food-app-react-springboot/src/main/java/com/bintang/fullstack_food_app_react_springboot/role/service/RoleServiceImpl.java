package com.bintang.fullstack_food_app_react_springboot.role.service;

import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.role.dtos.RoleDto;
import com.bintang.fullstack_food_app_react_springboot.role.entity.Role;
import com.bintang.fullstack_food_app_react_springboot.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response<RoleDto> createRole(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        Role savedRole = roleRepository.save(role);

        return Response.<RoleDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Role created")
                .data(modelMapper.map(savedRole, RoleDto.class))
                .build();
    }

    @Override
    public Response<RoleDto> updateRole(RoleDto roleDto) {
        return null;
    }

    @Override
    public Response<List<RoleDto>> getAllRoles() {
        return null;
    }

    @Override
    public Response<?> deleteRole(Long roleId) {
        return null;
    }
}
