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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Value("${file.storage.path}")
    private String fileStoragePath;

    private final PostRepository postRepository;
    private final PhotoPostRepository photoPostRepository;
    private final UserService userService;

    private static final Logger log = LogManager.getLogger(PostServiceImpl.class);

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PhotoPostRepository photoPostRepository,
                           UserService userService) {
        this.postRepository = postRepository;
        this.photoPostRepository = photoPostRepository;
        this.userService = userService;
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostMapper::toPostDTO);
    }

    public PostDTO createPost(PostRequestDTO postRequestDTO, List<MultipartFile> images) throws IOException {

        User author = userService.getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        Post post = new Post();

        post.setText(postRequestDTO.text());
        post.setAuthor(author);

        Post savedPost = postRepository.save(post);

        if (images != null && !images.isEmpty()) {
            List<PhotoPost> photoPosts = new ArrayList<>();

            for (MultipartFile image : images) {
                String imageUrl = saveImage(image);
                PhotoPost photoPost = new PhotoPost();
                photoPost.setImage(imageUrl);
                photoPost.setPost(savedPost);
                photoPosts.add(photoPost);
            }

            photoPostRepository.saveAll(photoPosts);
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

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        postRepository.delete(post);
    }

    private String saveImage(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(fileStoragePath);

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.write(filePath, image.getBytes());
        return filePath.toString();
    }
}
