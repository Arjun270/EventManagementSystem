package com.ems.TicketService.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ems.TicketService.Client.EventClient;
import com.ems.TicketService.Client.UserClient;
import com.ems.TicketService.Dto.EventDto;
import com.ems.TicketService.Dto.TicketDto;
import com.ems.TicketService.Dto.UserDto;
import com.ems.TicketService.Entity.Ticket;
import com.ems.TicketService.Exception.EventNotFoundException;
import com.ems.TicketService.Exception.TicketNotFoundException;
import com.ems.TicketService.Exception.UserNotFoundException;
import com.ems.TicketService.Repository.TicketRepository;
import com.ems.TicketService.Utility.EntityDtoConversion;
import com.ems.TicketService.Utility.Status;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserClient userClient;
    private final EventClient eventClient;

    // Create
    public String bookTicket(TicketDto ticketDto) {
        // Validate user
        UserDto user = userClient.getUserById(ticketDto.getUserId()).getBody();
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + ticketDto.getUserId());
        }
        // Validate event
        EventDto event = eventClient.getEventById(ticketDto.getEventId()).getBody();
        if (event == null) {
            throw new EventNotFoundException("Event not found with ID: " + ticketDto.getEventId());
        }
        Ticket ticket = EntityDtoConversion.dtoToEntity(ticketDto);
        ticket.setBookingDate(LocalDateTime.now());
        ticket.setStatus(Status.BOOKED);
        ticketRepository.save(ticket);
        return "Ticket Booked Successfully";
    }

    public TicketDto getTicketById(int ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
        return EntityDtoConversion.entityToDto(ticket);
    }

    public List<TicketDto> getAllTickets(){
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("No tickets found");
        }
        return tickets.stream().map(EntityDtoConversion::entityToDto).collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsByUserId(int userId){
        UserDto user = userClient.getUserById(userId).getBody();
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        List<Ticket> ticketsByUserId = ticketRepository.findByUserId(userId);
        if (ticketsByUserId.isEmpty()) {
            throw new TicketNotFoundException("No tickets have been booked by this userId " + userId);
        }
        return ticketsByUserId.stream().map(EntityDtoConversion::entityToDto).collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsByEventId(int eventId){
        EventDto event = eventClient.getEventById(eventId).getBody();
        if (event == null) {
            throw new EventNotFoundException("Event not found with ID: " + eventId);
        }
        List<Ticket> ticketsByEventId = ticketRepository.findByEventId(eventId);
        if (ticketsByEventId.isEmpty()) {
            throw new TicketNotFoundException("No tickets have been booked for this event " + eventId);
        }
        return ticketsByEventId.stream().map(EntityDtoConversion::entityToDto).collect(Collectors.toList());
    }

    public String cancelTicket(int ticketId, int userId, int eventId){
        Ticket existingTicket = ticketRepository.findByTicketId(ticketId);
        if (existingTicket == null) {
            throw new TicketNotFoundException("There are no tickets with such Id");
        }
        if (existingTicket.getUserId() != userId) {
            throw new UserNotFoundException("There are no users with userId " + userId);
        }
        if (existingTicket.getEventId() != eventId) {
            throw new EventNotFoundException("There are no events with such eventId " + eventId);
        }
        existingTicket.setCancelingDate(LocalDateTime.now());
        existingTicket.setStatus(Status.CANCELLED);
        ticketRepository.save(existingTicket);
        return "Ticket cancellation success";
    }
}