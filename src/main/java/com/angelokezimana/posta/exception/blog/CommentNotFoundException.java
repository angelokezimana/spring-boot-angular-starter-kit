package com.angelokezimana.posta.exception.blog;


import jakarta.persistence.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException(String message) {
        super(message);
    }

    public static CommentNotFoundException forId(Long commentId) {
        return new CommentNotFoundException("Comment with " + commentId + " not found");
    }
}
