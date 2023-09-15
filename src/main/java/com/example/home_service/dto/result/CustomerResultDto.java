package com.example.home_service.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResultDto {
    String firstName;
    String lastName;
    String email;
    LocalDate dateOfSignUp;
    String address;
}
