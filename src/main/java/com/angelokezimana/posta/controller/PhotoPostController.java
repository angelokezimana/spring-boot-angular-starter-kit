package com.angelokezimana.posta.controller;

import com.angelokezimana.posta.model.PhotoPost;
import com.angelokezimana.posta.model.Post;
import com.angelokezimana.posta.service.PhotoPostService;
import com.angelokezimana.posta.service.PostService;
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
    private ResponseEntity<Post> create(@PathVariable Long postId,
                                        @RequestBody List<PhotoPost> newPhotosPost) {
        photoPostService.createPost(postId, newPhotosPost);

        return ResponseEntity.ok(postService.getPost(postId));
    }

    @DeleteMapping("/{postId}/{photoPostId}")
    private ResponseEntity<Post> delete(@PathVariable Long postId,
                                        @PathVariable Long photoPostId) {
        try {
            photoPostService.deletePhotoPost(photoPostId);
            return ResponseEntity.ok(postService.getPost(postId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(postService.getPost(postId));
        }
    }
}
