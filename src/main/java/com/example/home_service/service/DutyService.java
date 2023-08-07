package com.example.home_service.service;


import com.example.home_service.dto.DutyDto;
import com.example.home_service.entity.Duty;
import com.example.home_service.exception.FieldNotFoundException;

import java.util.Set;

public interface DutyService {
    void create(DutyDto dutyDTO);
    Set<DutyDto> findAll();

    Duty findByName(String name) throws FieldNotFoundException;

    void remove(DutyDto dutyDTO);

}
