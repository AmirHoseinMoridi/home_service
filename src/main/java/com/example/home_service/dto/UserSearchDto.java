package com.example.home_service.dto;

import com.example.home_service.entity.enumaration.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSearchDto {
    String firstName;

    String lastName;

    String username;

    String email;

    LocalDate dateOfSignUp;
    Role role;
}
