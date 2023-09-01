package com.example.home_service.entity;

import com.example.home_service.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
public class Comment extends BaseEntity<Long> {

    @Column(nullable = false)
    Integer point;

    @Column(nullable = false)
    String description ;

    @OneToOne
    Order order;
}
