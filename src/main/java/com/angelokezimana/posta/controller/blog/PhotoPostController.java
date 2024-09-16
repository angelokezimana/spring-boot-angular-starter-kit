package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.dto.blog.PhotoPostDTO;
import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.service.blog.PhotoPostService;
import com.angelokezimana.posta.service.blog.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/photo_posts")
public class PhotoPostController {

    private final PhotoPostService photoPostService;
    private final PostService postService;

    @Autowired
    public PhotoPostController(PhotoPostService photoPostService, PostService postService) {
        this.photoPostService = photoPostService;
        this.postService = postService;
    }

    @PostMapping("/{postId}")
    private ResponseEntity<PostDTO> create(@PathVariable Long postId,
                                           @RequestBody @Valid List<PhotoPostDTO> newPhotosPost) {
        photoPostService.createPhotoPost(postId, newPhotosPost);
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @DeleteMapping("/{postId}/{photoPostId}")
    private ResponseEntity<?> delete(@PathVariable Long postId,
                                     @PathVariable Long photoPostId) {

        photoPostService.deletePhotoPost(photoPostId, postId);
        return ResponseEntity.ok(postService.getPost(postId));
    }
}
