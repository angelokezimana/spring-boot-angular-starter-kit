package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.domain.blog.PhotoPost;
import com.angelokezimana.posta.domain.blog.Post;
import com.angelokezimana.posta.exception.blog.PostNotFoundException;
import com.angelokezimana.posta.repository.blog.PhotoPostRepository;
import com.angelokezimana.posta.repository.blog.PostRepository;
import com.angelokezimana.posta.service.blog.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoPostRepository photoPostRepository;

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post createPost(Post post) {
        Post savedPost = postRepository.save(post);

        for (PhotoPost photoPost : post.getPhotoPosts()) {
            photoPost.setPost(savedPost);
            photoPostRepository.save(photoPost);
        }

        return savedPost;
    }

    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public Post updatePost(Post updatedPost) {
        Post existingPost = postRepository.findById(updatedPost.getId())
                .orElseThrow(() -> PostNotFoundException.forId(updatedPost.getId()));

        existingPost.setText(updatedPost.getText());

        return postRepository.save(existingPost);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        postRepository.delete(post);
    }
}
