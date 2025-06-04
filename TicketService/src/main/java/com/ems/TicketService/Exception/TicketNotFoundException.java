package com.ems.TicketService.Exception;

public class TicketNotFoundException extends RuntimeException{
	public TicketNotFoundException(String message) {
		super(message);
	}
}
