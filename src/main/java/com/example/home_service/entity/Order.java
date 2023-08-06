package com.example.home_service.entity;

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

    @Column(name = "suggested_price_by_customer")
    Double suggestedPriceByCustomer;

    @Column(name = "suggested_date_for_start_work")
    LocalDate suggestedDateForStartWork;

    @ManyToOne
    Customer customer;

    @ManyToOne
    SubDuty subDuty;

    @ManyToOne
    Address address;

    @OneToOne
    Comment comment;

}
