package com.example.home_service.service.impl;

import com.example.home_service.dto.DutyDto;
import com.example.home_service.dto.SubDutyDto;
import com.example.home_service.entity.AssignmentRequests;
import com.example.home_service.entity.Duty;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.entity.enumaration.RequestAction;
import com.example.home_service.exception.*;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.repository.SubDutyRepository;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.service.SubDutyService;
import com.example.home_service.util.Checker;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SubDutyServiceImpl implements SubDutyService {
    private final SubDutyRepository repository;
    private final Mapper mapper;

    @Transactional
    @Override
    public void create(SubDutyDto subDutyDto, DutyDto dutyDto) {
        try {
            Checker.checkValidation(dutyDto);
            Optional<SubDuty> optionalSubDuty = save(subDutyDto);

            if (optionalSubDuty.isPresent()) {
                Duty duty = ServiceRegistry.dutyService().findByName(dutyDto.getName());
                SubDuty subDuty = optionalSubDuty.get();
                subDuty.setDuty(duty);
                repository.save(subDuty);
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Optional<SubDuty> save(SubDutyDto subDutyDto)
            throws FieldAlreadyExistException, ValidationException {

        try {
            Checker.checkValidation(subDutyDto);
            Optional<SubDuty> optionalSubDuty = repository.findByName(subDutyDto.getName());
            if (optionalSubDuty.isPresent()) {
                throw new FieldAlreadyExistException("sub duty already exist !");
            }
            SubDuty subDuty = mapper.dtoToSubDuty(subDutyDto);
            subDuty.setIsActive(true);
            repository.save(subDuty);
            return Optional.of(subDuty);
        } catch (NoResultException ignored) {
            return Optional.empty();
        }
    }


    @Transactional
    @Override
    public void update(SubDutyDto subDutyDto) {
        try {
            Checker.checkValidation(subDutyDto);
            SubDuty subDuty = findByName(subDutyDto.getName());
            subDuty.setPrice(subDutyDto.getPrice());
            subDuty.setDescription(subDutyDto.getDescription());
            repository.save(subDuty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional
    @Override
    public void remove(String subDutyName) {
        try {
            editStatus(subDutyName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllSubDutiesInDuty(Duty duty) throws FieldNotFoundException {
        DutyDto dutyDto = mapper.dutyToDto(duty);
        Set<SubDutyDto> subDuties = findSubDutiesInDuty(dutyDto);
        for (SubDutyDto subDutyDto : subDuties) {
            editStatus(subDutyDto.getName());
        }
    }

    private void editStatus(String subDutyName) throws FieldNotFoundException {
        SubDuty subDuty = findByName(subDutyName);
        subDuty.setIsActive(false);
        Set<Expert> experts = subDuty.getExperts();
        experts.clear();
        subDuty.setExperts(experts);
        repository.save(subDuty);
    }

    @Override
    public Set<SubDutyDto> findSubDutiesInDuty(DutyDto dutyDTO) {
        try {
            Checker.checkValidation(dutyDTO);
            Set<SubDutyDto> responses = new HashSet<>();
            Duty duty = ServiceRegistry.dutyService().findByName(dutyDTO.getName());
            Set<SubDuty> subDuties = repository.findByDuty(duty);
            subDuties.forEach(subDuty -> responses.add(mapper.subDutyToDto(subDuty)));
            return responses;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }


    @Override
    public SubDuty findByName(String name) throws FieldNotFoundException {
        try {
            Optional<SubDuty> optionalSubDuty = repository.findByName(name);
            if (optionalSubDuty.isPresent()) {
                return optionalSubDuty.get();
            } else throw new FieldNotFoundException("sub duty is not exist");
        } catch (NoResultException ignored) {
            throw new FieldNotFoundException("sub duty is not exist");
        }
    }

    @Override
    public Set<SubDutyDto> findAll() {
        Set<SubDutyDto> responses = new HashSet<>();
        repository.findAll().forEach(
                subDuty -> responses.add(mapper.subDutyToDto(subDuty))
        );
        return responses;
    }


    @Transactional
    @Override
    public void addExpertToSubDuty(Long assignmentRequestsId) {
        try {
            Optional<AssignmentRequests> optionalRequest = ServiceRegistry.assignmentRequestsService()
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

            ServiceRegistry.assignmentRequestsService().remove(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void removeExpertFromSubDuty(Long assignmentRequestsId) {
        try {
            Optional<AssignmentRequests> optional = ServiceRegistry.assignmentRequestsService()
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
            ServiceRegistry.assignmentRequestsService().remove(request);
            if (isRemoved) {
                repository.save(subDuty);
            } else {
                throw new FieldNotFoundException("this sub duty is not in this expert's sub duties !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Set<SubDuty> findByExpert(Expert expert) {
        return repository.findByExpert(expert);
    }

}
