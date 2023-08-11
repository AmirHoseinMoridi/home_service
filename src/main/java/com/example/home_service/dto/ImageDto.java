package com.example.home_service.dto;

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
public class ImageDto {

    @NotBlank(message = "image path not valid !")
    @Pattern(regexp = Regex.IMAGE_JPG, message = "image path not valid !")
    String imagePath;
}
