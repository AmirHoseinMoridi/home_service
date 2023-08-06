package com.example.home_service.dto.result;

import com.example.home_service.dto.AddressDto;
import com.example.home_service.entity.enumaration.ExpertStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertResultDto {
    String firstName;
    String lastName;
    String email;
    LocalDate dateOfSignUp;
    AddressDto addressDto;
    ExpertStatus status;
    Double point;
    String imagePath;
}