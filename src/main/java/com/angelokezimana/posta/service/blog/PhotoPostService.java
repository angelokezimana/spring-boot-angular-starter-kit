package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.entity.blog.PhotoPost;
import com.angelokezimana.posta.entity.blog.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoPostService {
    void createPhotoPost(Long postId, List<MultipartFile> images) throws IOException;
    void deletePhotoPost(Long photoPostId, Long postId) throws IOException;
    List<PhotoPost> savePhotoPosts(List<MultipartFile> images, Post post) throws IOException;
}
