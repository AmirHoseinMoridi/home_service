package com.example.home_service.config;



import com.example.home_service.base.domain.User;
import com.example.home_service.base.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Lazy
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByUsername(username.trim());
        if (optionalUser.isPresent()) {
            return new CustomUserDetails(
                    optionalUser.get()
            );
        }
        throw new UsernameNotFoundException(username + " not found");
    }
}
