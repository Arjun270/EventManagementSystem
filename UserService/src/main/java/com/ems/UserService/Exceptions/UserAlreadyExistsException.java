package com.ems.UserService.Exceptions;

public class UserAlreadyExistsException extends RuntimeException{
	public UserAlreadyExistsException(String message) {
        super(message);
    }
}
