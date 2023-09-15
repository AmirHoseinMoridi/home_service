package com.example.home_service.controller;

import com.example.home_service.dto.*;
import com.example.home_service.dto.Parrent.CreateSubDutyDto;
import com.example.home_service.dto.result.CustomerResultDto;
import com.example.home_service.dto.result.ExpertResultDto;
import com.example.home_service.dto.result.OrderResultDto;
import com.example.home_service.dto.result.ProposalResultDto;
import com.example.home_service.entity.Duty;
import com.example.home_service.entity.Proposal;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createDuty(@Validated @RequestBody DutyDto dutyDto) {
        Duty duty = mapper.dtoToDuty(dutyDto);
        serviceRegistry.dutyService().create(duty);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subDuty/save")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createSubDuty(@Validated @RequestBody CreateSubDutyDto createSubDutyDto) {
        Duty duty = mapper.dtoToDuty(createSubDutyDto.getDuty());
        SubDuty subDuty = mapper.dtoToSubDuty(createSubDutyDto.getSubDuty());
        serviceRegistry.subDutyService().create(subDuty, duty);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/duty/delete")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteDuty(@Validated @RequestBody DutyDto dutyDto) {
        serviceRegistry.dutyService().remove(dutyDto.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/subDuty/delete")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteSubDuty(@RequestBody DutyDto dutyDto) {
        serviceRegistry.subDutyService().remove(dutyDto.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/subDuty/edit")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> editSubDuty(@Validated @RequestBody SubDutyDto subDutyDto) {
        SubDuty subDuty = mapper.dtoToSubDuty(subDutyDto);
        serviceRegistry.subDutyService().update(subDuty);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/expert/findAll")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Set<ExpertResultDto>> findAllExperts() {
        Set<ExpertResultDto> results = new HashSet<>();
        serviceRegistry.expertService().findAll().forEach(
                expert -> results.add(mapper.expertToDto(expert))
        );
        return ResponseEntity.ok(results);
    }

    @GetMapping("/customer/findAll")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Set<CustomerResultDto>> findAllCustomers() {
        Set<CustomerResultDto> response = new HashSet<>();
        serviceRegistry.customerService().findAll().forEach(
                customer -> response.add(mapper.customerToDto(customer))
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/expert/accept")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> acceptExpert(@RequestBody IdDto idDto) {
        serviceRegistry.expertService().acceptExpert(idDto.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/subDuty/addExpert")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> addExpertToSubDuty(@RequestBody IdDto idDto) {
        serviceRegistry.subDutyService().addExpertToSubDuty(idDto.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/subDuty/removeExpert")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> removeExpertFromSubDuty(@RequestBody IdDto idDto) {
        serviceRegistry.subDutyService().removeExpertFromSubDuty(idDto.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/expert/search")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<ExpertResultDto>> expertAdvanceSearch(@RequestBody UserSearchDto dto) {
        return ResponseEntity.ok(
                serviceRegistry.expertService().doAdvanceSearch(dto)
                        .stream().map(mapper::expertToDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/customer/search")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<CustomerResultDto>> customerAdvanceSearch(@RequestBody UserSearchDto dto) {
        return ResponseEntity.ok(
                serviceRegistry.customerService().doAdvanceSearch(dto)
                        .stream().map(mapper::customerToDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/proposal/search/byExpert")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Set<ProposalResultDto>> proposalSearchByExpert(@RequestBody IdDto idDto) {
        Set<ProposalResultDto> result = new HashSet<>();
        serviceRegistry.proposalService().findByExpert(idDto.getId())
                .forEach(proposal ->result.add(
                        ProposalResultDto.builder()
                                .expertName(proposal.getExpert().getUsername())
                                .suggestedPriceByExpert(proposal.getSuggestedPriceByExpert())
                                .durationOfWork(proposal.getDurationOfWork())
                                .suggestedDate(proposal.getSuggestedDate())
                                .build()
                ));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/proposal/search/byCustomer")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Set<ProposalResultDto>> proposalSearchByCustomer(@RequestBody IdDto idDto) {
        Set<ProposalResultDto> result = new HashSet<>();
        serviceRegistry.proposalService().findByCustomerId(idDto.getId())
                .forEach(proposal ->result.add(
                        ProposalResultDto.builder()
                                .expertName(proposal.getExpert().getUsername())
                                .suggestedPriceByExpert(proposal.getSuggestedPriceByExpert())
                                .durationOfWork(proposal.getDurationOfWork())
                                .suggestedDate(proposal.getSuggestedDate())
                                .build()
                ));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/order/search")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<OrderResultDto>> orderSearch(@RequestBody OrderSearchDto dto){
        return ResponseEntity.ok(
                serviceRegistry.orderService().doAdvanceSearch(dto)
                        .stream().map(mapper::orderToDto).collect(Collectors.toList())

        );
    }
}
