package com.example.home_service.service.impl;

import com.example.home_service.base.service.Impl.BaseUserServiceImpl;
import com.example.home_service.dto.UserSearchDto;
import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Wallet;
import com.example.home_service.entity.enumaration.Role;
import com.example.home_service.exception.FieldAlreadyExistException;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.repository.CustomerRepository;
import com.example.home_service.service.CustomerService;
import com.example.home_service.service.ServiceRegistry;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerServiceImpl
        extends BaseUserServiceImpl<Customer, CustomerRepository>
        implements CustomerService {


    public CustomerServiceImpl(CustomerRepository repository, PasswordEncoder passwordEncoder, ServiceRegistry serviceRegistry) {
        super(repository, passwordEncoder, serviceRegistry);
    }

    @Override
    public void signUp(Customer customer) {
        if (repository.existsByUsername(customer.getUsername())) {
            throw new FieldAlreadyExistException("this email is already exist !");
        }
        Wallet wallet = serviceRegistry.walletService().createWallet();
        customer.setWallet(wallet);
        customer.setDateOfSignUp(ZonedDateTime.now());
        String password = customer.getPassword();
        customer.setPassword(passwordEncoder.encode(password));
        customer.setRole(Role.ROLE_CUSTOMER);
        repository.save(customer);

    }

    @Override
    public void editPassword(String username, String newPassword) {
        Customer customer = repository.findByUsername(username)
                .orElseThrow(() -> new FieldNotFoundException("customer not found !"));
        customer.setPassword(passwordEncoder.encode(newPassword));
        repository.save(customer);
    }

}
