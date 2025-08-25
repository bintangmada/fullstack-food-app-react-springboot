package com.bintang.fullstack_food_app_react_springboot.menu.repository;

import com.bintang.fullstack_food_app_react_springboot.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
}
