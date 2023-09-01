package com.example.home_service.repository;


import com.example.home_service.entity.Address;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Image;
import com.example.home_service.entity.Wallet;
import com.example.home_service.entity.enumaration.ExpertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface ExpertRepository extends JpaRepository<Expert,Long> {

    boolean existsByEmail(String email);
    Set<Expert> findByFirstName(String firstName);

    Set<Expert> findByLastName(String lastName);

    Optional<Expert> findByEmail(String email);

    Set<Expert> findByPassword(String password);

    Set<Expert> findByDateOfSignUp(LocalDate dateOfSignUp);

    Set<Expert> findByAddress(Address address);

    Set<Expert> findByStatus(ExpertStatus status);

    Optional<Expert> findByWallet(Wallet wallet);

    Optional<Expert> findByImage(Image image);

}
