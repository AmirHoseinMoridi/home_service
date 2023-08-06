package com.example.home_service.entity;

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

    @Column(name = "city_name", nullable = false)
    String cityName;

    @Column(name = "street_name", nullable = false)
    String streetName;

    @Column(nullable = false)
    String apartment;

    @Column(name = "postal_code", nullable = false)
    String postalCode;
}
