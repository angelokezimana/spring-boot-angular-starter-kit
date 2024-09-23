package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.dto.blog.PostRequestDTO;
import com.angelokezimana.posta.dto.blog.PostRequestUpdateDTO;
import com.angelokezimana.posta.entity.blog.PhotoPost;
import com.angelokezimana.posta.entity.blog.Post;
import com.angelokezimana.posta.entity.security.User;
import com.angelokezimana.posta.exception.blog.PostNotFoundException;
import com.angelokezimana.posta.exception.security.UserNotFoundException;
import com.angelokezimana.posta.mapper.blog.PostMapper;
import com.angelokezimana.posta.repository.blog.PhotoPostRepository;
import com.angelokezimana.posta.repository.blog.PostRepository;
import com.angelokezimana.posta.service.blog.PostService;
import com.angelokezimana.posta.service.security.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PhotoPostRepository photoPostRepository;
    private final UserService userService;
    private final ImageService imageService;

    private static final Logger log = LogManager.getLogger(PostServiceImpl.class);

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PhotoPostRepository photoPostRepository,
                           UserService userService,
                           ImageService imageService) {
        this.postRepository = postRepository;
        this.photoPostRepository = photoPostRepository;
        this.userService = userService;
        this.imageService = imageService;
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostMapper::toPostDTO);
    }

    public PostDTO createPost(PostRequestDTO postRequestDTO, List<MultipartFile> images) {

        User author = userService.getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        Post post = new Post();

        post.setText(postRequestDTO.text());
        post.setAuthor(author);

        Post savedPost = postRepository.save(post);

        if (images != null && !images.isEmpty()) {

            List<PhotoPost> photoPosts = imageService.savePhotoPosts(images, savedPost);
            photoPostRepository.saveAll(photoPosts);
            savedPost.setPhotoPosts(photoPosts);
        }

        return PostMapper.toPostDTO(savedPost);
    }

    public PostDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        return PostMapper.toPostDTO(post);
    }

    public PostDTO updatePost(Long postId, PostRequestUpdateDTO postRequestDTO) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        existingPost.setText(postRequestDTO.text());

        Post post = postRepository.save(existingPost);

        return PostMapper.toPostDTO(post);
    }

    public void deletePost(Long postId) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        for (PhotoPost photoPost : post.getPhotoPosts()) {
            imageService.deleteImageFromFileSystem(photoPost.getImage());
        }

        postRepository.delete(post);
    }
}
