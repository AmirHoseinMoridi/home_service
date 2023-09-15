package com.example.home_service.repository;


import com.example.home_service.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository
        extends JpaRepository<Image,Long> {

    Optional<Image> findByData(String data);

}
