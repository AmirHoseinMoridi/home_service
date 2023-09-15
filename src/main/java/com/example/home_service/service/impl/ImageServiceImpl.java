package com.example.home_service.service.impl;


import com.example.home_service.entity.Image;
import com.example.home_service.exception.ImageNotFoundException;
import com.example.home_service.exception.ImageSizeOutOfRangeException;
import com.example.home_service.exception.NotValidException;
import com.example.home_service.mapper.ImageMapper;
import com.example.home_service.mapper.ImageMapperImpl;
import com.example.home_service.repository.ImageRepository;
import com.example.home_service.service.ImageService;
import com.example.home_service.util.Checker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
@RequiredArgsConstructor
public class ImageServiceImpl
        implements ImageService {
    private final ImageRepository repository;
    private final ImageMapper imageMapper = new ImageMapperImpl();

    @Override
    public Image save(String imagePath)
            throws NotValidException, ImageNotFoundException, ImageSizeOutOfRangeException {

        File imageFile = imageMapper.paramToImageFile(imagePath);
        Checker.checkFileExist(imageFile);
        Checker.checkImageSize(imageFile);

        Image image = imageMapper.imageFileToImage(imageFile);
        return repository.save(image);
    }
}
