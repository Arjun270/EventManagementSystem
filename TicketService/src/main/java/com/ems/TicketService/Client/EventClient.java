package com.ems.TicketService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ems.TicketService.Dto.EventDto;

@FeignClient(name = "UserService")
public interface EventClient {
	
	@GetMapping("events/getByEventId/{EventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable int eventId); 
	
}