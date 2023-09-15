package com.example.home_service.entity;

import com.example.home_service.base.domain.BaseEntity;
import com.example.home_service.entity.enumaration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Table(name = "orders")
@Entity
public class Order
        extends BaseEntity<Long> {

    @Column(nullable = false)
    String description;

    Double suggestedPriceByCustomer;

    ZonedDateTime suggestedDateForStartWork;

    String address;

    @Enumerated(value = EnumType.STRING)
    OrderStatus status;

    @ManyToOne
    Customer customer;

    @ManyToOne
    SubDuty subDuty;

}
