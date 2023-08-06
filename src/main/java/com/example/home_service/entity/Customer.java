package com.example.home_service.entity;

import com.example.home_service.entity.base.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Customer extends Person {

    @OneToOne(orphanRemoval = true)
    Wallet wallet;
}
