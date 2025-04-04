package com.angelokezimana.starter.blog.service;

import com.angelokezimana.starter.blog.dto.PostDTO;
import com.angelokezimana.starter.blog.dto.PostDetailDTO;
import com.angelokezimana.starter.blog.dto.PostRequestDTO;
import com.angelokezimana.starter.blog.dto.PostRequestUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Page<PostDTO> getAllPosts(Pageable pageable);

    PostDetailDTO createPost(PostRequestDTO postRequestDTO, MultipartFile imageCover, List<MultipartFile> photos) throws IOException;

    PostDetailDTO getPost(Long postId);

    PostDetailDTO updatePost(Long postId, PostRequestUpdateDTO postRequestDTO, MultipartFile imageCover) throws IOException;

    void deletePost(Long postId) throws IOException;
}
