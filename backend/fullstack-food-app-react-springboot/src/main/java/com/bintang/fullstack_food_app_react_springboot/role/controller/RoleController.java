package com.bintang.fullstack_food_app_react_springboot.role.controller;

import com.bintang.fullstack_food_app_react_springboot.response.Response;
import com.bintang.fullstack_food_app_react_springboot.role.dtos.RoleDto;
import com.bintang.fullstack_food_app_react_springboot.role.repository.RoleRepository;
import com.bintang.fullstack_food_app_react_springboot.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {

    private final RoleService roleService;
    private final RoleRepository roleRepository;

    @PostMapping
    public ResponseEntity<Response<RoleDto>> createRole(@RequestBody @Valid RoleDto roleDto){
        return ResponseEntity.ok(roleService.createRole(roleDto));
    }

    @PutMapping
    public ResponseEntity<Response<RoleDto>> updateRole(@RequestBody @Valid RoleDto roleDto){
        return ResponseEntity.ok(roleService.updateRole(roleDto));
    }

    @GetMapping
    public ResponseEntity<Response<List<RoleDto>>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long roleId){
        return ResponseEntity.ok(roleService.deleteRole(roleId));
    }
}
