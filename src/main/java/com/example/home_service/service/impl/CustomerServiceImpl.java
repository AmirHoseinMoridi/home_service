package com.example.home_service.service.impl;

import com.example.home_service.dto.EmailAndPasswordDto;
import com.example.home_service.dto.NewPasswordDto;
import com.example.home_service.dto.CustomerRequestDto;
import com.example.home_service.dto.WalletDto;
import com.example.home_service.dto.result.CustomerResultDto;
import com.example.home_service.entity.Address;
import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Wallet;
import com.example.home_service.exception.FieldAlreadyExistException;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.exception.WrongPasswordException;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.repository.CustomerRepository;
import com.example.home_service.service.CustomerService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository repository;
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;
    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, ServiceRegistry serviceRegistry, Mapper mapper) {
        this.repository = repository;
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void signUp(CustomerRequestDto customerRequestDto) {
        try {
            Checker.checkValidation(customerRequestDto);
            Customer customer = mapper.dtoToCustomer(customerRequestDto);

            Optional<Customer> optionalCustomer = repository.findByEmail(customer.getEmail());
            if (optionalCustomer.isPresent()) {
                throw new FieldAlreadyExistException("this email is already exist !");
            }

            Address address = serviceRegistry.addressService().save(customerRequestDto.getAddress());
            Wallet wallet = serviceRegistry.walletService().createWallet();

            customer.setWallet(wallet);
            customer.setDateOfSignUp(LocalDate.now());
            customer.setAddress(address);

            repository.save(customer);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


    @Transactional()
    @Override
    public void editPassword(EmailAndPasswordDto emailAndPassword, NewPasswordDto newPassword) {
        try {
            Checker.checkValidation(emailAndPassword);
            Checker.checkValidation(newPassword);
            Customer customer = findByEmailAndPassword(
                    emailAndPassword.getEmail(), emailAndPassword.getPassword()
            );
            customer.setPassword(newPassword.getPassword());
            repository.save(customer);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<WalletDto> findWallet(EmailAndPasswordDto request) {
        try {
            Checker.checkValidation(request);
            Customer customer = findByEmailAndPassword(request.getEmail(), request.getPassword());
            WalletDto walletDTO = mapper.walletToDto(customer.getWallet());
            return Optional.ofNullable(walletDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Customer findByEmail(String email) throws FieldNotFoundException {
        Optional<Customer> optionalCustomer = repository.findByEmail(email);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else throw new FieldNotFoundException("customer is not exists !");
    }

    @Override
    public Set<CustomerResultDto> findAll() {
        Set<CustomerResultDto> response = new HashSet<>();
        repository.findAll().forEach(
                customer -> response.add(mapper.customerToDto(customer))
        );
        return response;
    }

    private Customer findByEmailAndPassword(String email, String password)
            throws FieldNotFoundException, WrongPasswordException {

        Customer customer = findByEmail(email);
        if (!customer.getPassword().equals(password)) {
            throw new WrongPasswordException("password is wrong !");
        }
        return customer;
    }
}
