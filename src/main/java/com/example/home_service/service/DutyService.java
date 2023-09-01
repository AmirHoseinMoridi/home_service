package com.example.home_service.service;


import com.example.home_service.entity.Duty;
import com.example.home_service.exception.FieldNotFoundException;

import java.util.Set;

public interface DutyService {
    void create(Duty duty);
    Set<Duty> findAll();

    Duty findByName(String name) throws FieldNotFoundException;

    void remove(String dutyName);

    Long count();

}
