package com.angelokezimana.posta.service.blog;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoPostService {
    void createPhotoPost(Long postId, List<MultipartFile> images) throws IOException;
    void deletePhotoPost(Long photoPostId, Long postId) throws IOException;
}
