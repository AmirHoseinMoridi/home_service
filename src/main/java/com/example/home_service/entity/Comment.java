package com.example.home_service.entity;

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
@Where(clause = "isRegistered = true")
public class Comment extends BaseEntity<Long> {
    public static final String DEFAULT_DESCRIPTION = "not registered";

    @Column(nullable = false, updatable = false)
    Integer point;

    @Column(nullable = false)
    String description ;

    @Column(nullable = false)
    Boolean isRegistered ;

}