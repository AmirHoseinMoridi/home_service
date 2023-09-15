package com.example.home_service.service;

import com.example.home_service.base.service.BaseUserService;
import com.example.home_service.dto.UserSearchDto;
import com.example.home_service.entity.Customer;

import java.util.List;


public interface CustomerService extends BaseUserService<Customer> {
    void signUp(Customer customer);

    void editPassword(String username,String newPassword);



}
