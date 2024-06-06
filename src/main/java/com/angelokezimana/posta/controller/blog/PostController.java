package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.domain.blog.Post;
import com.angelokezimana.posta.service.blog.IPostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private IPostService postService;

    private static final Logger log = LogManager.getLogger(PostController.class);

    @GetMapping
    private ResponseEntity<List<Post>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        String sortBy = sort[0];
        String sortOrder = sort.length > 1 ? sort[1] : "asc";
        Sort parseSortParameter = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        Pageable pageable = PageRequest.of(page, size, parseSortParameter);
        Page<Post> posts = postService.getAllPosts(pageable);

        return ResponseEntity.ok(posts.getContent());
    }

    @PostMapping
    private ResponseEntity<Post> create(@RequestBody Post newPost) {
        log.info("Received POST request with post: {}", newPost);
        log.info("Received POST request with photoPosts: {}", newPost.getPhotoPosts());
        Post post = postService.createPost(newPost);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<Post> findById(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    private ResponseEntity<Post> update(@PathVariable Long postId, @RequestBody Post updatedPost) {
        updatedPost.setId(postId);

        Post updatedPostResult = postService.updatePost(updatedPost);

        return ResponseEntity.ok(updatedPostResult);
    }

    @DeleteMapping("/{postId}")
    private ResponseEntity<String> delete(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete post. " + e.getMessage());
        }
    }
}
