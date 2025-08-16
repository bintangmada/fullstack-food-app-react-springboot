package com.bintang.fullstack_food_app_react_springboot.auth_users.entity;

import jakarta.persistence.*;

@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;


}
