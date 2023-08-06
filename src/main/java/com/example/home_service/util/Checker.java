package com.example.home_service.util;

import com.example.home_service.entity.Expert;
import com.example.home_service.entity.enumaration.ExpertStatus;
import com.example.home_service.exception.ExpertAccessException;
import com.example.home_service.exception.ImageNotFoundException;
import com.example.home_service.exception.ImageSizeOutOfRangeException;
import jakarta.validation.*;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

public class Checker {
    public static <E> void checkValidation(E entity) throws ValidationException {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

        try (factory) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<E>> violations = validator.validate(entity);

            if (!violations.isEmpty()) {
                StringBuilder errorMassage = new StringBuilder();
                violations.forEach(v -> errorMassage.append(v.getMessage()).append("\n"));
                throw new ValidationException(errorMassage.toString());
            }
        }
    }

    public static void checkFileExist(File file) throws ImageNotFoundException {
        if (!file.exists() || !file.isFile()) {
            throw new ImageNotFoundException("this image file is not exist !");
        }
    }

    public static void checkImageSize(File imageFile) throws ImageNotFoundException, ImageSizeOutOfRangeException {
        try {
            long fileSizeInBytes = Files.size(imageFile.toPath());
            long fileSizeInKB = fileSizeInBytes / 1024;
            int maxSizeKB = 300;

            if (fileSizeInKB >= maxSizeKB) {
                throw new ImageSizeOutOfRangeException("size of Image most be less than 300kb ");
            }
        } catch (IOException io) {
            throw new ImageNotFoundException("can not find image file !");
        }
    }

    public static void checkExpertsAccess(Expert expert) throws ExpertAccessException {
        if (!expert.getStatus().equals(ExpertStatus.ACCEPTED)){
            throw new ExpertAccessException("expert has not access to this item !");
        }
    }
}
