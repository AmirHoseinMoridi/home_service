package com.example.home_service.dto;

import com.example.home_service.util.Regex;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardDto {

    @NotNull
    Long proposalId;

    @Size(min = 16, max = 16, message = "card number is not valid !")
    @Pattern(regexp = Regex.DIGIT_STRING, message = "card number is not valid !")
    String cardNumber;

    @Size(min = 4, max = 4, message = "cvv2 is not valid !")
    @Pattern(regexp = Regex.DIGIT_STRING, message = "cvv2 is not valid !")
    String cvv2;

    @PastOrPresent(message = "Expiration date is not valid !")
    LocalDate ExpirationDate;
}
