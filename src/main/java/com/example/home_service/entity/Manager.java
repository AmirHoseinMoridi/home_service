package com.example.home_service.entity;

import com.example.home_service.base.domain.User;
import com.example.home_service.entity.enumaration.Role;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Manager
        extends User {
}
