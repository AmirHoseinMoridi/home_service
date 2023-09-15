package com.example.home_service.service.impl;

import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.RequestAction;
import com.example.home_service.exception.*;
import com.example.home_service.repository.SubDutyRepository;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.service.SubDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SubDutyServiceImpl implements SubDutyService {
    private final SubDutyRepository repository;
    private final ServiceRegistry serviceRegistry;

    @Autowired
    public SubDutyServiceImpl(SubDutyRepository repository, ServiceRegistry serviceRegistry) {
        this.repository = repository;
        this.serviceRegistry = serviceRegistry;
    }

    @Transactional
    @Override
    public void create(SubDuty subDuty, Duty duty) {
        ifExistThrowException(subDuty);
        Duty finedDuty = serviceRegistry.dutyService().findByName(duty.getName());
        subDuty.setIsActive(true);
        subDuty.setDuty(finedDuty);
        repository.save(subDuty);
    }

    private void ifExistThrowException(SubDuty subDuty) {
        boolean isExists = repository.existsByName(subDuty.getName());
        if (isExists) {
            throw new FieldAlreadyExistException("sub duty already exist !");
        }
    }


    @Transactional
    @Override
    public void update(SubDuty subDuty) {
        SubDuty finedSubDuty = findByName(subDuty.getName());
        finedSubDuty.setPrice(subDuty.getPrice());
        finedSubDuty.setDescription(subDuty.getDescription());
        repository.save(finedSubDuty);
    }


    @Transactional
    @Override
    public void remove(String subDutyName) {
        editStatusIfExists(subDutyName);
    }

    @Override
    public void removeAllSubDutiesInDuty(Duty duty) throws FieldNotFoundException {
        findSubDutiesInDuty(duty).forEach(
                subDuty -> editStatusIfExists(subDuty.getName())
        );
    }

    private void editStatusIfExists(String subDutyName) throws FieldNotFoundException {
        SubDuty subDuty = findByName(subDutyName);
        subDuty.setIsActive(false);
        Set<Expert> experts = subDuty.getExperts();
        experts.clear();
        subDuty.setExperts(experts);
        repository.save(subDuty);
    }

    @Override
    public Set<SubDuty> findSubDutiesInDuty(Duty duty) {
        Duty finedDuty = serviceRegistry.dutyService().findByName(duty.getName());
        return repository.findByDuty(finedDuty);
    }


    @Override
    public SubDuty findByName(String name) {
        Optional<SubDuty> optionalSubDuty = repository.findByName(name);
        if (optionalSubDuty.isPresent()) {
            return optionalSubDuty.get();
        } else throw new FieldNotFoundException("sub duty is not exist");
    }

    @Override
    public Set<SubDuty> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public SubDuty findById(Long id) {
        return repository.findById(id).orElseThrow();
    }


    @Transactional
    @Override
    public void addExpertToSubDuty(Long assignmentRequestsId) {
        Optional<AssignmentRequests> optionalRequest = serviceRegistry.assignmentRequestsService()
                .findById(assignmentRequestsId);
        if (optionalRequest.isEmpty()) {
            throw new FieldNotFoundException("AssignmentRequests is not exists !");
        }
        AssignmentRequests request = optionalRequest.get();
        if (request.getAction().equals(RequestAction.REMOVE)) {
            throw new WrongActionException("this AssignmentRequests is for REMOVE !");
        }
        if (!request.getIsActive()) {
            throw new NotActiveException("AssignmentRequests is not active !");
        }

        Expert expert = request.getExpert();
        SubDuty subDuty = request.getSubDuty();
        subDuty.addExpert(expert);
        repository.save(subDuty);

        serviceRegistry.assignmentRequestsService().remove(request);
    }

    @Transactional
    @Override
    public void removeExpertFromSubDuty(Long assignmentRequestsId) {
        Optional<AssignmentRequests> optional = serviceRegistry.assignmentRequestsService()
                .findById(assignmentRequestsId);
        if (optional.isEmpty()) {
            throw new FieldNotFoundException("AssignmentRequests is not exists !");
        }
        AssignmentRequests request = optional.get();
        if (request.getAction().equals(RequestAction.ADD)) {
            throw new WrongActionException("this AssignmentRequests is for ADD .");
        }
        if (!request.getIsActive()) {
            throw new NotActiveException("AssignmentRequests is not active !");
        }
        Expert expert = request.getExpert();
        SubDuty subDuty = request.getSubDuty();
        boolean isRemoved = subDuty.removeExpert(expert);
        serviceRegistry.assignmentRequestsService().remove(request);
        if (isRemoved) {
            repository.save(subDuty);
        } else {
            throw new FieldNotFoundException("this sub duty is not in this expert's sub duties !");
        }
    }


    @Override
    public Set<SubDuty> findByExpert(Expert expert) {
        return repository.findByExpert(expert);
    }

}
