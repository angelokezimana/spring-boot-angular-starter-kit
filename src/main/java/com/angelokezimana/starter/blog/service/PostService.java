package com.angelokezimana.starter.blog.service;

import com.angelokezimana.starter.blog.dto.PostDto;
import com.angelokezimana.starter.blog.dto.PostDetailDto;
import com.angelokezimana.starter.blog.dto.PostRequestDto;
import com.angelokezimana.starter.blog.dto.PostRequestUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Page<PostDto> getAllPosts(Pageable pageable);

    PostDetailDto createPost(PostRequestDto postRequestDTO, MultipartFile imageCover, List<MultipartFile> photos) throws IOException;

    PostDetailDto getPost(Long postId);

    PostDetailDto updatePost(Long postId, PostRequestUpdateDto postRequestDTO, MultipartFile imageCover) throws IOException;

    void deletePost(Long postId) throws IOException;
}
