package com.example.home_service.repository;

import com.example.home_service.entity.Address;
import com.example.home_service.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface PersonRepository<P extends Person> extends JpaRepository<P, Long> {

    Set<P> findByFirstName(String firstName);

    Set<P> findByLastName(String lastName);

    Optional<P> findByEmail(String email);

    Set<P> findByPassword(String password);

    Set<P> findByDateOfSignUp(LocalDate dateOfSignUp);

    Set<P> findByAddress(Address address);
}
