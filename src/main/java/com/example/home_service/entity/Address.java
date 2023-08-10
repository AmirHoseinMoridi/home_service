package com.example.home_service.entity;

import com.example.home_service.entity.base.BaseEntity;
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
public class Address extends BaseEntity<Long> {

    @Column( nullable = false)
    String cityName;

    @Column( nullable = false)
    String streetName;

    @Column(nullable = false)
    String apartment;

    @Column( nullable = false)
    String postalCode;
}
