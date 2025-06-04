package com.ems.TicketService.Exception;

public class EventNotFoundException extends RuntimeException{
	public EventNotFoundException(String message) {
		super(message);
	}
}
