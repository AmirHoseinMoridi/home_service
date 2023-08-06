package com.example.home_service.entity;


import com.example.home_service.entity.enumaration.ExpertStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Expert extends Person {

    Double point ;

    @Enumerated(value = EnumType.STRING)
    ExpertStatus status ;

    @OneToOne(orphanRemoval = true)
    Wallet wallet;

    @OneToOne
    Image image;
}
