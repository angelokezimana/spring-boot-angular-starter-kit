package com.angelokezimana.posta.exception.blog;


public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }

    public static CommentNotFoundException forId(Long commentId) {
        return new CommentNotFoundException("Comment with " + commentId + " not found");
    }
}
