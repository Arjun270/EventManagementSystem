package com.ems.FeedbackService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(FeedbackNotFoundException.class)
	public ResponseEntity<String> HandleFeedbackNotFoundException(FeedbackNotFoundException ex,WebRequest webRequest){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(FeedbackAlreadyExistsException.class)
	public ResponseEntity<String> HandleFeedbackAlreadyExistsException(FeedbackNotFoundException ex,WebRequest webRequest){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
		
	}
}
