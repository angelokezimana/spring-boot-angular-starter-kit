package com.angelokezimana.starter.service.blog.impl;

import com.angelokezimana.starter.dto.blog.CommentDTO;
import com.angelokezimana.starter.dto.blog.CommentRequestDTO;
import com.angelokezimana.starter.dto.blog.CommentWithPostDTO;
import com.angelokezimana.starter.entity.blog.Comment;
import com.angelokezimana.starter.entity.blog.Post;
import com.angelokezimana.starter.entity.security.User;
import com.angelokezimana.starter.exception.blog.CommentNotFoundException;
import com.angelokezimana.starter.exception.blog.PostNotFoundException;
import com.angelokezimana.starter.exception.security.UserNotFoundException;
import com.angelokezimana.starter.mapper.blog.CommentMapper;
import com.angelokezimana.starter.mapper.blog.CommentWithPostMapper;
import com.angelokezimana.starter.repository.blog.CommentRepository;
import com.angelokezimana.starter.repository.blog.PostRepository;
import com.angelokezimana.starter.service.blog.CommentService;
import com.angelokezimana.starter.service.security.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              UserService userService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Page<CommentDTO> getCommentsByPost(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
        return comments.map(CommentMapper::toCommentDTO);
    }

    public CommentDTO createComment(Long postId, CommentRequestDTO commentRequestDTO) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        User author = userService.getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        Comment comment = new Comment();

        comment.setText(commentRequestDTO.text());
        comment.setAuthor(author);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.toCommentDTO(savedComment);
    }

    public CommentWithPostDTO getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.forId(commentId));

        return CommentWithPostMapper.toCommentWithPostDTO(comment);
    }

    @PreAuthorize("hasPermission(#commentId, 'COMMENT', 'UPDATE') || hasPermission('COMMENT', 'UPDATE')")
    public CommentDTO updateComment(Long commentId, CommentRequestDTO updatedComment) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.forId(commentId));

        existingComment.setText(updatedComment.text());

        Comment comment = commentRepository.save(existingComment);

        return CommentMapper.toCommentDTO(comment);
    }

    @PreAuthorize("hasPermission(#commentId, 'COMMENT', 'DELETE') || hasPermission('COMMENT', 'DELETE')")
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.forId(commentId));

        commentRepository.delete(comment);
    }
}
