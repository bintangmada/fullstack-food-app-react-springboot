package com.bintang.fullstack_food_app_react_springboot.role.service;

import com.bintang.fullstack_food_app_react_springboot.exceptions.BadRequestException;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
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

import java.util.Collections;
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
        Role existingRole = roleRepository.findById(roleDto.getId())
                .orElseThrow(() -> new NotFoundException("Role not found"));

        if(roleRepository.findByName(roleDto.getName()).isPresent()){
            throw new BadRequestException("Role with name already exists");
        }

        existingRole.setName(roleDto.getName());

        Role updatedRole = roleRepository.save(existingRole);
        RoleDto updatedRoleDto = modelMapper.map(updatedRole, RoleDto.class);

        return Response.<RoleDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role successfully updated")
                .data(updatedRoleDto)
                .build();

    }

    @Override
    public Response<List<RoleDto>> getAllRoles() {
        List<Role> listRoles = roleRepository.findAll();

        if(listRoles.isEmpty() || listRoles == null){
            return Response.<List<RoleDto>>builder()
                    .message("Role is empty")
                    .statusCode(HttpStatus.NO_CONTENT.value())
                    .data(Collections.EMPTY_LIST)
                    .build();
        }

        List<RoleDto> listRoleDto = listRoles
                .stream()
                .map(r -> modelMapper.map(r, RoleDto.class))
                .toList();

        return Response.<List<RoleDto>>builder()
                .message("All role")
                .statusCode(HttpStatus.OK.value())
                .data(listRoleDto)
                .build();
    }

    @Override
    public Response<?> deleteRole(Long roleId) {
        if(roleRepository.findById(roleId) == null || !roleRepository.existsById(roleId)){
            throw new NotFoundException("Role is not found");
        }

        roleRepository.deleteById(roleId);

        return Response.builder()
                .message("Role deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}
