package com.example.home_service.service;


import com.example.home_service.dto.AddressDto;
import com.example.home_service.entity.Address;
import com.example.home_service.exception.ValidationException;

import java.util.Optional;

public interface AddressService {
    Address save(AddressDto addressDTO) throws ValidationException;
}
