package com.example.home_service;

import com.example.home_service.dto.*;
import com.example.home_service.entity.Address;
import com.example.home_service.entity.Expert;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.service.AddressService;
import com.example.home_service.service.CustomerService;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomeServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HomeServiceApplication.class, args);

    }

}
