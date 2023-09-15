package com.example.home_service.entity;

import com.example.home_service.base.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Where(clause = "is_active = true")
public class Duty
        extends BaseEntity<Long> {


    @Column( nullable = false)
    String name;


    @Column(nullable = false)
    Boolean isActive;

}
