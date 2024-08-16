package org.meuprojeto.shortened_link.controllers.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.meuprojeto.shortened_link.services.exceptions.ResourceAlreadyExistsException;
import org.meuprojeto.shortened_link.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), "Resource not found", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandardError> resourceExists(ResourceAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), "Resource already exists", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }
}
