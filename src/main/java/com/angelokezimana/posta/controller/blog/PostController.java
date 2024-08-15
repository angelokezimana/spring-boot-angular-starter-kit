package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.dto.blog.PostRequestDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    private static final Logger log = LogManager.getLogger(PostController.class);

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
    private ResponseEntity<PostDTO> create(@Valid @RequestBody PostRequestDTO newPost) {
        log.info("Received POST request with post: {}", newPost);
        PostDTO postDTO = postService.createPost(newPost);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<?> findById(@PathVariable Long postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            PostDTO postDTO = postService.getPost(postId);
            return ResponseEntity.ok(postDTO);
        } catch (RuntimeException e) {
            response.put("message", "Failed to fetch post. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{postId}")
    private ResponseEntity<?> update(@PathVariable Long postId, @Valid @RequestBody PostRequestDTO updatedPost) {
        Map<String, Object> response = new HashMap<>();
        try {
            PostDTO updatedPostResult = postService.updatePost(postId, updatedPost);

            return ResponseEntity.ok(updatedPostResult);
        } catch (RuntimeException e) {
            response.put("message", "Failed to update post. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{postId}")
    private ResponseEntity<Map<String, Object>> delete(@PathVariable Long postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            postService.deletePost(postId);
            response.put("message", "Post deleted successfully");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "Failed to delete post. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
