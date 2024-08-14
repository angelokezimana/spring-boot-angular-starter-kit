package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.entity.blog.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDTO> getAllPosts(Pageable pageable);
    PostDTO createPost(Post post);
    PostDTO getPost(long postId);
    PostDTO updatePost(Post updatedPost);
    void deletePost(Long postId);
}
