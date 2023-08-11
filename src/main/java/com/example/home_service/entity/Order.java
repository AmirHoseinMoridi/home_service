package com.example.home_service.entity;

import com.example.home_service.entity.base.BaseEntity;
import com.example.home_service.entity.enumaration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Table(name = "orders")
@Entity
public class Order extends BaseEntity<Long> {

    @Column(nullable = false)
    String description;

    Double suggestedPriceByCustomer;

    LocalDate suggestedDateForStartWork;

    @Enumerated(value = EnumType.STRING)
    OrderStatus status;

    @ManyToOne
    Customer customer;

    @ManyToOne
    SubDuty subDuty;

    @ManyToOne
    Address address;

    @OneToOne
    Comment comment;

}
