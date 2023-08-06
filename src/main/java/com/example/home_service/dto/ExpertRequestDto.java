package com.example.home_service.dto;

import com.example.home_service.util.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertRequestDto{

    @NotBlank(message = "first name is not valid !")
    @Pattern(regexp = Regex.STRING_WITHOUT_NUMBER, message = "firs name is not valid !")
    String firstName;

    @NotBlank(message = "last name is not valid !")
    @Pattern(regexp = Regex.STRING_WITHOUT_NUMBER, message = "last name can't has any number")
    String lastName;

    @NotBlank(message = "email is not valid !")
    @Pattern(regexp = Regex.EMAIL, message = "email is not valid!")
    String email;

    @NotBlank(message = "password is not valid !")
    @Pattern(regexp = Regex.EIGHT_CHARACTERS_WITH_LETTERS_AND_NUMBERS, message = "password is not valid")
    String password;

    AddressDto address;

    ImageDto imageDTO;
}
