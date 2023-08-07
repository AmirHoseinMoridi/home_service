package com.example.home_service.service;


import com.example.home_service.dto.ImageDto;
import com.example.home_service.entity.Image;
import com.example.home_service.exception.ImageNotFoundException;
import com.example.home_service.exception.ImageSizeOutOfRangeException;
import com.example.home_service.exception.ValidationException;

import java.util.Optional;

public interface ImageService {

    Image save(ImageDto request) throws ValidationException, ImageNotFoundException, ImageSizeOutOfRangeException;
}
