package com.example.home_service.dto.result;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProposalResultDto {

    Double suggestedPriceByExpert;

    ZonedDateTime suggestedDate;

    ZonedDateTime durationOfWork;

    String expertName;
}
