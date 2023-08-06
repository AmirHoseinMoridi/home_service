package com.example.home_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProposalDto {

    @NotNull(message = "suggestedPriceByExpert")
    @PositiveOrZero(message = "suggested_price_by_expert must be a positive number")
    Double suggestedPriceByExpert;

    @NotNull(message = "suggested_date is null!")
    LocalDate suggestedDate;

    @NotNull(message = "duration_of_work id null!")
    LocalTime durationOfWork;
}
