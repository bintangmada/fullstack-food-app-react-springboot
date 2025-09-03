package com.bintang.fullstack_food_app_react_springboot.review.repository;

import com.bintang.fullstack_food_app_react_springboot.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM REVIEWS WHERE MENU_ID = :menuId ORDER BY ID DESC", nativeQuery = true)
    List<Review> findByMenuIdOrderByIdDesc(@Param("menuId") Long menuId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.menu.id = :menuId")
    Double calculateAverageRatingByMenuId(@Param("menuId") Long menuId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Review r " +
            "WHERE r.user.id = :userId " +
            "AND r.menu.id = :menuId " +
            "AND r.orderId = :orderId")
    boolean existsByUserIdAndMenuIdAndOrderId(
            @Param("userId") Long userId,
            @Param("menuId") Long menuId,
            @Param("orderId") Long orderId
    );
}
