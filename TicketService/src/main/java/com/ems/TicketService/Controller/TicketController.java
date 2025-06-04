package com.ems.TicketService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.TicketService.Entity.Ticket;
import com.ems.TicketService.Service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	//create
	@PostMapping("/book")
    public String bookTicket(@RequestBody @Valid Ticket ticket) {
        return ticketService.bookTicket(ticket);
    }
	
	//read
	@GetMapping("/getTicketById/{ticketId}")
    public Ticket getTicketById(@PathVariable int ticketId) {
        return ticketService.getTicketById(ticketId);
    }
	
	@GetMapping("/getAllTickets")
	public List<Ticket> getAllTickets() {
		return ticketService.getAllTickets();
		
	}
	
	@GetMapping("/getTicketByUserId/{userId}")
    public List<Ticket> getTicketsByUserId(@PathVariable int userId) {
        return ticketService.getTicketsByUserId(userId);
    }
	
	@GetMapping("/getTicketByEventId/{eventId}")
    public List<Ticket> getTicketsByEventId(@PathVariable int eventId) {
        return ticketService.getTicketsByEventId(eventId);
    }
	
	//update
	@PutMapping("/cancel/{ticketId}")
	public String cancelTicket(@PathVariable int ticketId,@RequestParam int eventId,@RequestParam int userId) {
		return ticketService.cancelTicket(ticketId, eventId, userId);
	}
	
	
	
}
