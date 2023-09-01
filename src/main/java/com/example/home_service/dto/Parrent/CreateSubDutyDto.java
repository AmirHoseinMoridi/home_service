package com.example.home_service.dto.Parrent;

import com.example.home_service.dto.DutyDto;
import com.example.home_service.dto.SubDutyDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSubDutyDto {
    DutyDto duty;
    SubDutyDto subDuty;
}
