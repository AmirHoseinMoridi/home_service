package com.example.home_service.controller;

import com.example.home_service.dto.*;
import com.example.home_service.dto.Parrent.AddProposalDto;
import com.example.home_service.dto.result.OrderResultDto;
import com.example.home_service.entity.Order;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;

    public ExpertController(@Validated @RequestBody ServiceRegistry serviceRegistry, Mapper mapper) {
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
    }

    @PutMapping("/editPassword")
    @PreAuthorize("hasRole('ROLE_ACCEPTED_EXPERT')")
    public ResponseEntity<?> editPassword(@Validated @RequestBody NewPasswordDto newPasswordDto,Principal principal) {
        serviceRegistry.expertService().editPassword(
                 principal.getName(),newPasswordDto.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findWallet")
    @PreAuthorize("hasRole('ROLE_ACCEPTED_EXPERT')")
    public WalletDto findWallet(Principal principal) {
        return serviceRegistry.expertService().findWallet(principal.getName())
                .map(mapper::walletToDto)
                .orElse(new WalletDto());
    }

    @PostMapping("/add/subDuty")
    @PreAuthorize("hasRole('ROLE_ACCEPTED_EXPERT')")
    public ResponseEntity<?> addSbDutyRequest(@Validated @RequestBody DutyDto request, Principal principal){
        serviceRegistry.expertService().addingSubDutyRequest(
                principal.getName(), request.getName()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/subDuty")
    @PreAuthorize("hasRole('ROLE_ACCEPTED_EXPERT')")
    public ResponseEntity<?> removeSbDutyRequest(@Validated @RequestBody DutyDto request, Principal principal){
        serviceRegistry.expertService().removingSubDutyRequest(
                principal.getName(),request.getName()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/proposal")
    @PreAuthorize("hasRole('ROLE_ACCEPTED_EXPERT')")
    public ResponseEntity<?> addProposal(@Validated @RequestBody ProposalDto proposalDto, Principal principal){

        serviceRegistry.proposalService().add(principal.getName(), proposalDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/orders")
    @PreAuthorize("hasRole('ROLE_ACCEPTED_EXPERT')")
    public ResponseEntity<Set<OrderResultDto>> findOrders(Principal principal){
        return ResponseEntity.ok(
                serviceRegistry.orderService().findOrders(principal.getName())
                        .stream().map(mapper::orderToDto).collect(Collectors.toSet()
                        )
        );
    }
}
