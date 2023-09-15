package com.example.home_service.entity;

import com.example.home_service.base.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Comment
        extends BaseEntity<Long> {

    @Column(nullable = false)
    Integer point;

    @Column(nullable = false)
    String description ;

    @OneToOne
    Order order;
}
