package com.example.home_service.service.impl;

import com.example.home_service.base.service.Impl.BaseUserServiceImpl;
import com.example.home_service.dto.UserSearchDto;
import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.RequestAction;
import com.example.home_service.entity.enumaration.Role;
import com.example.home_service.exception.DutyOutOfRangeException;
import com.example.home_service.exception.FieldAlreadyExistException;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.repository.ExpertRepository;
import com.example.home_service.service.ExpertService;
import com.example.home_service.service.ServiceRegistry;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ExpertServiceImpl
        extends BaseUserServiceImpl<Expert, ExpertRepository>
        implements ExpertService{


    public ExpertServiceImpl(ExpertRepository repository, PasswordEncoder passwordEncoder, ServiceRegistry serviceRegistry) {
        super(repository, passwordEncoder, serviceRegistry);
    }

    @Override
    public void signUp(Expert expert,Image image) {
        if (repository.existsByUsername(expert.getUsername())) {
            throw new FieldAlreadyExistException("this email is already exist !");
        }
        Wallet wallet = serviceRegistry.walletService().createWallet();
        expert.setWallet(wallet);
        expert.setDateOfSignUp(ZonedDateTime.now());
        expert.setImage(image);
        String password = expert.getPassword();
        expert.setPassword(passwordEncoder.encode(password));
        expert.setRole(Role.ROLE_NON_ACCEPTED_EXPERT);
        expert.setPoint(5D);
        repository.save(expert);
    }


    @Override
    public void editPassword(String username, String newPassword) {
        Expert expert = repository.findByUsername(username)
                .orElseThrow(()->new FieldNotFoundException("expert not found !"));
        expert.setPassword(passwordEncoder.encode(newPassword));
        repository.save(expert);
    }

    @Override
    public void addingSubDutyRequest(String username, String subDutyName) {

        Expert expert = repository.findByUsername(username)
                .orElseThrow(()->new FieldNotFoundException("expert not found !"));

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


    private void createAssignmentRequest(Expert expert, SubDuty subDuty, RequestAction action) {
        AssignmentRequests assignmentRequests = AssignmentRequests.builder()
                .expert(expert)
                .subDuty(subDuty)
                .action(action)
                .isActive(true)
                .build();
        serviceRegistry.assignmentRequestsService().save(assignmentRequests);
    }

    @Override
    public void removingSubDutyRequest(String username, String subDutyName) {

        Expert expert = repository.findByUsername(username)
                .orElseThrow(()->new FieldNotFoundException("expert not found !"));

        SubDuty subDuty = serviceRegistry.subDutyService().findByName(subDutyName);
        Set<SubDuty> expertsSubDuties = serviceRegistry.subDutyService().findByExpert(expert);

        if (expertsSubDuties.contains(subDuty)) {
            createAssignmentRequest(expert, subDuty, RequestAction.REMOVE);
        } else throw new FieldNotFoundException("this sub duty is not exists in expert's sub duties !");
    }

    @Override
    public void subtractPoint(Expert expert, int subtract) {
        Double oldPoint = expert.getPoint();
        expert.setPoint(oldPoint - subtract);
        if (expert.getPoint() < 0) {
            expert.setRole(Role.ROLE_NON_ACCEPTED_EXPERT);
        }
        repository.save(expert);
    }

    @Override
    public void addPoint(Expert expert, int point) {
        expert.setPoint((double) point);
        repository.save(expert);
    }

    @Override
    public void acceptExpert(Long expertId) {
        Expert expert = findById(expertId);
        expert.setRole(Role.ROLE_ACCEPTED_EXPERT);
        repository.save(expert);
    }
    @Override
    public Double showPoint(Long expertId) {
        return findById(expertId).getPoint();
    }

}
