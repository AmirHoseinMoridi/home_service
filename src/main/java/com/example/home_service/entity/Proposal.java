package com.example.home_service.entity;

import com.example.home_service.base.domain.BaseEntity;
import com.example.home_service.entity.enumaration.ProposalStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Proposal
        extends BaseEntity<Long> {

    @Column( nullable = false)
    LocalDate dateOfCreate;

    @Column( nullable = false, scale = 2)
    Double suggestedPriceByExpert;

    @Column( nullable = false)
    ZonedDateTime suggestedDate;

    @Column( nullable = false)
    ZonedDateTime durationOfWork;

    @Enumerated(value = EnumType.STRING)
    ProposalStatus status ;

    @ManyToOne
    Order order;

    @ManyToOne
    Expert expert;
}
