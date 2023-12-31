package com.example.home_service.service.impl;


import com.example.home_service.entity.Duty;
import com.example.home_service.exception.FieldAlreadyExistException;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.repository.DutyRepository;
import com.example.home_service.service.DutyService;
import com.example.home_service.service.ServiceRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DutyServiceImpl
        implements DutyService {
    private final DutyRepository repository;
    private final ServiceRegistry serviceRegistry;


    @Transactional
    @Override
    public void create(Duty duty) {
        ifPresentThrowException(duty);
        duty.setIsActive(true);
        repository.save(duty);
    }

    private void ifPresentThrowException(Duty duty) {
        boolean isPresent = repository.existsByName(duty.getName());
        if (isPresent) {
            throw new FieldAlreadyExistException("this duty is already exists !");
        }
    }

    @Override
    public Set<Duty> findAll() {
        return new HashSet<>(repository.findAll());
    }


    @Override
    public Duty findByName(String name) throws FieldNotFoundException {
        Optional<Duty> optionalDuty = repository.findByName(name);
        if (optionalDuty.isPresent()) {
            return optionalDuty.get();
        } else throw new FieldNotFoundException("duty is not exists");
    }


    @Transactional
    @Override
    public void remove(String dutyName) {
        Duty finedDuty = findByName(dutyName);
        serviceRegistry.subDutyService().removeAllSubDutiesInDuty(finedDuty);
        finedDuty.setIsActive(false);
        repository.save(finedDuty);
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
