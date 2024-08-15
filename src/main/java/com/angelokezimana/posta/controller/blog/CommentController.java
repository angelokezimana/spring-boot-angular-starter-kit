package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.dto.blog.CommentDTO;
import com.angelokezimana.posta.dto.blog.CommentRequestDTO;
import com.angelokezimana.posta.dto.blog.CommentWithPostDTO;
import com.angelokezimana.posta.service.blog.CommentService;
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
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private static final Logger log = LogManager.getLogger(PostController.class);

    @GetMapping("/posts/{postId}")
    private ResponseEntity<List<CommentDTO>> findCommentsByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        String sortBy = sort[0];
        String sortOrder = sort.length > 1 ? sort[1] : "asc";
        Sort parseSortParameter = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        Pageable pageable = PageRequest.of(page, size, parseSortParameter);
        Page<CommentDTO> commentDTOs = commentService.getCommentsByPost(postId, pageable);

        return ResponseEntity.ok(commentDTOs.getContent());
    }

    @PostMapping("/posts/{postId}")
    private ResponseEntity<?> create(@PathVariable Long postId,
                                     @Valid @RequestBody CommentRequestDTO newPost) {
        Map<String, Object> response = new HashMap<>();
        try {
            CommentDTO commentDTO = commentService.createComment(postId, newPost);
            return ResponseEntity.ok(commentDTO);
        } catch (RuntimeException e) {
            response.put("message", "Failed to create a new comment. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{commentId}")
    private ResponseEntity<?> findById(@PathVariable Long commentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            CommentWithPostDTO commentWithPostDTO = commentService.getComment(commentId);
            return ResponseEntity.ok(commentWithPostDTO);
        } catch (RuntimeException e) {
            response.put("message", "Failed to fetch comment. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{commentId}")
    private ResponseEntity<?> update(@PathVariable Long commentId,
                                     @Valid @RequestBody CommentRequestDTO updatedComment) {
        Map<String, Object> response = new HashMap<>();
        try {
            CommentDTO updatedCommentResult = commentService.updateComment(commentId, updatedComment);

            return ResponseEntity.ok(updatedCommentResult);
        } catch (RuntimeException e) {
            response.put("message", "Failed to update comment. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{commentId}")
    private ResponseEntity<Map<String, Object>> delete(@PathVariable Long commentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.deleteComment(commentId);
            response.put("message", "Comment deleted successfully");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "Failed to delete comment. " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
