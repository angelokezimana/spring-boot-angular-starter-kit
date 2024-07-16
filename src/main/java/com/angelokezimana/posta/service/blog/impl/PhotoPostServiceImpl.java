package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.domain.blog.PhotoPost;
import com.angelokezimana.posta.domain.blog.Post;
import com.angelokezimana.posta.exception.blog.PhotoPostNotFoundException;
import com.angelokezimana.posta.exception.blog.PostNotFoundException;
import com.angelokezimana.posta.repository.blog.PhotoPostRepository;
import com.angelokezimana.posta.repository.blog.PostRepository;
import com.angelokezimana.posta.service.blog.PhotoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoPostServiceImpl implements PhotoPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoPostRepository photoPostRepository;

    public void createPost(Long postId, List<PhotoPost> newPhotosPost) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        for (PhotoPost photoPost : newPhotosPost) {
            photoPost.setPost(post);
            photoPostRepository.save(photoPost);
        }
    }

    public void deletePhotoPost(Long photoPostId) {
        PhotoPost photoPost = photoPostRepository.findById(photoPostId)
                .orElseThrow(() -> PhotoPostNotFoundException.forId(photoPostId));

        photoPostRepository.delete(photoPost);
    }
}
