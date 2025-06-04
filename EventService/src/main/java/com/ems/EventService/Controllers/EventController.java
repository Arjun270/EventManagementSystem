package com.ems.EventService.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.EventService.Dto.EventDto;
import com.ems.EventService.Dto.EventResponseDto;
import com.ems.EventService.Service.EventService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {
	
	private final EventService eventService;
	
	//Create
	@PostMapping("/create")
    public ResponseEntity<EventDto> createEvent(@RequestBody @Valid EventDto event) {
		return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(event));
    }
	
	//Read
	@GetMapping("/getEventById/{eventId}")
	public ResponseEntity<EventDto> getByEventId(@PathVariable int eventId){
		return ResponseEntity.ok(eventService.getEventById(eventId));
	}
	
	@GetMapping("/getAllEvents")
	public ResponseEntity<List<EventDto>> getAllEvents(){
		return ResponseEntity.ok(eventService.getAllEvents());
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<EventDto>> searchEventsByName(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        }
        return ResponseEntity.ok(eventService.searchEventsByName(name));
    }
	
	@GetMapping("/filterByCategory/{category}")
	public ResponseEntity<List<EventDto>> getEventByCategory(@PathVariable String category){
		return ResponseEntity.ok(eventService.filterByCategory(category));
	}
	
	@GetMapping("/filterByLocation/{location}")
	public ResponseEntity<List<EventDto>> getEventByLocation(@PathVariable String location){
		return ResponseEntity.ok(eventService.filterByLocation(location));
	}
	
	@GetMapping("/filterByDate")
	public ResponseEntity<List<EventDto>> getEventByDates(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate) {
	    return ResponseEntity.ok(eventService.filterByDateRange(startDate, endDate));
	}

	
	@GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventDto>> getEventsByOrganizer(@PathVariable @Min(1) int organizerId) {
        return ResponseEntity.ok(eventService.getEventsByOrganizer(organizerId));
    }
	
	//Update
	@PutMapping("/updateEventById/{eventId}")
	public ResponseEntity<String> updateEvent(@PathVariable @Min(1) int eventId, @RequestBody @Valid EventDto event){
		return ResponseEntity.ok(eventService.updateEvent(eventId,event));
	}
	
	//Delete
	@DeleteMapping("/deleteEventById/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable @Min(1) int eventId) {
        return ResponseEntity.ok(eventService.deleteEvent(eventId));
    }
	
	//event Summary
	@GetMapping("/events/getEventById/{eventId}")
    public EventResponseDto getByEventSummary(@PathVariable int eventId) {
		return eventService.getByEventSummary(eventId);
	}
}
