package com.example.home_service.entity;

import com.example.home_service.entity.enumaration.ProposalStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Proposal extends BaseEntity<Long> {

    @Column(name = "date_of_create", nullable = false)
    LocalDate dateOFCreate;

    @Column(name = "suggested_price_by_expert", nullable = false, scale = 2)
    Double suggestedPriceByExpert;

    @Column(name = "suggested_date", nullable = false)
    LocalDate suggestedDate;

    @Column(name = "duration_of_work", nullable = false)
    LocalTime durationOfWork;

    @Enumerated(value = EnumType.STRING)
    ProposalStatus status = ProposalStatus.WAITING;

    @ManyToOne
    Order order;

    @ManyToOne
    Expert expert;
}
