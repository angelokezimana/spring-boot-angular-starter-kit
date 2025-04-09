package com.angelokezimana.starter.blog.service;

import com.angelokezimana.starter.blog.model.PhotoPost;
import com.angelokezimana.starter.blog.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PhotoPostService {
    void createPhotoPost(Long postId, List<MultipartFile> images) throws IOException;
    void deletePhotoPost(Long photoPostId, Long postId) throws IOException;
    Set<PhotoPost> savePhotoPosts(List<MultipartFile> images, Post post) throws IOException;
}
