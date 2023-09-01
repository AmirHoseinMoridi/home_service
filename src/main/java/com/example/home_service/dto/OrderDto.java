package com.example.home_service.dto;

import com.example.home_service.util.Regex;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    @NotBlank(message = "email is not valid !")
    @Pattern(regexp = Regex.EMAIL, message = "email is not valid!")
    String customerEmail;

    @NotBlank(message = "sub duty name not valid !")
    @Pattern(regexp = Regex.STRING_WITHOUT_NUMBER, message = "sub duty name is not valid !")
    String subDutyName;

    @NotBlank(message = "description is not valid!")
    String description;

    @NotNull(message = "suggested_price_by_customer is null !")
    @PositiveOrZero(message = "suggested_price_by_customer must be a positive number")
    Double suggestedPriceByCustomer;

    @NotNull(message = "suggested_date_for_start_work is null !")
    @FutureOrPresent(message = "suggested_date_for_start_work must be date in the future or present ")
    LocalDateTime suggestedDateForStartWork;

    AddressDto addressDto;
}
