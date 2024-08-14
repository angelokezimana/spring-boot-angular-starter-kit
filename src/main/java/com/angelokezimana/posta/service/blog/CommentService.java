package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.dto.blog.CommentDTO;
import com.angelokezimana.posta.entity.blog.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentDTO> getCommentsByPost(Long postId, Pageable pageable);
    CommentDTO createComment(Long postId, Comment comment);
    CommentDTO getComment(Long commentId);
    CommentDTO updateComment(Comment updatedComment);
    void deleteComment(Long commentId);
}
