package com.example.home_service.repository;


import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Wallet;

import java.util.Optional;

public interface CustomerRepository extends PersonRepository<Customer> {


    Optional<Customer> findByWallet(Wallet wallet);

}
