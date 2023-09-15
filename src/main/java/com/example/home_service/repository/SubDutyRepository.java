package com.example.home_service.repository;

import com.example.home_service.entity.Duty;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface SubDutyRepository
        extends JpaRepository<SubDuty,Long> {

    Optional<SubDuty> findByName(String name);
    boolean existsByName(String name);

    Set<SubDuty> findByDuty(Duty duty);

    @Query(value = "select s from SubDuty s where :expert member of s.experts")
    Set<SubDuty> findByExpert(@Param(value = "expert") Expert expert);

}
