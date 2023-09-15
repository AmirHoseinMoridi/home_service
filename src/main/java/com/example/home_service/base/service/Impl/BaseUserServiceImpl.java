package com.example.home_service.base.service.Impl;



import com.example.home_service.base.domain.User;
import com.example.home_service.base.repository.BaseUserRepository;
import com.example.home_service.base.service.BaseUserService;
import com.example.home_service.dto.UserSearchDto;
import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Wallet;
import com.example.home_service.entity.enumaration.Role;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.exception.WrongPasswordException;
import com.example.home_service.service.ServiceRegistry;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
public class BaseUserServiceImpl<E extends User, R extends BaseUserRepository<E>>
        implements BaseUserService<E> {

    protected final R repository;

    protected final PasswordEncoder passwordEncoder;

    protected final ServiceRegistry serviceRegistry;
    @Override
    public Optional<E> findByUsername(String username) {
        return repository.findByUsername(username);
    }


    @Override
    public Optional<Wallet> findWallet(String username) {
        E user = repository.findByUsername(username)
                .orElseThrow(()->new FieldNotFoundException("user not found !"));
        return Optional.ofNullable(user.getWallet());
    }

    @Override
    public Set<E> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public Long count() {
        return repository.count();
    }
    public E findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new FieldNotFoundException("user not found !")
                );
    }


    @Override
    public List<E> doAdvanceSearch(UserSearchDto dto) {
        return repository.findAll(
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    fillFirstNamePredicate(predicates, root, criteriaBuilder, dto.getFirstName());
                    fillLastNamePredicate(predicates, root, criteriaBuilder, dto.getLastName());
                    fillUsernamePredicate(predicates, root, criteriaBuilder, dto.getUsername());
                    fillDateOfSignUpPredicate(predicates, root, criteriaBuilder, dto.getDateOfSignUp());
                    fillRolePredicate(predicates, root, criteriaBuilder, dto.getRole());
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }
        );
    }

    private void fillRolePredicate(List<Predicate> predicates, Root<E> root, CriteriaBuilder criteriaBuilder, Role role) {
        if (role != null) {
            Predicate rolePredicate = criteriaBuilder.equal(root.get("role"), role);
            predicates.add(rolePredicate);
        }
    }


    private void fillDateOfSignUpPredicate(List<Predicate> predicates, Root<E> root, CriteriaBuilder criteriaBuilder, LocalDate dateOfSignUp) {
        if (dateOfSignUp != null) {
            predicates.add(
                    criteriaBuilder.equal(
                            root.get("dateOfSignUp"),   dateOfSignUp
                    )
            );
        }
    }

    private void fillUsernamePredicate(List<Predicate> predicates, Root<E> root, CriteriaBuilder criteriaBuilder, String username) {
        if (StringUtils.isNotBlank(username)) {
            predicates.add(
                    criteriaBuilder.like(
                            root.get("username"), "%" + username.trim() + "%"
                    )
            );
        }
    }

    private void fillLastNamePredicate(List<Predicate> predicates, Root<E> root, CriteriaBuilder criteriaBuilder, String lastName) {
        if (StringUtils.isNotBlank(lastName)) {
            predicates.add(
                    criteriaBuilder.like(
                            root.get("lastName"), "%" + lastName.trim() + "%"
                    )
            );
        }
    }

    private void fillFirstNamePredicate(List<Predicate> predicates, Root<E> root, CriteriaBuilder criteriaBuilder, String firstName) {
        if (StringUtils.isNotBlank(firstName)) {
            predicates.add(
                    criteriaBuilder.like(
                            root.get("firstName"), "%" + firstName.trim() + "%"
                    )
            );
        }
    }

}
