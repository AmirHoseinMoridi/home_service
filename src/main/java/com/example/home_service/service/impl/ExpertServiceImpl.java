package com.example.home_service.service.impl;


import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.ExpertStatus;
import com.example.home_service.entity.enumaration.RequestAction;
import com.example.home_service.exception.*;
import com.example.home_service.repository.ExpertRepository;
import com.example.home_service.service.ExpertService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository repository;
    private final ServiceRegistry serviceRegistry;

    @Autowired
    public ExpertServiceImpl(ExpertRepository repository, ServiceRegistry serviceRegistry) {
        this.repository = repository;
        this.serviceRegistry = serviceRegistry;
    }

    @Transactional
    @Override
    public void signUp(Expert expert, Image image) {
        if (repository.existsByEmail(expert.getEmail())) {
            throw new FieldAlreadyExistException("this email is already exist !");
        }
        Address address = serviceRegistry.addressService().save(expert.getAddress());
        Wallet wallet = serviceRegistry.walletService().createWallet();
        Expert filledExpert = fillExpert(expert, image, address, wallet);
        repository.save(filledExpert);
    }


    private Expert fillExpert(Expert expert, Image image, Address address, Wallet wallet) {
        expert.setAddress(address);
        expert.setImage(image);
        expert.setWallet(wallet);
        expert.setStatus(ExpertStatus.AWAITING_CONFIRMATION);
        expert.setDateOfSignUp(LocalDate.now());
        expert.setPoint(0.0);
        return expert;
    }


    @Transactional
    @Override
    public void editPassword(String email, String oldPassword, String newPassword) {
        Expert expert = findByEmailAndPassword(email, oldPassword);
        expert.setPassword(newPassword);
        repository.save(expert);
    }


    @Transactional
    @Override
    public void addingSubDutyRequest(String email, String password, String subDutyName) {

        Expert expert = findByEmailAndPassword(email, password);
        Checker.checkExpertsAccess(expert);

        SubDuty subDuty = serviceRegistry.subDutyService().findByName(subDutyName);
        checkRangeOfDuty(expert, subDuty);
        createAssignmentRequest(expert, subDuty, RequestAction.ADD);

    }

    private void checkRangeOfDuty(Expert expert, SubDuty subDuty) {
        Set<SubDuty> expertSubDuties = serviceRegistry.subDutyService().findByExpert(expert);
        if (!expertSubDuties.isEmpty()) {
            SubDuty firstSubDuty = expertSubDuties.stream().findFirst().get();
            if (!firstSubDuty.getDuty().equals(subDuty.getDuty())) {
                throw new DutyOutOfRangeException("this subDuty is not in range of this experts duty");
            }
        }
    }

    @Transactional
    @Override
    public void removingSubDutyRequest(String email, String password, String subDutyName) {

        Expert expert = findByEmailAndPassword(email, password);
        Checker.checkExpertsAccess(expert);
        SubDuty subDuty = serviceRegistry.subDutyService().findByName(subDutyName);
        Set<SubDuty> expertsSubDuties = serviceRegistry.subDutyService().findByExpert(expert);

        if (expertsSubDuties.contains(subDuty)) {
            createAssignmentRequest(expert, subDuty, RequestAction.REMOVE);
        } else throw new FieldNotFoundException("this sub duty is not exists in expert's sub duties !");

    }

    @Override
    public Optional<Wallet> findWallet(String email, String password) {
        Expert expert = findByEmailAndPassword(email, password);
        return Optional.ofNullable(expert.getWallet());
    }


    @Override
    public Set<Expert> findAll() throws ImageNotFoundException {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public void subtractPoint(Expert expert, int subtract) {
        Double oldPoint = expert.getPoint();
        expert.setPoint(oldPoint - subtract);
        if (expert.getPoint() < 0) {
            expert.setStatus(ExpertStatus.AWAITING_CONFIRMATION);
        }
        repository.save(expert);
    }


/*    @Override
    public Set<ExpertResultDto> findByStatus(ExpertStatus expertStatus) {
        try {
            Set<ExpertResultDto> responses = new HashSet<>();
            repository.findByStatus(expertStatus).forEach(
                    expert -> responses.add(mapper.expertToDto(expert))
            );
            return responses;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }*/


    @Transactional
    @Override
    public void acceptExpert(String expertEmail) {
        Expert expert = findByEmailOrThrowException(expertEmail);
        expert.setStatus(ExpertStatus.ACCEPTED);
        repository.save(expert);
    }


    public Expert findByEmailAndPassword(String email, String password)
            throws FieldNotFoundException, WrongPasswordException {

        Expert expert = findByEmailOrThrowException(email);

        return Optional.of(expert)
                .filter(e -> e.getPassword().equals(password))
                .orElseThrow(() -> new WrongPasswordException("password is wrong !"));
    }

    @Override
    public Long count() {
        return repository.count();
    }


    private Expert findByEmailOrThrowException(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new FieldNotFoundException("expert is not exists!"));
    }

    private void createAssignmentRequest(Expert expert, SubDuty subDuty, RequestAction action)
            throws FieldAlreadyExistException {
        AssignmentRequests assignmentRequests = AssignmentRequests.builder()
                .expert(expert)
                .subDuty(subDuty)
                .action(action)
                .isActive(true)
                .build();
        serviceRegistry.assignmentRequestsService().save(assignmentRequests);
    }
}
