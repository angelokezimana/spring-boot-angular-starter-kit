package com.angelokezimana.starter.controller.blog;

import com.angelokezimana.starter.dto.ResponseDTO;
import com.angelokezimana.starter.dto.blog.PostDTO;
import com.angelokezimana.starter.dto.blog.PostDetailDTO;
import com.angelokezimana.starter.dto.blog.PostRequestDTO;
import com.angelokezimana.starter.dto.blog.PostRequestUpdateDTO;
import com.angelokezimana.starter.service.blog.PostService;
import jakarta.validation.Valid;
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
import java.util.List;


@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

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
    private ResponseEntity<PostDetailDTO> create(@ModelAttribute @Valid PostRequestDTO postRequestDTO,
                                                 @RequestParam(value = "imageCover") MultipartFile imageCover,
                                                 @RequestParam(value = "photos", required = false) List<MultipartFile> photos) throws IOException {
        PostDetailDTO postDTO = postService.createPost(postRequestDTO, imageCover, photos);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<PostDetailDTO> findById(@PathVariable Long postId) {

        PostDetailDTO postDTO = postService.getPost(postId);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping(path = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<PostDetailDTO> update(@PathVariable Long postId,
                                                 @ModelAttribute PostRequestUpdateDTO updatedPost,
                                                 @RequestParam(value = "imageCover", required = false) MultipartFile imageCover) throws IOException {

        PostDetailDTO updatedPostResult = postService.updatePost(postId, updatedPost, imageCover);
        return ResponseEntity.ok(updatedPostResult);
    }

    @DeleteMapping("/{postId}")
    private ResponseEntity<ResponseDTO> delete(@PathVariable Long postId) throws IOException {

        postService.deletePost(postId);
        return ResponseEntity.ok(new ResponseDTO("message", "Post deleted successfully"));
    }
}
