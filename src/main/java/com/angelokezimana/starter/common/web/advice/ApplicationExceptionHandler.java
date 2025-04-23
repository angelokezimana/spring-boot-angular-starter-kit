/**
 * Created By angelokezimana
 * Date: 1/1/2025
 * Time: 5:24 AM
 */

package com.angelokezimana.starter.common.web.advice;

import com.angelokezimana.starter.common.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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

    private final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        logger.error("MethodArgumentNotValidException handled: ", ex);

        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseDto handleException(EntityNotFoundException ex) {
        logger.error("EntityNotFoundException handled: ", ex);
        return new ResponseDto("errorMessage", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.LOCKED)
    @ExceptionHandler(LockedException.class)
    public ResponseDto handleException(LockedException ex) {
        logger.error("LockedException handled: ", ex);
        return new ResponseDto("errorMessage", "Your account is locked. Please contact your administrator for assistance.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DisabledException.class)
    public ResponseDto handleException(DisabledException ex) {
        logger.error("DisabledException handled: ", ex);
        return new ResponseDto("errorMessage", "Your account is disabled. Please contact your administrator for assistance.");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseDto handleException(BadCredentialsException ex) {
        logger.error("BadCredentialsException handled: ", ex);
        return new ResponseDto("errorMessage", "Invalid username or password.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MessagingException.class)
    public ResponseDto handleException(MessagingException ex) {
        logger.error("MessagingException handled: ", ex);
        return new ResponseDto("errorMessage", "Error while sending email: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseDto handleException(ExpiredJwtException ex) {
        logger.error("ExpiredJwtException handled: ", ex);
        return new ResponseDto("errorMessage", "Your session has expired or the token is not valid. Please log in again.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDto handleException(AccessDeniedException ex) {
        logger.error("AccessDeniedException handled: ", ex);
        return new ResponseDto("errorMessage", "Access Denied");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseDto handleException(Exception ex) {
        logger.error("Exception handled: ", ex);
        return new ResponseDto("errorMessage", ex.getMessage());
    }
}
