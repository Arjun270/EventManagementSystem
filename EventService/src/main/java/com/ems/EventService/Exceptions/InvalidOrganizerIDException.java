package com.ems.EventService.Exceptions;
//Need to access user microservice to use this for role failure
public class InvalidOrganizerIDException extends RuntimeException{
	public InvalidOrganizerIDException(String message) {
		super(message);
	}
}
