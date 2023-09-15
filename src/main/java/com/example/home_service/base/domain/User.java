package com.example.home_service.base.domain;

import com.example.home_service.entity.Wallet;
import com.example.home_service.entity.enumaration.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User
        extends BaseEntity<Long> {


    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(unique = true,nullable = false)
    String username;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(name = "date_of_signup", nullable = false)
    ZonedDateTime dateOfSignUp;

    @Column(nullable = false)
    String address;

    @Enumerated(EnumType.STRING)
    Role role;

    @OneToOne(orphanRemoval = true)
    Wallet wallet;

}
