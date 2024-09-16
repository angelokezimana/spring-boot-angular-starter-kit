package com.angelokezimana.posta.exception.blog;


import jakarta.persistence.EntityNotFoundException;

public class PhotoPostNotFoundException extends EntityNotFoundException {
    public PhotoPostNotFoundException(String message) {
        super(message);
    }

    public static PhotoPostNotFoundException forId(Long photoPostId) {
        return new PhotoPostNotFoundException("PhotoPost with " + photoPostId + " not found");
    }
}
