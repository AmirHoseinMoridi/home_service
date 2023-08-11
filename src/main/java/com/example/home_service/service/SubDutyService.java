package com.example.home_service.service;

import com.example.home_service.dto.DutyDto;
import com.example.home_service.dto.SubDutyDto;
import com.example.home_service.entity.Duty;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.exception.FieldNotFoundException;

import java.util.Set;

public interface SubDutyService {
    SubDuty create(SubDutyDto subDutyDTO, DutyDto dutyDTO);

    Set<SubDutyDto> findAll();

    Set<SubDutyDto> findSubDutiesInDuty(DutyDto dutyDTO);

    SubDuty update(SubDutyDto subDutyDTO);

    void remove(String subDutyName);
    void removeAllSubDutiesInDuty(Duty duty) throws FieldNotFoundException;

    SubDuty findByName(String name) throws FieldNotFoundException;

    boolean addExpertToSubDuty(Long assignmentRequestsId);

    boolean removeExpertFromSubDuty(Long assignmentRequestsId);

    Set<SubDuty> findByExpert(Expert expert);
}
