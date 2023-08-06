package com.example.home_service.repository;


import com.example.home_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AddressRepository extends JpaRepository<Address,Long> {

    Set<Address> findByCityName(String cityName);
    Set<Address> findByStreetName(String streetName);
    Set<Address> findByApartment(String apartment);
    Set<Address> findByPostalCode(String postalCode);

}
