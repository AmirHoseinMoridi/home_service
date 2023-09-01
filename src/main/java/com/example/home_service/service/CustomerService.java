package com.example.home_service.service;

import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Wallet;
import com.example.home_service.exception.FieldNotFoundException;

import java.util.Optional;
import java.util.Set;



public interface CustomerService {
    void signUp(Customer customer);

    void editPassword(String email,String oldPassword, String newPassword);

    Optional<Wallet> findWallet(String email, String password);

    Customer findByEmail(String email) throws FieldNotFoundException;

    Set<Customer> findAll();
}
