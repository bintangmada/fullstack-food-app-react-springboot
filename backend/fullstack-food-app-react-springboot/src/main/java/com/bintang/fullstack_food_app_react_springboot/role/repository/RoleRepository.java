package com.bintang.fullstack_food_app_react_springboot.role.repository;

import com.bintang.fullstack_food_app_react_springboot.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
