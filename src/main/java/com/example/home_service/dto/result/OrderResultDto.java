package com.example.home_service.dto.result;

import com.example.home_service.entity.Customer;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.entity.enumaration.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResultDto {

    Long id;

    String description;

    Double suggestedPriceByCustomer;

    ZonedDateTime suggestedDateForStartWork;

    String address;

    OrderStatus status;
}
