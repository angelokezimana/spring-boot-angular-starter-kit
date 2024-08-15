package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.dto.blog.PhotoPostDTO;
import com.angelokezimana.posta.service.blog.PhotoPostService;
import com.angelokezimana.posta.service.blog.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/photo_posts")
public class PhotoPostController {

    @Autowired
    private PhotoPostService photoPostService;

    @Autowired
    private PostService postService;

    @PostMapping("/{postId}")
    private ResponseEntity<?> create(@PathVariable Long postId,
                                           @Valid @RequestBody List<PhotoPostDTO> newPhotosPost) {
        try {
            photoPostService.createPhotoPost(postId, newPhotosPost);
            return ResponseEntity.ok(postService.getPost(postId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create new photo. " + e.getMessage());
        }
    }

    @DeleteMapping("/{postId}/{photoPostId}")
    private ResponseEntity<?> delete(@PathVariable Long postId,
                                           @PathVariable Long photoPostId) {
        try {
            photoPostService.deletePhotoPost(photoPostId);
            return ResponseEntity.ok(postService.getPost(postId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete photo. " + e.getMessage());
        }
    }
}
