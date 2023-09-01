package com.example.home_service.dto.Parrent;

import com.example.home_service.dto.EmailAndPasswordDto;
import com.example.home_service.util.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddSbDutyDto {
    @NotBlank(message = "email is not valid !")
    @Pattern(regexp = Regex.EMAIL, message = "email is not valid!")
    String email;

    @NotBlank(message = "password is not valid !")
    @Pattern(regexp = Regex.EIGHT_CHARACTERS_WITH_LETTERS_AND_NUMBERS, message = "password is not valid")
    String password;

    String subDutyName;
}
