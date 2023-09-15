package com.example.home_service.service;


import com.example.home_service.entity.Image;
import com.example.home_service.exception.ImageNotFoundException;
import com.example.home_service.exception.ImageSizeOutOfRangeException;
import com.example.home_service.exception.NotValidException;

public interface ImageService {

    Image save(String imagePath) ;
}
