package com.example.home_service.service;

import com.example.home_service.entity.Duty;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.SubDuty;

import java.util.Set;

public interface SubDutyService {
    void create(SubDuty subDuty, Duty duty);

    Set<SubDuty> findAll();
    SubDuty findById(Long id);

    Set<SubDuty> findSubDutiesInDuty(Duty duty);

    void update(SubDuty subDuty);

    void remove(String subDutyName);
    void removeAllSubDutiesInDuty(Duty duty) ;

    SubDuty findByName(String name) ;

    void addExpertToSubDuty(Long assignmentRequestsId);

    void removeExpertFromSubDuty(Long assignmentRequestsId);

    Set<SubDuty> findByExpert(Expert expert);
}
