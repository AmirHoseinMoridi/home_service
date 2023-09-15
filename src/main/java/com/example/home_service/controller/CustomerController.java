package com.example.home_service.controller;

import com.example.home_service.dto.*;
import com.example.home_service.dto.result.ProposalResultDto;
import com.example.home_service.entity.Proposal;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.service.ServiceRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;


    @PutMapping("/editPassword")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> editPassword(@Validated @RequestBody NewPasswordDto newPasswordDto, Principal principal) {
        serviceRegistry.customerService().editPassword(
                principal.getName(), newPasswordDto.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findWallet")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public WalletDto findWallet(Principal principal) {
        return serviceRegistry.customerService().findWallet(principal.getName())
                .map(mapper::walletToDto)
                .orElse(new WalletDto());
    }

    @PostMapping("/add/order")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> addOrder(@Validated @RequestBody OrderDto orderDto, Principal principal) {
        serviceRegistry.orderService().addOrder(orderDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/start/order")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> startOrder(@RequestBody IdDto orderID) {
        serviceRegistry.orderService().start(orderID.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/done/order")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> doneOrder(@Validated @RequestBody CommentDto commentDto) {
        serviceRegistry.orderService().done(commentDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/payment/Wallet")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> paymentWithWallet(@RequestBody IdDto proposalId) {
        serviceRegistry.proposalService().paymentWithWallet(proposalId.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/payment/CreditCard")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> paymentWithWallet(@Validated @RequestBody CreditCardDto creditCardDto) {
        serviceRegistry.proposalService().paymentWithCreditCard(creditCardDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/select/Proposal")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> selectProposal(@RequestBody IdDto proposalId) {
        serviceRegistry.proposalService().select(proposalId.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/proposal")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Set<ProposalResultDto>> findProposals(@RequestBody IdDto idDto) {
        Set<ProposalResultDto> response = new HashSet<>();
        serviceRegistry.proposalService().findByOrderId(idDto.getId()).forEach(
                proposal -> response.add(
                        ProposalResultDto
                                .builder()
                                .durationOfWork(proposal.getDurationOfWork())
                                .suggestedDate(proposal.getSuggestedDate())
                                .suggestedPriceByExpert(proposal.getSuggestedPriceByExpert())
                                .expertName(proposal.getExpert().getUsername())
                                .build()
                ));
        return ResponseEntity.ok(response);
    }

}
