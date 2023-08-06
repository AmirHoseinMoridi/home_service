package com.example.home_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Where(clause = "isActive = true")
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

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                '}';
    }
}
