package com.ems.TicketService.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.TicketService.Entity.Ticket;
import com.ems.TicketService.Entity.Ticket.Status;
import com.ems.TicketService.Exception.EventNotFoundException;
import com.ems.TicketService.Exception.TicketNotFoundException;
import com.ems.TicketService.Exception.UserNotFoundException;
import com.ems.TicketService.Repository.TicketRepository;

@Service
public class TicketService {
	
	@Autowired
	TicketRepository ticketRepository;

	//create
	public String bookTicket(Ticket ticket) {
		//need to validate userID and eventID
		ticketRepository.save(ticket);
		return "Ticket Booked Successfully";
	}

	public Ticket getTicketById(int ticketId) throws TicketNotFoundException{
		// TODO Auto-generated method stub
		return ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
	}
	
	public List<Ticket> getAllTickets() throws TicketNotFoundException{
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("No tickets found");
        }
        return tickets;
    }

	public List<Ticket> getTicketsByUserId(int userId) throws TicketNotFoundException{
		// TODO Auto-generated method stub
		//userId validation needs to be done
		List<Ticket> ticketsByUserId = ticketRepository.findByUserId(userId);
		if(ticketsByUserId.isEmpty()){
			throw new TicketNotFoundException("No tickets has been booked by this userId"+userId);
		}
		return ticketsByUserId;
	}
	
	public List<Ticket> getTicketsByEventId(int eventId) throws TicketNotFoundException{
		// TODO Auto-generated method stub
		//eventId validation needs to be done
		List<Ticket> ticketsByEventId = ticketRepository.findByEventId(eventId);
		if(ticketsByEventId.isEmpty()){
			throw new TicketNotFoundException("No tickets has been booked for this event"+eventId);
		}
		return ticketsByEventId;
	}
	
	public String cancelTicket(int ticketId,int userId,int eventId) throws TicketNotFoundException,EventNotFoundException,UserNotFoundException{
		Ticket existingTicket = ticketRepository.findByTicketId(ticketId);
		if(existingTicket==null) {
			throw new TicketNotFoundException("There are no tickets with such Id");
		}
		if(existingTicket.getUserId()==userId) {
			if(existingTicket.getEventId()==eventId) {
			existingTicket.setCancelingDate(LocalDateTime.now());
			existingTicket.setStatus(Status.CANCELLED);
			ticketRepository.save(existingTicket);
			return "Ticket cancellation success";
			//add ticket of event by 1
			}
			else {
				throw new EventNotFoundException("There are no events with such eventId"+eventId);
			}
		}
		else {
			throw new UserNotFoundException("There are no users with userId"+userId);
		}
		
	}
}
