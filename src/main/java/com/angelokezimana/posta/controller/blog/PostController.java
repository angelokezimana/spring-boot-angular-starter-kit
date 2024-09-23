package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.dto.blog.PostRequestDTO;
import com.angelokezimana.posta.dto.blog.PostRequestUpdateDTO;
import com.angelokezimana.posta.service.blog.PostService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private static final Logger log = LogManager.getLogger(PostController.class);

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<PostDTO> create(@ModelAttribute PostRequestDTO postRequestDTO,
                                           @RequestParam(value = "photos", required = false) List<MultipartFile> photos) throws IOException {
        PostDTO postDTO = postService.createPost(postRequestDTO, photos);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<PostDTO> findById(@PathVariable Long postId) {

        PostDTO postDTO = postService.getPost(postId);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping("/{postId}")
    private ResponseEntity<PostDTO> update(@PathVariable Long postId, @RequestBody @Valid PostRequestUpdateDTO updatedPost) {

        PostDTO updatedPostResult = postService.updatePost(postId, updatedPost);
        return ResponseEntity.ok(updatedPostResult);
    }

    @DeleteMapping("/{postId}")
    private ResponseEntity<Map<String, String>> delete(@PathVariable Long postId) throws IOException {

        Map<String, String> response = new HashMap<>();
        postService.deletePost(postId);
        response.put("message", "Post deleted successfully");
        return ResponseEntity.ok(response);
    }
}
