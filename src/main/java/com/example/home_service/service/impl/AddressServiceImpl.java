package com.example.home_service.service.impl;


import com.example.home_service.entity.Address;
import com.example.home_service.exception.NotValidException;
import com.example.home_service.repository.AddressRepository;
import com.example.home_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;


    @Autowired
    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address save(Address address) throws NotValidException {
        return repository.save(address);
    }
}
