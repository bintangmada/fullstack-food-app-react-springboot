package com.bintang.fullstack_food_app_react_springboot.auth_users.repository;

import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}
