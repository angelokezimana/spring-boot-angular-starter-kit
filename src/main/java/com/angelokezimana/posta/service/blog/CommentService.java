package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.dto.blog.CommentDTO;
import com.angelokezimana.posta.dto.blog.CommentRequestDTO;
import com.angelokezimana.posta.dto.blog.CommentWithPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentDTO> getCommentsByPost(Long postId, Pageable pageable);
    CommentDTO createComment(Long postId, CommentRequestDTO commentRequestDTO);
    CommentWithPostDTO getComment(Long commentId);
    CommentDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO);
    void deleteComment(Long commentId);
}
