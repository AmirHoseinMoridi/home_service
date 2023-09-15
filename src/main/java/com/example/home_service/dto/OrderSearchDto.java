package com.example.home_service.dto;

import com.example.home_service.entity.enumaration.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderSearchDto {
    ZonedDateTime start;
    ZonedDateTime end;
    OrderStatus status;
    Long subDutyId;
}
