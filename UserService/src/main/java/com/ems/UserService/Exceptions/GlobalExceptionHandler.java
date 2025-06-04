package com.ems.UserService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(OrganizerNotFoundException.class)
    public ResponseEntity<String> handleOrganizerNotFoundException(OrganizerNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
       return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(OrganizerAlreadyExistsException.class)
    public ResponseEntity<String> handleOrganizerAlreadyExistsException(OrganizerAlreadyExistsException ex, WebRequest request) {
       return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    

    
    
}
