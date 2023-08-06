package com.example.home_service.entity;

import com.example.home_service.entity.enumaration.RequestAction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "assignment_requests")
@Where(clause = "isActive = true")
public class AssignmentRequests extends BaseEntity<Long> {

    @Column(nullable = false)
    Boolean isActive;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    RequestAction action;

    @ManyToOne
    Expert expert;

    @ManyToOne
    SubDuty subDuty;

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", isActive=" + isActive +
                ", action=" + action +
                ", expert=" + expert +
                ", subDuty=" + subDuty +
                '}';
    }
}