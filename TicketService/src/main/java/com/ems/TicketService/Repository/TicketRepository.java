package com.ems.TicketService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.TicketService.Entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	Ticket findByTicketId(int ticketId);

	List<Ticket> findByUserId(int userId);

	List<Ticket> findByEventId(int eventId);

}
