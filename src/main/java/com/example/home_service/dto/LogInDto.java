package com.example.home_service.dto;

import com.example.home_service.util.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogInDto {
    @NotBlank(message = "username is not valid !")
    String username;

    @NotBlank(message = "password is not valid !")
    @Pattern(regexp = Regex.EIGHT_CHARACTERS_WITH_LETTERS_AND_NUMBERS, message = "password is not valid")
    String password;

}
