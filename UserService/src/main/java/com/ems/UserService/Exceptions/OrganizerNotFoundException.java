package com.ems.UserService.Exceptions;

public class OrganizerNotFoundException extends RuntimeException{
	public OrganizerNotFoundException(String message) {
        super(message);
    }
}
