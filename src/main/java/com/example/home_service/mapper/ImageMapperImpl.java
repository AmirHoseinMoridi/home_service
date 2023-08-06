package com.example.home_service.mapper;


import com.example.home_service.dto.ImageDto;
import com.example.home_service.entity.Image;
import com.example.home_service.exception.ImageNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageMapperImpl implements ImageMapper {

    public String imageToImagePath(Image image) throws ImageNotFoundException {
        try {
            String folderPath = "D:/DownloadedImages/";
            String imageName = "image_" + image.getId() + ".jpg";
            byte[] imageBytes = Base64.getDecoder().decode(image.getData());

            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File imageFile = new File(folderPath + imageName);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(imageBytes);
            outputStream.close();
            return folderPath + imageName;
        } catch (IOException e) {
            throw new ImageNotFoundException("image is not exists !");
        }

    }

    public File paramToImageFile(ImageDto request) {
        return new File(request.getImagePath());
    }

    public Image imageFileToImage(File imageFile) throws ImageNotFoundException {
        try {
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            String base64 = Base64.getEncoder().encodeToString(imageBytes);
            return Image.builder()
                    .data(base64)
                    .build();
        } catch (IOException e) {
            throw new ImageNotFoundException(e.getMessage());
        }
    }
}
