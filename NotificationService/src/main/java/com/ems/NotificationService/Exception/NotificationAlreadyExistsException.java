package com.ems.NotificationService.Exception;

public class NotificationAlreadyExistsException extends RuntimeException{
	public NotificationAlreadyExistsException(String message) {
		super(message);
	}
}
