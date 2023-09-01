package com.example.home_service.dto.result;

import com.example.home_service.dto.AddressDto;
import com.example.home_service.entity.enumaration.ExpertStatus;
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
public class ExpertResultDto {
    String firstName;
    String lastName;
    String email;
    LocalDate dateOfSignUp;
    AddressDto address;
    ExpertStatus status;
    Double point;
    String imagePath;
}
