package com.ems.FeedbackService.Exceptions;

public class FeedbackAlreadyExistsException extends RuntimeException{
	public FeedbackAlreadyExistsException(String message) {
		super(message);
	}
}
