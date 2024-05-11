package com.angelokezimana.posta.service;

import com.angelokezimana.posta.model.PhotoPost;
import com.angelokezimana.posta.model.Post;
import com.angelokezimana.posta.repository.PhotoPostRepository;
import com.angelokezimana.posta.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoPostRepository photoPostRepository;

    public Post createPost(Post post) {
        Post savedPost = postRepository.save(post);

        for(PhotoPost photoPost: post.getPhotoPosts()) {
            photoPost.setPost(savedPost);
            photoPostRepository.save(photoPost);
        }

        return savedPost;
    }

    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Not Found"));
    }
}
