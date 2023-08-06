package com.example.home_service.dto;

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
public class PersonResultDTO {
    String firstName;
    String lastName;
    String email;
    LocalDate dateOfSignUp;
    AddressDTO address;
}
