package com.ems.EventService.Exceptions;

public class EventNotFoundException extends RuntimeException{
	public EventNotFoundException(String message) {
		super(message);
	}
}
