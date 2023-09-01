package com.example.home_service.service.impl;

import com.example.home_service.entity.Address;
import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Wallet;
import com.example.home_service.exception.FieldAlreadyExistException;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.exception.WrongPasswordException;
import com.example.home_service.repository.CustomerRepository;
import com.example.home_service.service.CustomerService;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository repository;
    private final ServiceRegistry serviceRegistry;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, ServiceRegistry serviceRegistry) {
        this.repository = repository;
        this.serviceRegistry = serviceRegistry;
    }

    @Transactional
    @Override
    public void signUp(Customer customer) {
        if (repository.existsByEmail(customer.getEmail())) {
            throw new FieldAlreadyExistException("this email is already exist !");
        }

        Address address = serviceRegistry.addressService().save(customer.getAddress());
        Wallet wallet = serviceRegistry.walletService().createWallet();

        customer.setWallet(wallet);
        customer.setDateOfSignUp(LocalDate.now());
        customer.setAddress(address);

        repository.save(customer);
    }


    @Transactional()
    @Override
    public void editPassword(String email, String oldPassword, String newPassword) {
        Customer customer = findByEmailAndPassword(email, oldPassword);
        customer.setPassword(newPassword);
        repository.save(customer);
    }

    @Override
    public Optional<Wallet> findWallet(String email, String password) {
        Customer customer = findByEmailAndPassword(email, password);
        return Optional.ofNullable(customer.getWallet());
    }

    @Override
    public Customer findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new FieldNotFoundException("customer is not exists !"));
    }

    @Override
    public Set<Customer> findAll() {
        return new HashSet<>(repository.findAll());
    }

    private Customer findByEmailAndPassword(String email, String password){

        Customer customer = findByEmail(email);
        if (!customer.getPassword().equals(password)) {
            throw new WrongPasswordException("password is wrong !");
        }
        return customer;
    }
}
