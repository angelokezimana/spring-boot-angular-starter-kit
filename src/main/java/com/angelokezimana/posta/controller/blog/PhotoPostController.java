package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.dto.blog.PhotoPostDTO;
import com.angelokezimana.posta.entity.blog.PhotoPost;
import com.angelokezimana.posta.service.blog.PhotoPostService;
import com.angelokezimana.posta.service.blog.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> response = new HashMap<>();
        try {
            photoPostService.createPhotoPost(postId, newPhotosPost);
            return ResponseEntity.ok(postService.getPost(postId));
        } catch (RuntimeException e) {
            response.put("message", "Failed to create new photo. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{postId}/{photoPostId}")
    private ResponseEntity<?> delete(@PathVariable Long postId,
                                     @PathVariable Long photoPostId) {
        Map<String, Object> response = new HashMap<>();
        try {
            PhotoPost photoPost = photoPostService.getPhotoPost(photoPostId);
            if (photoPost.getPost().getId() != postId) {
                throw new RuntimeException("Photo does not belong to the specified post, or the post does not exist.");
            }

            photoPostService.deletePhotoPost(photoPostId);
            return ResponseEntity.ok(postService.getPost(postId));
        } catch (RuntimeException e) {
            response.put("message", "Failed to delete photo. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
