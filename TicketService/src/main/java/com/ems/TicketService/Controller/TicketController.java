package com.ems.TicketService.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.TicketService.Dto.TicketDto;
import com.ems.TicketService.Service.TicketService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {
	
	private final TicketService ticketService;
	
	//create
	@PostMapping("/book")
    public String bookTicket(@RequestBody @Valid TicketDto ticket) {
        return ticketService.bookTicket(ticket);
    }
	
	//read
	@GetMapping("/getTicketById/{ticketId}")
    public TicketDto getTicketById(@PathVariable int ticketId) {
        return ticketService.getTicketById(ticketId);
    }
	
	@GetMapping("/getTicketByUserId/{userId}")
    public List<TicketDto> getTicketsByUserId(@PathVariable int userId) {
        return ticketService.getTicketsByUserId(userId);
    }
	
	@GetMapping("/getTicketByEventId/{eventId}")
    public List<TicketDto> getTicketsByEventId(@PathVariable int eventId) {
        return ticketService.getTicketsByEventId(eventId);
    }
	
	@GetMapping("/getAllTickets")
	public List<TicketDto> getAllTickets() {
		return ticketService.getAllTickets();
		
	}
	
	//update
	@PutMapping("/cancel/{ticketId}")
	public String cancelTicket(@PathVariable int ticketId,@RequestParam int eventId,@RequestParam int userId) {
		return ticketService.cancelTicket(ticketId, eventId, userId);
	}
	
	
	
}
