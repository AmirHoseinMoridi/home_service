package com.example.home_service.service.impl;


import com.example.home_service.dto.*;
import com.example.home_service.dto.result.ExpertResultDto;
import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.ExpertStatus;
import com.example.home_service.entity.enumaration.RequestAction;
import com.example.home_service.exception.*;
import com.example.home_service.mapper.ImageMapper;
import com.example.home_service.mapper.ImageMapperImpl;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.repository.ExpertRepository;
import com.example.home_service.service.ExpertService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import jakarta.persistence.NoResultException;
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
    private final Mapper mapper;

    private final ImageMapper imageMapper = new ImageMapperImpl();
    @Autowired
    public ExpertServiceImpl(ExpertRepository repository, ServiceRegistry serviceRegistry, Mapper mapper) {
        this.repository = repository;
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void signUp(ExpertRequestDto expertRequestDTO) {
        try {
            Checker.checkValidation(expertRequestDTO);
            save(expertRequestDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


    private Expert save(ExpertRequestDto expertRequestDTO)
            throws ImageNotFoundException,ImageSizeOutOfRangeException, FieldAlreadyExistException {

        Optional<Expert> optionalExpert = repository.findByEmail(expertRequestDTO.getEmail());
        if (optionalExpert.isPresent()) {
            throw new FieldAlreadyExistException("this email is already exist !");
        }
        Expert expert = mapper.dtoToExpert(expertRequestDTO);
        Image image = serviceRegistry.imageService().save(expertRequestDTO.getImageDTO());
        Address address = serviceRegistry.addressService().save(expertRequestDTO.getAddress());
        Wallet wallet = serviceRegistry.walletService().createWallet();
        Expert filledExpert = fillExpert(expert, image, address, wallet);
        return repository.save(filledExpert);
    }


    private Expert fillExpert(Expert expert, Image image, Address address, Wallet wallet){
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
    public void editPassword(EmailAndPasswordDto emailAndPassword, NewPasswordDto newPassword) {
        try {
            Checker.checkValidation(newPassword);
            Expert expert = findByEmailAndPassword(emailAndPassword);
            expert.setPassword(newPassword.getPassword());
            repository.save(expert);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


    @Transactional
    @Override
    public void addingSubDutyRequest(EmailAndPasswordDto emailAndPassword, String subDutyName) {
        try {
            Expert expert = findByEmailAndPassword(emailAndPassword);
            Checker.checkExpertsAccess(expert);

            SubDuty subDuty = serviceRegistry.subDutyService().findByName(subDutyName);
            checkRangeOfDuty(expert, subDuty);
            createAssignmentRequest(expert, subDuty, RequestAction.ADD);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void checkRangeOfDuty(Expert expert, SubDuty subDuty) throws DutyOutOfRangeException {
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
    public void removingSubDutyRequest(EmailAndPasswordDto emailAndPassword, String subDutyName) {
        try {
            Expert expert = findByEmailAndPassword(emailAndPassword);
            Checker.checkExpertsAccess(expert);
            SubDuty subDuty = serviceRegistry.subDutyService().findByName(subDutyName);
            Set<SubDuty> expertsSubDuties = serviceRegistry.subDutyService().findByExpert(expert);

            if (expertsSubDuties.contains(subDuty)) {
                createAssignmentRequest(expert, subDuty, RequestAction.REMOVE);
            } else throw new FieldNotFoundException("this sub duty is not exists in expert's sub duties !");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<WalletDto> findWallet(EmailAndPasswordDto emailAndPassword) {
        try {
            Expert expert = findByEmailAndPassword(emailAndPassword);
            WalletDto walletDTO = mapper.walletToDto(expert.getWallet());
            return Optional.ofNullable(walletDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Set<ExpertResultDto> findAll() throws ImageNotFoundException {
        Set<ExpertResultDto> results = new HashSet<>();
        repository.findAll().forEach(
                expert -> results.add(mapper.expertToDto(expert))
        );
        return results;
    }


    @Override
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
    }


    @Transactional
    @Override
    public void acceptExpert(String expertEmail) {
        try {
            Expert expert = findByEmail(expertEmail);
            expert.setStatus(ExpertStatus.ACCEPTED);
            repository.save(expert);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


    private Expert findByEmailAndPassword(EmailAndPasswordDto param)
            throws FieldNotFoundException, WrongPasswordException {

        Checker.checkValidation(param);
        Expert expert = findByEmail(param.getEmail());

        return Optional.of(expert)
                .filter(e -> e.getPassword().equals(param.getPassword()))
                .orElseThrow(() -> new WrongPasswordException("password is wrong !"));
    }


    private Expert findByEmail(String email) throws FieldNotFoundException {
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
