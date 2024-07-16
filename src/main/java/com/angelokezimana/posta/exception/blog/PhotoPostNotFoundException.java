package com.angelokezimana.posta.exception.blog;


public class PhotoPostNotFoundException extends RuntimeException{
    public PhotoPostNotFoundException(String message) {
        super(message);
    }

    public static PhotoPostNotFoundException forId(Long photoPostId) {
        return new PhotoPostNotFoundException("PhotoPost with " + photoPostId + " not found");
    }
}
