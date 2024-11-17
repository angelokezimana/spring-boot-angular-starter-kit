package com.angelokezimana.posta.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void deleteImageFromFileSystem(String imagePath) throws IOException;
    String saveImage(MultipartFile image) throws IOException;
}
