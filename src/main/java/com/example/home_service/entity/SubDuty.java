package com.example.home_service.entity;

import com.example.home_service.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Where(clause = "is_active = true")
public class SubDuty extends BaseEntity<Long> {


    @Column( nullable = false)
    String name;

    @Column(nullable = false, scale = 2)
    Double price;

    @Column(nullable = false)
    Boolean isActive;

    String description;

    @ManyToOne
    Duty duty;

    @ManyToMany
    Set<Expert> experts;

    public boolean addExpert(Expert expert) {
        return experts.add(expert);
    }

    public boolean removeExpert(Expert expert) {
        return experts.remove(expert);
    }

}
