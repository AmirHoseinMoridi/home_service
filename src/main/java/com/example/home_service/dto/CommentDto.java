package com.example.home_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

    @NotNull(message = "point is null!")
    @Range(min = 0,max = 5 ,message = "point is out of range !")
    Integer point;

    @Size(max = 1000, message = "description size is too long")
    String description;

}
