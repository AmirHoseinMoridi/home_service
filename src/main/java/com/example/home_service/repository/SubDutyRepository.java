package com.example.home_service.repository;

import com.example.home_service.entity.Duty;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SubDutyRepository extends JpaRepository<SubDuty,Long> {

    Optional<SubDuty> findByName(String name);

    Set<SubDuty> findByDuty(Duty duty);

    //todo
    Set<SubDuty> findByExpert(Expert expert);

}
