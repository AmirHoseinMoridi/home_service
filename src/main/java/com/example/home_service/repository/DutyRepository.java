package com.example.home_service.repository;


import com.example.home_service.entity.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DutyRepository extends JpaRepository<Duty,Long> {

    Optional<Duty> findByName(String name);

    boolean existsByName(String name);
}
