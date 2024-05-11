package com.angelokezimana.posta.service;

import com.angelokezimana.posta.model.PhotoPost;
import com.angelokezimana.posta.model.Post;
import com.angelokezimana.posta.repository.PhotoPostRepository;
import com.angelokezimana.posta.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoPostRepository photoPostRepository;

    public void createPost(Long postId, List<PhotoPost> newPhotosPost) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        for (PhotoPost photoPost : newPhotosPost) {
            photoPost.setPost(post);
            photoPostRepository.save(photoPost);
        }
    }

    public void deletePhotoPost(Long photoPostId) {
        PhotoPost photoPost = photoPostRepository.findById(photoPostId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        photoPostRepository.delete(photoPost);
    }
}
