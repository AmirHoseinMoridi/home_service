package com.example.home_service.service.impl;


import com.example.home_service.dto.AddressDto;
import com.example.home_service.entity.Address;
import com.example.home_service.exception.ValidationException;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.repository.AddressRepository;
import com.example.home_service.service.AddressService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final Mapper mapper;

    @Autowired
    public AddressServiceImpl(AddressRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Address save(AddressDto addressDTO) throws ValidationException {
        Checker.checkValidation(addressDTO);
        Address address = mapper.dtoToAddress(addressDTO);
        return repository.save(address);
    }
}
