package com.example.home_service.dto.Parrent;

import com.example.home_service.dto.ProposalDto;
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
    String email;
    String password;
    ProposalDto proposalDto;
}
