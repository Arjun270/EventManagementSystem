package com.ems.UserService.Exceptions;

public class OrganizerAlreadyExistsException extends RuntimeException{
	public OrganizerAlreadyExistsException(String message) {
		super(message);
	}
}
