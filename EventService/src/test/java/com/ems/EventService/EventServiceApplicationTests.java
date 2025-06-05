package com.ems.EventService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ems.EventService.Client.UserClient;
import com.ems.EventService.Dto.EventDto;
import com.ems.EventService.Dto.OrganizerDto;
import com.ems.EventService.Entity.Event;
import com.ems.EventService.Exceptions.EventNotFoundException;
import com.ems.EventService.Repository.EventRepository;
import com.ems.EventService.Service.EventService;

@ExtendWith(MockitoExtension.class)
class EventServiceApplicationTests {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private EventDto eventDto;
    private OrganizerDto organizerDto;

    @BeforeEach
    void setUp() {
        organizerDto = new OrganizerDto();
        organizerDto.setOrganizerId(1);

        event = Event.builder()
                .eventId(1)
                .name("Test Event")
                .category("Test Category")
                .location("Test Location")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .startTime(LocalTime.NOON)
                .endTime(LocalTime.MIDNIGHT)
                .address("Test Address")
                .description("Test Description")
                .organizerId(1)
                .ticketCount(100)
                .build();

        eventDto = EventDto.builder()
                .eventId(1)
                .name("Test Event")
                .category("Test Category")
                .location("Test Location")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .startTime(LocalTime.NOON)
                .endTime(LocalTime.MIDNIGHT)
                .address("Test Address")
                .description("Test Description")
                .organizerId(1)
                .ticketCount(100)
                .build();
    }

    // CREATE TESTS
    @Test
    void createEvent_Success() {
        when(userClient.getOrganizerById(anyInt())).thenReturn(ResponseEntity.ok(organizerDto));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        EventDto result = eventService.createEvent(eventDto);

        assertNotNull(result);
        assertEquals(eventDto.getName(), result.getName());
        verify(eventRepository).save(any(Event.class));
    }

	@Test
	void createEvent_FailsWhenOrganizerNotFound() {
		when(userClient.getOrganizerById(anyInt())).thenReturn(ResponseEntity.notFound().build());

		assertThrows(com.ems.EventService.Exceptions.InvalidOrganizerIDException.class, () -> eventService.createEvent(eventDto));
		verify(eventRepository, never()).save(any(Event.class));
	}
    // READ TESTS
    @Test
    void getEventById_Success() {
        when(eventRepository.findByEventId(1)).thenReturn(Optional.of(event));

        EventDto result = eventService.getEventById(1);

        assertNotNull(result);
        assertEquals(event.getName(), result.getName());
    }

    @Test
    void getEventById_NotFound() {
        when(eventRepository.findByEventId(999)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(999));
    }

    @Test
    void getAllEvents_Success() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));

        var results = eventService.getAllEvents();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void searchEventsByName_Success() {
        when(eventRepository.findByNameContainingIgnoreCase("Test"))
            .thenReturn(Arrays.asList(event));

        var results = eventService.searchEventsByName("Test");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    // FILTER TESTS
    @Test
    void filterByCategory_Success() {
        when(eventRepository.findByCategoryIgnoreCase("Test Category"))
            .thenReturn(Arrays.asList(event));

        var results = eventService.filterByCategory("Test Category");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void filterByLocation_Success() {
        when(eventRepository.findByLocationIgnoreCase("Test Location"))
            .thenReturn(Arrays.asList(event));

        var results = eventService.filterByLocation("Test Location");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void filterByDateRange_Success() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);
        when(eventRepository.findEventsByDateRange(start, end))
            .thenReturn(Arrays.asList(event));

        var results = eventService.filterByDateRange(start, end);

        assertFalse(results.isEmpty());
        assertEquals(start, results.get(0).getStartDate());
    }

    @Test
    void getEventsByOrganizer_Success() {
        when(eventRepository.findByOrganizerId(1)).thenReturn(Arrays.asList(event));

        var results = eventService.getEventsByOrganizer(1);

        assertFalse(results.isEmpty());
        assertEquals(1, results.get(0).getOrganizerId());
    }

    // UPDATE TESTS
    @Test
    void updateEvent_Success() {
        when(eventRepository.findByEventId(1)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        String result = eventService.updateEvent(1, eventDto);

        assertEquals("Event updated successfully", result);
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEvent_FailsWhenEventNotFound() {
        when(eventRepository.findByEventId(999)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, 
            () -> eventService.updateEvent(999, eventDto));
        verify(eventRepository, never()).save(any(Event.class));
    }

    // DELETE TESTS
    @Test
    void deleteEvent_Success() {
        when(eventRepository.findByEventId(1)).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).delete(any(Event.class));

        String result = eventService.deleteEvent(1);

        assertEquals("Event deleted successfully.", result);
        verify(eventRepository).delete(any(Event.class));
    }

    @Test
    void deleteEvent_FailsWhenEventNotFound() {
        when(eventRepository.findByEventId(999)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, 
            () -> eventService.deleteEvent(999));
        verify(eventRepository, never()).delete(any(Event.class));
    }
}