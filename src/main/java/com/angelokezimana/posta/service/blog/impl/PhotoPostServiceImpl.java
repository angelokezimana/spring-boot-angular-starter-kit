package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.dto.blog.PhotoPostDTO;
import com.angelokezimana.posta.entity.blog.PhotoPost;
import com.angelokezimana.posta.entity.blog.Post;
import com.angelokezimana.posta.exception.blog.PhotoPostNotFoundException;
import com.angelokezimana.posta.exception.blog.PostNotFoundException;
import com.angelokezimana.posta.repository.blog.PhotoPostRepository;
import com.angelokezimana.posta.repository.blog.PostRepository;
import com.angelokezimana.posta.service.blog.PhotoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoPostServiceImpl implements PhotoPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoPostRepository photoPostRepository;

    public void createPhotoPost(Long postId, List<PhotoPostDTO> photoPostDTOs) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        List<PhotoPost> photoPosts = photoPostDTOs.stream()
                .map(photoPostDTO -> {
                    PhotoPost photoPost = new PhotoPost();
                    photoPost.setImage(photoPostDTO.image());
                    photoPost.setPost(post);
                    return photoPost;
                })
                .collect(Collectors.toList());

        photoPostRepository.saveAll(photoPosts);
    }

    public PhotoPost getPhotoPost(Long photoPostId) {
        return photoPostRepository.findById(photoPostId)
                .orElseThrow(() -> PhotoPostNotFoundException.forId(photoPostId));
    }

    public void deletePhotoPost(Long photoPostId) {
        PhotoPost photoPost = photoPostRepository.findById(photoPostId)
                .orElseThrow(() -> PhotoPostNotFoundException.forId(photoPostId));

        photoPostRepository.delete(photoPost);
    }
}
