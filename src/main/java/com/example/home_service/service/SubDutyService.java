package com.example.home_service.service;

import com.example.home_service.entity.Duty;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.exception.FieldNotFoundException;

import java.util.Set;

public interface SubDutyService {
    void create(SubDuty subDuty, Duty duty);

    Set<SubDuty> findAll();

    Set<SubDuty> findSubDutiesInDuty(Duty duty);

    void update(SubDuty subDuty);

    void remove(String subDutyName);
    void removeAllSubDutiesInDuty(Duty duty) throws FieldNotFoundException;

    SubDuty findByName(String name) throws FieldNotFoundException;

    void addExpertToSubDuty(Long assignmentRequestsId);

    void removeExpertFromSubDuty(Long assignmentRequestsId);

    Set<SubDuty> findByExpert(Expert expert);
}
