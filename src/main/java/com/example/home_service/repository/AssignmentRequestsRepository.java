package com.example.home_service.repository;


import com.example.home_service.entity.AssignmentRequests;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.entity.enumaration.RequestAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AssignmentRequestsRepository extends JpaRepository<AssignmentRequests, Long> {
    Set<AssignmentRequests> findByAction(RequestAction action);

    boolean existsByExpertAndSubDutyAndAction(Expert expert, SubDuty subDuty, RequestAction action);
}
