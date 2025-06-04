package com.ems.TicketService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<String> handleEventNotAvailableException1(EventNotFoundException ex,WebRequest webRequest){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<String> handleTicketNotAvailableException(TicketNotFoundException ex,WebRequest webRequest){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotAvailableException(UserNotFoundException ex,WebRequest webRequest){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
}
