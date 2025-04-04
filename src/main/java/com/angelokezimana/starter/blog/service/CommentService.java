package com.angelokezimana.starter.blog.service;

import com.angelokezimana.starter.blog.dto.CommentDTO;
import com.angelokezimana.starter.blog.dto.CommentRequestDTO;
import com.angelokezimana.starter.blog.dto.CommentWithPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentDTO> getCommentsByPost(Long postId, Pageable pageable);
    CommentDTO createComment(Long postId, CommentRequestDTO commentRequestDTO);
    CommentWithPostDTO getComment(Long commentId);
    CommentDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO);
    void deleteComment(Long commentId);
}
