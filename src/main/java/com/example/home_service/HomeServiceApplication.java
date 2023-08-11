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
/*        ConfigurableApplicationContext context = SpringApplication.run(HomeServiceApplication.class, args);
        AddressDto addressDto = new AddressDto("a","b","c","d");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("amir","moridi","amir@gmail.com","1234aaaa",addressDto);
        ImageDto imageDto = new ImageDto("F:/im.jpg");
        DutyDto dutyDto = new DutyDto("dutytt");
        SubDutyDto subDutyDto = new SubDutyDto("subDuty",500D,"dddd");
        ExpertRequestDto expertRequestDto =new  ExpertRequestDto("amir","moridi","amir@gmail.com","1234aaaa",addressDto,imageDto);
        ServiceRegistry serviceRegistry = context.getBean(ServiceRegistry.class);

        EmailAndPasswordDto emailAndPasswordDto = new EmailAndPasswordDto("amir@gmail.com","1234567a");
        NewPasswordDto newPasswordDto = new NewPasswordDto("1234567a");

        OrderDto orderDto = new OrderDto(customerRequestDto.getEmail(),subDutyDto.getName(),"ssdfsf",
                8D, LocalDate.now().plusDays(1),addressDto);

        serviceRegistry.orderService().addOrder(orderDto);

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");*/
    }

}
