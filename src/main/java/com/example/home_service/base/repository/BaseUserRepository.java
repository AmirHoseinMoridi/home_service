package com.example.home_service.base.repository;

import com.example.home_service.base.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseUserRepository <E extends User>
        extends JpaRepository<E,Long>,
        JpaSpecificationExecutor<E> {

    Optional<E> findByUsername(String username);

    boolean existsByUsername(String username);
}
