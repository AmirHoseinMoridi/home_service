package com.example.home_service;

import com.example.home_service.dto.AddressDto;
import com.example.home_service.dto.CustomerRequestDto;
import com.example.home_service.dto.OrderDto;
import com.example.home_service.entity.Customer;
import com.example.home_service.service.ServiceRegistry;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerTest {
    private final ServiceRegistry serviceRegistry;

    @Autowired
    public CustomerTest(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    private final AddressDto addressDto = new AddressDto("a", "b", "c", "d");


    private final CustomerRequestDto customerRequestDto = new CustomerRequestDto("firs naem", "last name",
            "b@email.com", "1234abcd", addressDto);



}
