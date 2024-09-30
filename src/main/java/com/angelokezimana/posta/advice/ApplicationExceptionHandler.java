package com.angelokezimana.posta.advice;

import com.angelokezimana.posta.dto.ResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseDTO handleException(EntityNotFoundException ex) {
        return new ResponseDTO("errorMessage", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.LOCKED)
    @ExceptionHandler(LockedException.class)
    public ResponseDTO handleException(LockedException ex) {
        return new ResponseDTO("errorMessage", "Your account is locked. Please contact your administrator for assistance.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DisabledException.class)
    public ResponseDTO handleException(DisabledException ex) {
        return new ResponseDTO("errorMessage", "Your account is disabled. Please contact your administrator for assistance.");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseDTO handleException(BadCredentialsException ex) {
        return new ResponseDTO("errorMessage", "Invalid username or password.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MessagingException.class)
    public ResponseDTO handleException(MessagingException ex) {
        return new ResponseDTO("errorMessage", "Error while sending email: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseDTO handleException(ExpiredJwtException ex) {
        return new ResponseDTO("errorMessage", "Your session has expired or the token is not valid. Please log in again.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseDTO handleException(Exception ex) {
        return new ResponseDTO("errorMessage", ex.getMessage());
    }
}
