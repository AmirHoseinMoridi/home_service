package com.example.home_service.dto;

import com.example.home_service.entity.enumaration.ExpertStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertResultDTO {
    PersonResultDTO personResultDTO;
    ExpertStatus status;
    Double point;
    String imagePath;
}
