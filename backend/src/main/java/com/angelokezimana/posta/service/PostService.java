package com.angelokezimana.posta.service;

import com.angelokezimana.posta.model.Post;
import com.angelokezimana.posta.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Not Found"));
    }
}
