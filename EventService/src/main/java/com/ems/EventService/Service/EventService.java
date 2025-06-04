package com.ems.EventService.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ems.EventService.Client.UserClient;
import com.ems.EventService.Dto.EventDto;
import com.ems.EventService.Dto.EventResponseDto;
import com.ems.EventService.Dto.OrganizerDto;
import com.ems.EventService.Entity.Event;
import com.ems.EventService.Exceptions.EventNotFoundException;
import com.ems.EventService.Exceptions.InvalidOrganizerIDException;
import com.ems.EventService.Repository.EventRepository;
import com.ems.EventService.Utility.EntityDtoConversion;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class EventService {
	
	private final EventRepository eventRepository;
	
	private final UserClient userClient;

	//create
	public EventDto createEvent(EventDto event) {
	    // Fetch organizer details
	    OrganizerDto organizer = userClient.getOrganizerById(event.getOrganizerId()).getBody();
	    
	    // Validate organizer existence
	    if (organizer == null) {
	        throw new InvalidOrganizerIDException("Organizer does not exist or has an invalid role.");
	    }
	    // Save the event and return DTO conversion
	    return EntityDtoConversion.EntitytoDto(eventRepository.save(EntityDtoConversion.DtotoEntity(event)));
	}
	
	//read
	public EventDto getEventById(int eventId){
        Event event = eventRepository.findByEventId(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
        return EntityDtoConversion.EntitytoDto(event);
    }
	
	public List<EventDto> getAllEvents(){
		List<Event> events= eventRepository.findAll();
		if(events.isEmpty()) {
			throw new EventNotFoundException("No events were registered");
		}
		return events.stream().map(EntityDtoConversion::EntitytoDto).toList();
	}
	
	public List<EventDto> searchEventsByName(String name){
        List<Event> eventsByName = eventRepository.findByNameContainingIgnoreCase(name);
        if (eventsByName.isEmpty()) {
            throw new EventNotFoundException("No events found matching keyword: " + name);
        }
        return eventsByName.stream().map(EntityDtoConversion::EntitytoDto).toList();
    }
	
	public List<EventDto> getEventsByOrganizer(int organizerId){

		List<Event> eventsByOrganizerId = eventRepository.findByOrganizerId(organizerId);
		if(eventsByOrganizerId.isEmpty()) {
		    throw new EventNotFoundException("No events organized by this organizer.");
		}

		return eventsByOrganizerId.stream().map(EntityDtoConversion::EntitytoDto).toList();	
		
	}
	
	public List<EventDto> filterByCategory(String category){
		List<Event> eventsByCategory = eventRepository.findByCategoryIgnoreCase(category);
		if(eventsByCategory.isEmpty()) {
			throw new EventNotFoundException("There are no available events in that category");
		}
		return eventsByCategory.stream().map(EntityDtoConversion::EntitytoDto).toList();
	}

	public List<EventDto> filterByLocation(String location){
		// TODO Auto-generated method stub
		List<Event> eventsByLocation = eventRepository.findByLocationIgnoreCase(location);
		if(eventsByLocation.isEmpty()) {
			throw new EventNotFoundException("There are no available events in that location");
		}
		return eventsByLocation.stream().map(EntityDtoConversion::EntitytoDto).toList();
	}
	
	public List<EventDto> filterByDateRange(LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		List<Event> eventsByDates = eventRepository.findEventsByDateRange(startDate, endDate);
		if(eventsByDates.isEmpty()) {
			throw new EventNotFoundException("There are no available events with these dates");
		}
		return eventsByDates.stream().map(EntityDtoConversion::EntitytoDto).toList();
	}

	//Update
	public String updateEvent(int eventId, EventDto event) {
	    Event existingEvent = eventRepository.findByEventId(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with eventId: " + eventId));

	    existingEvent.setName(event.getName() != null ? event.getName() : existingEvent.getName());
	    existingEvent.setCategory(event.getCategory() != null ? event.getCategory() : existingEvent.getCategory());
	    existingEvent.setAddress(event.getAddress() != null ? event.getAddress() : existingEvent.getAddress());
	    existingEvent.setLocation(event.getLocation() != null ? event.getLocation() : existingEvent.getLocation());
	    existingEvent.setStartDate(event.getStartDate() != null ? event.getStartDate() : existingEvent.getStartDate());
	    existingEvent.setEndDate(event.getEndDate() != null ? event.getEndDate() : existingEvent.getEndDate());
	    existingEvent.setStartTime(event.getStartTime() != null ? event.getStartTime() : existingEvent.getStartTime());
	    existingEvent.setEndTime(event.getEndTime() != null ? event.getEndTime() : existingEvent.getEndTime());
	    existingEvent.setOrganizerId(event.getOrganizerId() > 0 ? event.getOrganizerId() : existingEvent.getOrganizerId());
	    existingEvent.setTicketCount(event.getTicketCount() > 0 ? event.getTicketCount() : existingEvent.getTicketCount());

	    eventRepository.save(existingEvent);
	    return "Event updated successfully";
	}

	//Delete
	public String deleteEvent(int eventId){
        Event event = eventRepository.findByEventId(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
        eventRepository.delete(event);
        return "Event deleted successfully.";
    }

	public EventResponseDto getByEventSummary(int eventId) {
		// TODO Auto-generated method stub
		Event event = eventRepository.findByEventId(eventId).orElseThrow(()-> new EventNotFoundException("Event Not Found with ID: "+eventId));
		EventResponseDto eventResponse = new EventResponseDto(event.getEventId(),event.getName(),event.getCategory(),event.getLocation(),event.getStartDate());
		return eventResponse;
	}


	


	


	
	
}
