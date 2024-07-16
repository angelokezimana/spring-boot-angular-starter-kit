package com.angelokezimana.posta.exception.blog;


public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }

    public static PostNotFoundException forId(Long postId) {
        return new PostNotFoundException("Post with " + postId + " not found");
    }
}
