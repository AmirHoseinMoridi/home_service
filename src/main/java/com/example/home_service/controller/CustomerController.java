package com.example.home_service.controller;

import com.example.home_service.dto.*;
import com.example.home_service.entity.Customer;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;

    public CustomerController(ServiceRegistry serviceRegistry, Mapper mapper) {
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
    }

    @PostMapping("/signUp")
    public void signUp(@Validated @RequestBody CustomerRequestDto customerRequestDto){
        Customer customer = mapper.dtoToCustomer(customerRequestDto);
        serviceRegistry.customerService().signUp(customer);
    }

    @PutMapping("/editPassword")
    public void editPassword(@Validated @RequestBody NewPasswordDto newPasswordDto){
        serviceRegistry.customerService().editPassword(newPasswordDto.getEmail(),
                newPasswordDto.getPassword(),newPasswordDto.getNewPassword());
    }

    @GetMapping("/findWallet")
    public WalletDto findWallet(@Validated @RequestBody EmailAndPasswordDto request){
        return serviceRegistry.customerService()
                .findWallet(request.getEmail(), request.getPassword())
                .map(mapper::walletToDto)
                .orElse(new WalletDto());
    }

    @PostMapping("/add/order")
    public void addOrder(@Validated @RequestBody OrderDto orderDto){
        serviceRegistry.orderService().addOrder(orderDto);
    }

    @PutMapping("/start/order")
    public void startOrder( @RequestBody Long orderID){
        serviceRegistry.orderService().start(orderID);
    }

    @PutMapping("/done/order")
    public void doneOrder(@Validated @RequestBody CommentDto commentDto){
        serviceRegistry.orderService().done(commentDto);
    }

    @PutMapping("/payment/Wallet")
    public void paymentWithWallet( @RequestBody Long proposalId){
        serviceRegistry.proposalService().paymentWithWallet(proposalId);
    }

    @PutMapping("/payment/CreditCard")
    public void paymentWithWallet(@Validated @RequestBody CreditCardDto creditCardDto){
        serviceRegistry.proposalService().paymentWithCreditCard(creditCardDto);
    }

    @PutMapping("/select/Proposal")
    public void selectProposal(Long proposalId){
        serviceRegistry.proposalService().select(proposalId);
    }

}
