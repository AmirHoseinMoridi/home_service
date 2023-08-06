package com.example.home_service.dto;

import com.example.home_service.util.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubDutyDTO {

    @NotBlank(message = "name is not valid !")
    @Pattern(regexp = Regex.STRING_WITHOUT_NUMBER, message = "name can't have any digit")
    String name;

    @NotNull(message = "price is null!")
    @PositiveOrZero(message = "price must be a positive number")
    Double price;

    String description;
}
