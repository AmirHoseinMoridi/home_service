package com.example.home_service.mapper;


import com.example.home_service.entity.Image;
import com.example.home_service.exception.ImageNotFoundException;

import java.io.File;


public interface ImageMapper {
    String imageToImagePath(Image image) throws ImageNotFoundException;
     File paramToImageFile(String imagePath);
    Image imageFileToImage(File imageFile) throws ImageNotFoundException;
}
