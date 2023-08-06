package com.example.home_service.repository;


import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Image;
import com.example.home_service.entity.Wallet;
import com.example.home_service.entity.enumaration.ExpertStatus;

import java.util.Optional;
import java.util.Set;

public interface ExpertRepository extends PersonRepository<Expert> {

    Set<Expert> findByStatus(ExpertStatus status);

    Optional<Expert> findByWallet(Wallet wallet);

    Optional<Expert> findByImage(Image image);

}
