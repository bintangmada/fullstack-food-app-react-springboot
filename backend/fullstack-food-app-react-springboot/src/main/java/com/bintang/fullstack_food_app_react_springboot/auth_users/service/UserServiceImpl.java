package com.bintang.fullstack_food_app_react_springboot.auth_users.service;

import com.bintang.fullstack_food_app_react_springboot.auth_users.dtos.UserDto;
import com.bintang.fullstack_food_app_react_springboot.auth_users.entity.User;
import com.bintang.fullstack_food_app_react_springboot.auth_users.repository.UserRepository;
import com.bintang.fullstack_food_app_react_springboot.aws.AwsS3Service;
import com.bintang.fullstack_food_app_react_springboot.email_notification.services.NotificationService;
import com.bintang.fullstack_food_app_react_springboot.exceptions.BadRequestException;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
    private final AwsS3Service awsS3Service;

    @Override
    public User getCurrentLoggedInUser() {

        // AMBIL EMAIL DARI SECURITY CONTEXT HOLDER
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email not found"));

    }

    @Override
    public Response<List<UserDto>> getAllUsers() {
        log.info("insife getAllUsers()");
        List<User> listUser = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<UserDto> listUserDto = modelMapper.map(listUser, new TypeToken<List<UserDto>>(){}.getType());

        return Response.<List<UserDto>>builder()
                .message("All user are retrieved")
                .statusCode(HttpStatus.OK.value())
                .data(listUserDto)
                .build();
    }

    @Override
    public Response<UserDto> getOwnAccountDetails() {
        log.info("Inside getOwnAccountDetails()");
        User currentUserLogin = getCurrentLoggedInUser();

        UserDto currentUserLoginDto = modelMapper.map(currentUserLogin, UserDto.class);

        return Response.<UserDto>builder()
                .message("Own Account Details")
                .statusCode(HttpStatus.OK.value())
                .data(currentUserLoginDto)
                .build();
    }

    @Override
    public Response<?> updateOwnAccount(UserDto userDto) {

        log.info("Inside updateOwnAccount()");

        // Mendapatkan user yang sedang login
        User user = getCurrentLoggedInUser();

        // get profile url
        String profileUrl = user.getProfileUrl();

        // ambil image file dari userDto
        MultipartFile imageFile = userDto.getImageFile();

        // cek image
        if(imageFile != null && !imageFile.isEmpty()){

            // hapus jika sudah ada image sebelumnya
            if(profileUrl != null && !profileUrl.isEmpty()){
                String keyName = profileUrl.substring(profileUrl.lastIndexOf("/")+1);
                awsS3Service.deleteFile("profile/"+keyName);
                log.info("Deleted old profile image from s3");
            }

            // upload new image
            String imageName = UUID.randomUUID().toString()+"_"+imageFile.getOriginalFilename();
            URL newImageUrl = awsS3Service.uploadFile("profile/"+imageName, imageFile);
            user.setProfileUrl(newImageUrl.toString());
        }

        // update user details
        if(userDto.getName() != null) user.setName(user.getName());
        if(userDto.getPhoneNumber() != null) user.setPhoneNumber(userDto.getPhoneNumber());
        if(userDto.getAddress() != null) user.setAddress(userDto.getAddress());
        if(userDto.getPassword() != null) user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if(userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())){
            if(userRepository.existsByEmail(userDto.getEmail())){
                throw new BadRequestException("Email already exists");
            }
        }
        user.setEmail(userDto.getEmail());

        userRepository.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Account updated successfully")
                .build();
    }

    @Override
    public Response<?> deactivateOwnAccount() {
        return null;
    }
}
