package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.dto.blog.PhotoPostDTO;
import com.angelokezimana.posta.entity.blog.PhotoPost;

import java.util.List;

public interface PhotoPostService {
    void createPhotoPost(Long postId, List<PhotoPostDTO> newPhotosPost);
    PhotoPost getPhotoPost(Long photoPostId);
    void deletePhotoPost(Long photoPostId);
}
