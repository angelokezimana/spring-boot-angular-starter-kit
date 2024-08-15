package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.dto.blog.PhotoPostDTO;

import java.util.List;

public interface PhotoPostService {
    void createPhotoPost(Long postId, List<PhotoPostDTO> newPhotosPost);
    void deletePhotoPost(Long photoPostId);
}
