package com.example.home_service.dto.Parrent;

import com.example.home_service.dto.ProposalDto;
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
public class AddProposalDto {

    @NotBlank(message = "username is not valid !")
    String username;

    @NotBlank(message = "password is not valid !")
    @Pattern(regexp = Regex.EIGHT_CHARACTERS_WITH_LETTERS_AND_NUMBERS, message = "password is not valid")
    String password;

    ProposalDto proposalDto;
}
