package com.bintang.fullstack_food_app_react_springboot.auth_users.dtos;

import com.bintang.fullstack_food_app_react_springboot.role.dtos.RoleDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String profileUrl;

    // HANYA UNTUK MENULIS PASSWORD, BUKAN UNTUK MEMBACA PASSWORD
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean isActive;
    private String address;
    private List<RoleDto> roles;
    private MultipartFile imageFile;
}
