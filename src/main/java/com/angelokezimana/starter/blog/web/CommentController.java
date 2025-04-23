package com.angelokezimana.starter.blog.web;

import com.angelokezimana.starter.common.dto.ResponseDto;
import com.angelokezimana.starter.blog.dto.CommentDto;
import com.angelokezimana.starter.blog.dto.CommentRequestDto;
import com.angelokezimana.starter.blog.dto.CommentWithPostDto;
import com.angelokezimana.starter.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/posts/{postId}")
    private ResponseEntity<List<CommentDto>> findCommentsByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {

        String sortBy = sort[0];
        String sortOrder = sort.length > 1 ? sort[1] : "asc";
        Sort parseSortParameter = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        Pageable pageable = PageRequest.of(page, size, parseSortParameter);
        Page<CommentDto> commentDTOs = commentService.getCommentsByPost(postId, pageable);

        return ResponseEntity.ok(commentDTOs.getContent());
    }

    @PostMapping("/posts/{postId}")
    private ResponseEntity<CommentDto> create(@PathVariable Long postId,
                                              @RequestBody @Valid CommentRequestDto newPost) {

        CommentDto commentDTO = commentService.createComment(postId, newPost);
        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping("/{commentId}")
    private ResponseEntity<CommentWithPostDto> findById(@PathVariable Long commentId) {

        CommentWithPostDto commentWithPostDTO = commentService.getComment(commentId);
        return ResponseEntity.ok(commentWithPostDTO);
    }

    @PutMapping("/{commentId}")
    private ResponseEntity<CommentDto> update(@PathVariable Long commentId,
                                              @RequestBody @Valid CommentRequestDto updatedComment) {

        CommentDto updatedCommentResult = commentService.updateComment(commentId, updatedComment);
        return ResponseEntity.ok(updatedCommentResult);
    }

    @DeleteMapping("/{commentId}")
    private ResponseEntity<ResponseDto> delete(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ResponseDto("message", "Comment deleted successfully"));
    }
}
