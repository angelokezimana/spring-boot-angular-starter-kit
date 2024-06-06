package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.domain.blog.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService {
    Page<Post> getAllPosts(Pageable pageable);
    Post createPost(Post post);
    Post getPost(long postId);
    Post updatePost(Post updatedPost);
    void deletePost(Long postId);
}
