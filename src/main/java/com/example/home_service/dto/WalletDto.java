package com.example.home_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletDto {
    Double balance;
}
