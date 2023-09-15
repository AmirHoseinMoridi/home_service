package com.example.home_service.entity;

import com.example.home_service.base.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Wallet extends BaseEntity<Long> {

    @Column(nullable = false, scale = 1)
    Double balance;
}
