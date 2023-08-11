package com.example.home_service.service.impl;


import com.example.home_service.entity.AssignmentRequests;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.entity.enumaration.RequestAction;
import com.example.home_service.exception.FieldAlreadyExistException;
import com.example.home_service.repository.AssignmentRequestsRepository;
import com.example.home_service.service.AssignmentRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class AssignmentRequestsServiceImpl implements AssignmentRequestsService {
    private final AssignmentRequestsRepository repository;
    @Autowired
    public AssignmentRequestsServiceImpl(AssignmentRequestsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AssignmentRequests> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(AssignmentRequests assignmentRequests) throws FieldAlreadyExistException {
        Expert expert = assignmentRequests.getExpert();
        SubDuty subDuty = assignmentRequests.getSubDuty();
        RequestAction action = assignmentRequests.getAction();
        boolean isExists = repository.existsByExpertAndSubDutyAndAction(expert, subDuty, action);
        if (!isExists) {
            repository.save(assignmentRequests);
        } else throw new FieldAlreadyExistException("this request is already exists !");
    }

    @Override
    public void remove(AssignmentRequests assignmentRequests) {
        assignmentRequests.setIsActive(false);
        repository.save(assignmentRequests);
    }

    @Override
    public Set<AssignmentRequests> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public Set<AssignmentRequests> findByRequestAction(RequestAction action) {
        return repository.findByAction(action);
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
