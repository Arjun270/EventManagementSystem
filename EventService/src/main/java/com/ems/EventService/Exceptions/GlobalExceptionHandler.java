package com.ems.EventService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<String> handleEventNotAvailableException(EventNotFoundException ex,WebRequest webRequest){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidOrganizerIDException.class)
	public ResponseEntity<String> handleInvalidOrganizerIDException(InvalidOrganizerIDException ex,WebRequest webRequest){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
}
