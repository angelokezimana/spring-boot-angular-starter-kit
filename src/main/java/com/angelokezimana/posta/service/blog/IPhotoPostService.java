package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.domain.blog.PhotoPost;

import java.util.List;

public interface IPhotoPostService {
    void createPost(Long postId, List<PhotoPost> newPhotosPost);
    void deletePhotoPost(Long photoPostId);
}
