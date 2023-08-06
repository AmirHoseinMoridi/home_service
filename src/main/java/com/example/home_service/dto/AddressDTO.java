package com.example.home_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {

    @NotBlank(message = "city name is not valid !")
    String cityName;

    @NotBlank(message = "street name is not valid !")
    String streetName;

    @NotBlank(message = "apartment is not valid !")
    String apartment;

    @NotBlank(message = "postal code is not valid !")
    String postalCode;
}
