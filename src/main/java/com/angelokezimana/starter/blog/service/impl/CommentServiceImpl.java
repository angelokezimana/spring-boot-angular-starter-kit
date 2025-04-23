package com.angelokezimana.starter.blog.service.impl;

import com.angelokezimana.starter.blog.dto.CommentDto;
import com.angelokezimana.starter.blog.dto.CommentRequestDto;
import com.angelokezimana.starter.blog.dto.CommentWithPostDto;
import com.angelokezimana.starter.blog.model.Comment;
import com.angelokezimana.starter.blog.model.Post;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.blog.exception.CommentNotFoundException;
import com.angelokezimana.starter.blog.exception.PostNotFoundException;
import com.angelokezimana.starter.user.exception.UserNotFoundException;
import com.angelokezimana.starter.blog.mapper.CommentMapper;
import com.angelokezimana.starter.blog.mapper.CommentWithPostMapper;
import com.angelokezimana.starter.blog.repository.CommentRepository;
import com.angelokezimana.starter.blog.repository.PostRepository;
import com.angelokezimana.starter.blog.service.CommentService;
import com.angelokezimana.starter.user.service.ProfileService;
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
    private final ProfileService profileService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              ProfileService profileService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.profileService = profileService;
    }

    public Page<CommentDto> getCommentsByPost(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
        return comments.map(CommentMapper::toCommentDTO);
    }

    public CommentDto createComment(Long postId, CommentRequestDto commentRequestDTO) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        User author = profileService.getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        Comment comment = new Comment();

        comment.setText(commentRequestDTO.text());
        comment.setAuthor(author);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.toCommentDTO(savedComment);
    }

    public CommentWithPostDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.forId(commentId));

        return CommentWithPostMapper.toCommentWithPostDTO(comment);
    }

    @PreAuthorize("hasPermission(#commentId, 'COMMENT', 'UPDATE') || hasPermission('COMMENT', 'UPDATE')")
    public CommentDto updateComment(Long commentId, CommentRequestDto updatedComment) {
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
