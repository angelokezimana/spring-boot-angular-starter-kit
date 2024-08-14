package com.angelokezimana.posta.controller.blog;

import com.angelokezimana.posta.dto.blog.CommentDTO;
import com.angelokezimana.posta.entity.blog.Comment;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private static final Logger log = LogManager.getLogger(PostController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.setAllowedFields("text", "author");
    }

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
    private ResponseEntity<CommentDTO> create(@PathVariable Long postId, @Valid @RequestBody Comment newPost) {
        CommentDTO commentDTO = commentService.createComment(postId, newPost);
        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping("/{commentId}")
    private ResponseEntity<CommentDTO> findById(@PathVariable Long commentId) {
        CommentDTO commentDTO = commentService.getComment(commentId);
        return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/{commentId}")
    private ResponseEntity<CommentDTO> update(@PathVariable Long commentId, @Valid @RequestBody Comment updatedComment) {
        updatedComment.setId(commentId);

        CommentDTO updatedCommentResult = commentService.updateComment(updatedComment);

        return ResponseEntity.ok(updatedCommentResult);
    }

    @DeleteMapping("/{commentId}")
    private ResponseEntity<String> delete(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete comment. " + e.getMessage());
        }
    }
}
