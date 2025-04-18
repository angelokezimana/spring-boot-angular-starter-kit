package com.angelokezimana.starter.blog.service.impl;

import com.angelokezimana.starter.blog.dto.PostDTO;
import com.angelokezimana.starter.blog.dto.PostDetailDTO;
import com.angelokezimana.starter.blog.dto.PostRequestDTO;
import com.angelokezimana.starter.blog.dto.PostRequestUpdateDTO;
import com.angelokezimana.starter.blog.model.PhotoPost;
import com.angelokezimana.starter.blog.model.Post;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.blog.exception.PostNotFoundException;
import com.angelokezimana.starter.user.exception.UserNotFoundException;
import com.angelokezimana.starter.blog.mapper.PostMapper;
import com.angelokezimana.starter.blog.repository.PhotoPostRepository;
import com.angelokezimana.starter.blog.repository.PostRepository;
import com.angelokezimana.starter.blog.service.PhotoPostService;
import com.angelokezimana.starter.blog.service.PostService;
import com.angelokezimana.starter.common.image.ImageService;
import com.angelokezimana.starter.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PhotoPostRepository photoPostRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final PhotoPostService photoPostService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PhotoPostRepository photoPostRepository,
                           UserService userService,
                           ImageService imageService,
                           PhotoPostService photoPostService) {
        this.postRepository = postRepository;
        this.photoPostRepository = photoPostRepository;
        this.userService = userService;
        this.imageService = imageService;
        this.photoPostService = photoPostService;
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllPostsWithRelations(pageable);
        return posts.map(PostMapper::toPostDTO);
    }

    public PostDetailDTO createPost(PostRequestDTO postRequestDTO,
                                    MultipartFile imageCover,
                                    List<MultipartFile> images) throws IOException {

        User author = userService.getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        Post post = new Post();

        String imageUrl = imageService.saveImage(imageCover);

        post.setTitle(postRequestDTO.title());
        post.setText(postRequestDTO.text());
        post.setImageCover(imageUrl);
        post.setAuthor(author);

        Post savedPost = postRepository.save(post);

        if (images != null && !images.isEmpty()) {

            Set<PhotoPost> photoPosts = photoPostService.savePhotoPosts(images, savedPost);
            photoPostRepository.saveAll(photoPosts);
            savedPost.setPhotoPosts(photoPosts);
        }

        return PostMapper.toPostDetailDTO(savedPost);
    }

    public PostDetailDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        return PostMapper.toPostDetailDTO(post);
    }

    @PreAuthorize("hasPermission(#postId, 'POST', 'UPDATE') || hasPermission('POST', 'UPDATE')")
    public PostDetailDTO updatePost(Long postId, PostRequestUpdateDTO postRequestDTO, MultipartFile imageCover) throws IOException {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        existingPost.setTitle(postRequestDTO.title());
        existingPost.setText(postRequestDTO.text());

        if (imageCover != null && !imageCover.isEmpty()) {
            imageService.deleteImageFromFileSystem(existingPost.getImageCover());
            String imageUrl = imageService.saveImage(imageCover);
            existingPost.setImageCover(imageUrl);
        }

        Post post = postRepository.save(existingPost);

        return PostMapper.toPostDetailDTO(post);
    }

    @PreAuthorize("hasPermission(#postId, 'POST', 'DELETE') || hasPermission('POST', 'DELETE')")
    public void deletePost(Long postId) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        for (PhotoPost photoPost : post.getPhotoPosts()) {
            imageService.deleteImageFromFileSystem(photoPost.getImage());
        }

        imageService.deleteImageFromFileSystem(post.getImageCover());

        postRepository.delete(post);
    }
}
