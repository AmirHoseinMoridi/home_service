package com.example.home_service.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@MappedSuperclass
public class BaseEntity<ID extends Serializable>
        implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    ID id;
}
