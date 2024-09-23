package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.entity.blog.PhotoPost;
import com.angelokezimana.posta.entity.blog.Post;
import com.angelokezimana.posta.exception.blog.PhotoPostNotFoundException;
import com.angelokezimana.posta.exception.blog.PostNotFoundException;
import com.angelokezimana.posta.repository.blog.PhotoPostRepository;
import com.angelokezimana.posta.repository.blog.PostRepository;
import com.angelokezimana.posta.service.blog.PhotoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class PhotoPostServiceImpl implements PhotoPostService {

    private final PostRepository postRepository;
    private final PhotoPostRepository photoPostRepository;
    private final ImageService imageService;

    @Autowired
    public PhotoPostServiceImpl(PostRepository postRepository,
                                PhotoPostRepository photoPostRepository,
                                ImageService imageService) {
        this.postRepository = postRepository;
        this.photoPostRepository = photoPostRepository;
        this.imageService = imageService;
    }

    public void createPhotoPost(Long postId, List<MultipartFile> images) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        List<PhotoPost> photoPosts = imageService.savePhotoPosts(images, post);

        photoPostRepository.saveAll(photoPosts);
    }

    public void deletePhotoPost(Long photoPostId, Long postId) throws IOException {
        PhotoPost photoPost = photoPostRepository.findById(photoPostId)
                .orElseThrow(() -> PhotoPostNotFoundException.forId(photoPostId));

        if (photoPost.getPost().getId() != postId) {
            throw new PhotoPostNotFoundException("Photo does not belong to the specified post, or the post does not exist.");
        }

        imageService.deleteImageFromFileSystem(photoPost.getImage());

        photoPostRepository.delete(photoPost);
    }
}
