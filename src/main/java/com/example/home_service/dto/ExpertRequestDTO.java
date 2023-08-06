package com.example.home_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertRequestDTO extends PersonRequestDTO {

    ImageDTO imageDTO;
}
