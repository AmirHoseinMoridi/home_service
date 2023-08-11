package com.example.home_service.service;

import com.example.home_service.dto.EmailAndPasswordDto;
import com.example.home_service.dto.NewPasswordDto;
import com.example.home_service.dto.CustomerRequestDto;
import com.example.home_service.dto.WalletDto;
import com.example.home_service.dto.result.CustomerResultDto;
import com.example.home_service.entity.Customer;
import com.example.home_service.exception.FieldNotFoundException;

import java.util.Optional;
import java.util.Set;



public interface CustomerService {
    Customer signUp(CustomerRequestDto customerRequestDTO);

    void editPassword(EmailAndPasswordDto emailAndPassword, NewPasswordDto newPassword);

    Optional<WalletDto> findWallet(EmailAndPasswordDto request);

    Customer findByEmail(String email) throws FieldNotFoundException;

    Set<CustomerResultDto> findAll();
}
