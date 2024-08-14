package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.dto.blog.CommentDTO;
import com.angelokezimana.posta.entity.blog.Comment;
import com.angelokezimana.posta.entity.blog.Post;
import com.angelokezimana.posta.entity.security.User;
import com.angelokezimana.posta.exception.blog.CommentNotFoundException;
import com.angelokezimana.posta.exception.blog.PostNotFoundException;
import com.angelokezimana.posta.exception.security.UserNotFoundException;
import com.angelokezimana.posta.mapper.blog.CommentMapper;
import com.angelokezimana.posta.repository.blog.CommentRepository;
import com.angelokezimana.posta.repository.blog.PostRepository;
import com.angelokezimana.posta.repository.security.UserRepository;
import com.angelokezimana.posta.service.blog.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<CommentDTO> getCommentsByPost(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
        return comments.map(CommentMapper::toCommentDTO);
    }

    public CommentDTO createComment(Long postId, Comment comment) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.forId(postId));

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        User author = userRepository.findById(savedComment.getAuthor().getId())
                .orElseThrow(() -> UserNotFoundException.forId(savedComment.getAuthor().getId()));

        savedComment.setAuthor(author);

        return CommentMapper.toCommentDTO(savedComment);
    }

    public CommentDTO getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.forId(commentId));

        return CommentMapper.toCommentDTO(comment);
    }

    public CommentDTO updateComment(Comment updatedComment) {
        Comment existingComment = commentRepository.findById(updatedComment.getId())
                .orElseThrow(() -> CommentNotFoundException.forId(updatedComment.getId()));

        existingComment.setText(updatedComment.getText());

        Comment comment = commentRepository.save(existingComment);

        return CommentMapper.toCommentDTO(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.forId(commentId));

        commentRepository.delete(comment);
    }
}
