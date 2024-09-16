package com.angelokezimana.posta.exception.blog;


import jakarta.persistence.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException(String message) {
        super(message);
    }

    public static PostNotFoundException forId(Long postId) {
        return new PostNotFoundException("Post with " + postId + " not found");
    }
}
