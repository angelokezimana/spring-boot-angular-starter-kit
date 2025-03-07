package com.angelokezimana.starter.service.blog;

import com.angelokezimana.starter.dto.blog.CommentDTO;
import com.angelokezimana.starter.dto.blog.CommentRequestDTO;
import com.angelokezimana.starter.dto.blog.CommentWithPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentDTO> getCommentsByPost(Long postId, Pageable pageable);
    CommentDTO createComment(Long postId, CommentRequestDTO commentRequestDTO);
    CommentWithPostDTO getComment(Long commentId);
    CommentDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO);
    void deleteComment(Long commentId);
}
