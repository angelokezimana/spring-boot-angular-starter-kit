package com.angelokezimana.starter.common.image.impl;

import com.angelokezimana.starter.common.image.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final String fileStoragePath;

    public ImageServiceImpl(@Value("${file.storage.path}") String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }

    public void deleteImageFromFileSystem(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        Files.deleteIfExists(path);
    }

    public String saveImage(MultipartFile image) throws IOException {
        String contentType = image.getContentType();
        if (contentType == null || !isImage(contentType)) {
            throw new RuntimeException("Only image files are allowed.");
        }

        Path uploadPath = Paths.get(fileStoragePath);

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.write(filePath, image.getBytes());
        return filePath.toString();
    }

    private boolean isImage(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpeg")
                || contentType.equals("image/gif");
    }
}
