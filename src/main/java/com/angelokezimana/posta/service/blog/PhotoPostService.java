package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.entity.blog.PhotoPost;

import java.util.List;

public interface PhotoPostService {
    void createPost(Long postId, List<PhotoPost> newPhotosPost);
    void deletePhotoPost(Long photoPostId);
}
