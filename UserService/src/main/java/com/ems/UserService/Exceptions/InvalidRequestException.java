package com.ems.UserService.Exceptions;

public class InvalidRequestException extends RuntimeException{
	public InvalidRequestException(String message) {
		super(message);
	}

}
