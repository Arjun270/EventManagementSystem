package com.ems.FeedbackService.Exceptions;

public class FeedbackNotFoundException extends RuntimeException{
	public FeedbackNotFoundException(String message) {
		super(message);
	}
}
