package com.angelokezimana.starter.blog.service;

import com.angelokezimana.starter.blog.dto.CommentDto;
import com.angelokezimana.starter.blog.dto.CommentRequestDto;
import com.angelokezimana.starter.blog.dto.CommentWithPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentDto> getCommentsByPost(Long postId, Pageable pageable);
    CommentDto createComment(Long postId, CommentRequestDto commentRequestDTO);
    CommentWithPostDto getComment(Long commentId);
    CommentDto updateComment(Long commentId, CommentRequestDto commentRequestDTO);
    void deleteComment(Long commentId);
}
