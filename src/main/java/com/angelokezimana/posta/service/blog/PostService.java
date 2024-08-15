package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.dto.blog.PostRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDTO> getAllPosts(Pageable pageable);
    PostDTO createPost(PostRequestDTO postRequestDTO);
    PostDTO getPost(Long postId);
    PostDTO updatePost(Long postId, PostRequestDTO postRequestDTO);
    void deletePost(Long postId);
}
