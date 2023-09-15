package com.example.home_service.base.service.Impl;


import com.example.home_service.base.domain.User;
import com.example.home_service.base.repository.UserRepository;
import com.example.home_service.base.service.UserService;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseUserServiceImpl<User, UserRepository> implements UserService {


    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, ServiceRegistry serviceRegistry) {
        super(repository, passwordEncoder, serviceRegistry);
    }
}
