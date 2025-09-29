package com.bintang.fullstack_food_app_react_springboot.menu.service;

import com.bintang.fullstack_food_app_react_springboot.aws.AwsS3Service;
import com.bintang.fullstack_food_app_react_springboot.category.entity.Category;
import com.bintang.fullstack_food_app_react_springboot.category.repository.CategoryRepository;
import com.bintang.fullstack_food_app_react_springboot.exceptions.BadRequestException;
import com.bintang.fullstack_food_app_react_springboot.exceptions.NotFoundException;
import com.bintang.fullstack_food_app_react_springboot.menu.dtos.MenuDto;
import com.bintang.fullstack_food_app_react_springboot.menu.entity.Menu;
import com.bintang.fullstack_food_app_react_springboot.menu.repository.MenuRepository;
import com.bintang.fullstack_food_app_react_springboot.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final AwsS3Service awsS3Service;
    @Override
    public Response<MenuDto> addMenu(MenuDto menuDto) {

        log.info("Inside addMenu()");

        Category category = categoryRepository.findById(menuDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category is not found"));

        String imageUrl = null;

        MultipartFile imageFile = menuDto.getImageFile();
        if(imageFile == null || imageFile.isEmpty()){
            throw new BadRequestException("Menu image is required");
        }

        String imageName = UUID.randomUUID() + "_"+imageFile.getOriginalFilename();
        URL s3Url = awsS3Service.uploadFile("menus/"+imageName, imageFile);
        imageUrl = s3Url.toString();

        Menu menu = Menu.builder()
                .name(menuDto.getName())
                .description(menuDto.getDescription())
                .price(menuDto.getPrice())
                .imageUrl(imageUrl)
                .category(category)
                .build();

        Menu savedMenu = menuRepository.save(menu);

        return Response.<MenuDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menu created successfully")
                .data(modelMapper.map(savedMenu, MenuDto.class))
                .build();
    }

    @Override
    public Response<MenuDto> updateMenu(MenuDto menuDto) {

        log.info("Inside updateMenu()");

        Menu existingMenu = menuRepository.findById(menuDto.getId())
                .orElseThrow(() -> new NotFoundException("Menu is not found"));

        Category category = categoryRepository.findById(menuDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Menu is not found"));

        // LANJUT LAGI

        return null;
    }

    @Override
    public Response<MenuDto> getMenuById(Long menuId) {
        log.info("Inside getMenuById()");
        return null;
    }

    @Override
    public Response<?> deleteMenu(Long menuId) {
        log.info("Inside deleteMenu()");
        return null;
    }

    @Override
    public Response<List<MenuDto>> getMenus(Long categoryId, String search) {
        log.info("Inside getMenus()");
        return null;
    }
}
