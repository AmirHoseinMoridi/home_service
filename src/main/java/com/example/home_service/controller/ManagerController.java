package com.example.home_service.controller;

import com.example.home_service.dto.Parrent.CreateSubDutyDto;
import com.example.home_service.dto.DutyDto;
import com.example.home_service.dto.SubDutyDto;
import com.example.home_service.dto.result.CustomerResultDto;
import com.example.home_service.dto.result.ExpertResultDto;
import com.example.home_service.entity.Duty;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;

    public ManagerController(ServiceRegistry serviceRegistry, Mapper mapper) {
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
    }

    @PostMapping("/duty/save")
    public void createDuty(@Validated @RequestBody DutyDto dutyDto) {
        Duty duty = mapper.dtoToDuty(dutyDto);
        serviceRegistry.dutyService().create(duty);
    }

    @PostMapping("/subDuty/save")
    public void createSubDuty(@Validated @RequestBody CreateSubDutyDto createSubDutyDto) {
        Duty duty = mapper.dtoToDuty(createSubDutyDto.getDuty());
        SubDuty subDuty = mapper.dtoToSubDuty(createSubDutyDto.getSubDuty());
        serviceRegistry.subDutyService().create(subDuty,duty);
    }

    @DeleteMapping("/duty/delete")
    public void deleteDuty(@Validated @RequestBody DutyDto dutyDto) {
        serviceRegistry.dutyService().remove(dutyDto.getName());
    }

    @DeleteMapping("/subDuty/delete")
    public void deleteSubDuty(@RequestBody DutyDto dutyDto) {
        serviceRegistry.subDutyService().remove(dutyDto.getName());
    }

    @PutMapping("/subDuty/edit")
    public void editSubDuty(@Validated @RequestBody SubDutyDto subDutyDto) {
        SubDuty subDuty = mapper.dtoToSubDuty(subDutyDto);
        serviceRegistry.subDutyService().update(subDuty);
    }

    @GetMapping("/duty/findAll")
    public Set<DutyDto> findAllDuties() {
        Set<DutyDto> responses = new HashSet<>();
        serviceRegistry.dutyService().findAll().forEach(
                duty -> responses.add(mapper.dutyToDto(duty))
        );
        return responses;
    }

    @GetMapping("/subDuty/findAll")
    public Set<SubDutyDto> findAllSubDuties() {
        Set<SubDutyDto> responses = new HashSet<>();
        serviceRegistry.subDutyService().findAll().forEach(
                subDuty -> responses.add(mapper.subDutyToDto(subDuty))
        );
        return responses;
    }

    @GetMapping("/subDuty/findAll/inDuty")
    public Set<SubDutyDto> findSubDutiesInDuty(@Validated @RequestBody DutyDto dutyDto) {
        Duty duty = mapper.dtoToDuty(dutyDto);
        Set<SubDutyDto> response = new HashSet<>();
        serviceRegistry.subDutyService().findSubDutiesInDuty(duty).forEach(
                subDuty -> response.add(mapper.subDutyToDto(subDuty))
        );
        return response;
    }

    @GetMapping("/expert/findAll")
    public Set<ExpertResultDto> findAllExperts() {
        Set<ExpertResultDto> results = new HashSet<>();
        serviceRegistry.expertService().findAll().forEach(
                expert -> results.add(mapper.expertToDto(expert))
        );
        return results;
    }

    @GetMapping("/customer/findAll")
    public Set<CustomerResultDto> findAllCustomers() {
        Set<CustomerResultDto> response = new HashSet<>();
        serviceRegistry.customerService().findAll().forEach(
                customer -> response.add(mapper.customerToDto(customer))
        );
        return response;
    }

    @PutMapping("/expert/accept")
    public void acceptExpert(String expertEmail) {
        serviceRegistry.expertService().acceptExpert(expertEmail);
    }

    @PutMapping("/subDuty/addExpert")
    public void addExpertToSubDuty(Long assignmentRequestId) {
        serviceRegistry.subDutyService().addExpertToSubDuty(assignmentRequestId);
    }

    @PutMapping("/subDuty/removeExpert")
    public void removeExpertFromSubDuty(Long assignmentRequestId) {
        serviceRegistry.subDutyService().removeExpertFromSubDuty(assignmentRequestId);
    }
}
