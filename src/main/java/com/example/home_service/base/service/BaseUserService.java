package com.example.home_service.base.service;


import com.example.home_service.base.domain.User;
import com.example.home_service.dto.UserSearchDto;
import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BaseUserService<E extends User> {

    Optional<E> findByUsername(String username);

   // AuthenticationResponse logIn(String username, String Password);
    E findByUsernameAndPassword(String username, String Password) ;

    Optional<Wallet> findWallet(String username);
    Set<E> findAll();
    Long count();
    E findById(Long id);
    List<E> doAdvanceSearch (UserSearchDto dto);

}
