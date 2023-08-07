package com.example.home_service.service.impl;


import com.example.home_service.dto.DutyDto;
import com.example.home_service.entity.Duty;
import com.example.home_service.exception.FieldAlreadyExistException;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.repository.DutyRepository;
import com.example.home_service.service.DutyService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DutyServiceImpl implements DutyService {
    private final DutyRepository repository;
    private final Mapper mapper;


    @Transactional
    @Override
    public void create(DutyDto dutyDTO) {
        try {
            Checker.checkValidation(dutyDTO);
            Duty duty = mapper.dtoToDuty(dutyDTO);
            save(duty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void save(Duty duty) throws FieldAlreadyExistException {
        try {
            Optional<Duty> optionalDuty = repository.findByName(duty.getName());
            if (optionalDuty.isPresent()) {
                throw new FieldAlreadyExistException("this duty is already exists !");
            }
            duty.setIsActive(true);
            repository.save(duty);
        } catch (NoResultException ignored) {
        }
    }


    @Override
    public Set<DutyDto> findAll() {
        Set<DutyDto> responses = new HashSet<>();
        repository.findAll().forEach(duty -> responses.add(mapper.dutyToDto(duty)));
        return responses;
    }


    @Override
    public Duty findByName(String name)
            throws FieldNotFoundException {

        try {
            Optional<Duty> optionalDuty = repository.findByName(name);
            if (optionalDuty.isPresent()) {
                return optionalDuty.get();
            } else throw new FieldNotFoundException("duty is not exists");
        } catch (NoResultException ignored) {
            throw new FieldNotFoundException("duty is not exists");
        }
    }


    @Transactional
    @Override
    public void remove(DutyDto dutyDTO) {
        try {
            Checker.checkValidation(dutyDTO);
            Duty duty = findByName(dutyDTO.getName());
            ServiceRegistry.subDutyService().removeAllSubDutiesInDuty(duty);
            duty.setIsActive(false);
            repository.save(duty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
