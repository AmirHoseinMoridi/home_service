package com.example.home_service.repository;


import com.example.home_service.entity.AssignmentRequests;
import com.example.home_service.entity.enumaration.RequestAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AssignmentRequestsRepository extends JpaRepository<AssignmentRequests,Long> {
    Set<AssignmentRequests> findByAction(RequestAction action);

    //todo
    Optional<Long> count(AssignmentRequests request);
}
