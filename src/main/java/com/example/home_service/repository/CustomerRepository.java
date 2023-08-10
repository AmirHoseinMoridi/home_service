package com.example.home_service.repository;


import com.example.home_service.entity.Address;
import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Set<Customer> findByFirstName(String firstName);

    Set<Customer> findByLastName(String lastName);

    Optional<Customer> findByEmail(String email);

    Set<Customer> findByPassword(String password);

    Set<Customer> findByDateOfSignUp(LocalDate dateOfSignUp);

    Set<Customer> findByAddress(Address address);
    Optional<Customer> findByWallet(Wallet wallet);

}
