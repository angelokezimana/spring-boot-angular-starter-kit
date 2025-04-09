package com.angelokezimana.starter.blog.service.impl;

import com.angelokezimana.starter.blog.model.PhotoPost;
import com.angelokezimana.starter.blog.model.Post;
import com.angelokezimana.starter.blog.exception.PhotoPostNotFoundException;
import com.angelokezimana.starter.blog.exception.PostNotFoundException;
import com.angelokezimana.starter.blog.repository.PhotoPostRepository;
import com.angelokezimana.starter.blog.repository.PostRepository;
import com.angelokezimana.starter.blog.service.PhotoPostService;
import com.angelokezimana.starter.common.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @PreAuthorize("hasPermission(#postId, 'POST', 'CREATE') || hasPermission('POST', 'CREATE')")
    public void createPhotoPost(Long postId, List<MultipartFile> images) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        Set<PhotoPost> photoPosts = savePhotoPosts(images, post);

        photoPostRepository.saveAll(photoPosts);
    }

    public Set<PhotoPost> savePhotoPosts(List<MultipartFile> images, Post post) {
        return images.stream()
                .map(image -> {
                    try {
                        String imageUrl = imageService.saveImage(image);
                        PhotoPost photoPost = new PhotoPost();
                        photoPost.setImage(imageUrl);
                        photoPost.setPost(post);
                        return photoPost;
                    }catch (IOException e){
                        throw new RuntimeException("Failed to save image ", e);
                    }
                })
                .collect(Collectors.toSet());

    }

    @PreAuthorize("hasPermission(#postId, 'POST', 'DELETE') || hasPermission('POST', 'DELETE')")
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
