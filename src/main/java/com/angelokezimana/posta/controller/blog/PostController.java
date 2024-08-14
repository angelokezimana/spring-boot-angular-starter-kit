package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.entity.blog.Post;
import com.angelokezimana.posta.service.blog.PostService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;


@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    private static final Logger log = LogManager.getLogger(PostController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.setAllowedFields("text","photoPosts","author");
    }

    @GetMapping
    private ResponseEntity<List<PostDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        String sortBy = sort[0];
        String sortOrder = sort.length > 1 ? sort[1] : "asc";
        Sort parseSortParameter = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        Pageable pageable = PageRequest.of(page, size, parseSortParameter);
        Page<PostDTO> postDTOs = postService.getAllPosts(pageable);

        return ResponseEntity.ok(postDTOs.getContent());
    }

    @PostMapping
    private ResponseEntity<PostDTO> create(@Valid @RequestBody Post newPost) {
        log.info("Received POST request with post: {}", newPost);
        log.info("Received POST request with photoPosts: {}", newPost.getPhotoPosts());
        PostDTO postDTO = postService.createPost(newPost);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<PostDTO> findById(@PathVariable Long postId) {
        PostDTO postDTO = postService.getPost(postId);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping("/{postId}")
    private ResponseEntity<PostDTO> update(@PathVariable Long postId, @Valid @RequestBody Post updatedPost) {
        updatedPost.setId(postId);

        PostDTO updatedPostResult = postService.updatePost(updatedPost);

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
