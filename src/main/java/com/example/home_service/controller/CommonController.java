package com.example.home_service.controller;


import com.example.home_service.config.JwtUtils;
import com.example.home_service.dto.*;
import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Duty;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Image;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/public")
public class CommonController {
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;
    private final AuthenticationManager authenticationManager;
    @Qualifier("customUserDetailsService")
    private final UserDetailsService userDetailsService;
    private  final JwtUtils jwtUtils;



    public CommonController(ServiceRegistry serviceRegistry,
                            Mapper mapper,
                            AuthenticationManager authenticationManager,
                            UserDetailsService userDetailsService,
                            JwtUtils jwtUtils) {
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/logIn")
    public ResponseEntity<String> logIn(@RequestBody LogInDto request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),request.getPassword()
                )
        );
        final UserDetails user=  userDetailsService.loadUserByUsername(request.getUsername());
        if (user!=null){
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("error occurred!!!");
    }


    @PostMapping("/customer/signUp")
    public ResponseEntity<?> customerSignUp(@Validated @RequestBody CustomerDto customerDto) {
        Customer customer = mapper.dtoToCustomer(customerDto);
        serviceRegistry.customerService().signUp(customer);
        return ResponseEntity.status(200).body("sucsess");
    }

    @PostMapping("/expert/signUp")
    public ResponseEntity<?> expertSignUp(@Validated @RequestBody ExpertDto expertDto) {
        Expert expert = mapper.dtoToExpert(expertDto);
        Image image = serviceRegistry.imageService().save(expertDto.getImagePath());
        serviceRegistry.expertService().signUp(expert,image);
        return ResponseEntity.status(200).body("sucsess");
    }

    @GetMapping("/duty/findAll")
    public ResponseEntity<Set<DutyDto>> findAllDuties() {
        Set<DutyDto> responses = new HashSet<>();
        serviceRegistry.dutyService().findAll().forEach(
                duty -> responses.add(mapper.dutyToDto(duty))
        );
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/subDuty/findAll")
    public  ResponseEntity<Set<SubDutyDto>> findAllSubDuties() {
        Set<SubDutyDto> responses = new HashSet<>();
        serviceRegistry.subDutyService().findAll().forEach(
                subDuty -> responses.add(mapper.subDutyToDto(subDuty))
        );
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/subDuty/findAll/inDuty")
    public ResponseEntity<Set<SubDutyDto>> findSubDutiesInDuty(@Validated @RequestBody DutyDto dutyDto) {
        Duty duty = mapper.dtoToDuty(dutyDto);
        Set<SubDutyDto> response = new HashSet<>();
        serviceRegistry.subDutyService().findSubDutiesInDuty(duty).forEach(
                subDuty -> response.add(mapper.subDutyToDto(subDuty))
        );
        return  ResponseEntity.ok(response);
    }
}
