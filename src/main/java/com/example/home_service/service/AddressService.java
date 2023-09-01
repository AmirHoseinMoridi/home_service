package com.example.home_service.service;


import com.example.home_service.entity.Address;
import com.example.home_service.exception.NotValidException;

public interface AddressService {
    Address save(Address address) throws NotValidException;
}
