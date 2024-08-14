package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.controller.blog.PostController;
import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.entity.blog.PhotoPost;
import com.angelokezimana.posta.entity.blog.Post;
import com.angelokezimana.posta.entity.security.User;
import com.angelokezimana.posta.exception.blog.PostNotFoundException;
import com.angelokezimana.posta.exception.security.UserNotFoundException;
import com.angelokezimana.posta.mapper.blog.PostMapper;
import com.angelokezimana.posta.repository.blog.PhotoPostRepository;
import com.angelokezimana.posta.repository.blog.PostRepository;
import com.angelokezimana.posta.repository.security.UserRepository;
import com.angelokezimana.posta.service.blog.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoPostRepository photoPostRepository;

    private static final Logger log = LogManager.getLogger(PostController.class);

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostMapper::toPostDTO);
    }

    public PostDTO createPost(Post post) {
        Post savedPost = postRepository.save(post);

        for (PhotoPost photoPost : post.getPhotoPosts()) {
            photoPost.setPost(savedPost);
            photoPostRepository.save(photoPost);
        }

        User author = userRepository.findById(savedPost.getAuthor().getId())
                .orElseThrow(() -> UserNotFoundException.forId(savedPost.getAuthor().getId()));

        savedPost.setAuthor(author);
        return PostMapper.toPostDTO(savedPost);
    }

    public PostDTO getPost(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        return PostMapper.toPostDTO(post);
    }

    public PostDTO updatePost(Post updatedPost) {
        Post existingPost = postRepository.findById(updatedPost.getId())
                .orElseThrow(() -> PostNotFoundException.forId(updatedPost.getId()));

        existingPost.setText(updatedPost.getText());

        Post post = postRepository.save(existingPost);

        return PostMapper.toPostDTO(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        postRepository.delete(post);
    }
}
