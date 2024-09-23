package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.entity.blog.PhotoPost;
import com.angelokezimana.posta.entity.blog.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final String fileStoragePath;

    public ImageService(@Value("${file.storage.path}") String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }

    public List<PhotoPost> savePhotoPosts(List<MultipartFile> images, Post post) {
        return images.stream()
                .map(image -> {
                    try {
                        String contentType = image.getContentType();
                        if (contentType == null || !isImage(contentType)) {
                            throw new RuntimeException("Only image files are allowed.");
                        }
                        String imageUrl = saveImage(image);
                        PhotoPost photoPost = new PhotoPost();
                        photoPost.setImage(imageUrl);
                        photoPost.setPost(post);
                        return photoPost;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save image", e);
                    }
                })
                .collect(Collectors.toList());
    }

    public void deleteImageFromFileSystem(String imagePath) throws IOException {
            Path path = Paths.get(imagePath);
            Files.deleteIfExists(path);
    }

    private String saveImage(MultipartFile image) throws IOException {
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
