package com.example.home_service.service;


import com.example.home_service.entity.AssignmentRequests;
import com.example.home_service.entity.enumaration.RequestAction;
import com.example.home_service.exception.FieldAlreadyExistException;

import java.util.Optional;
import java.util.Set;

public interface AssignmentRequestsService {

    Optional<AssignmentRequests> findById(Long id);
    void save(AssignmentRequests assignmentRequests) ;
    void remove(AssignmentRequests assignmentRequests);
    Set<AssignmentRequests> findAll();
    Set<AssignmentRequests> findByRequestAction(RequestAction action);
    Long count();
}
