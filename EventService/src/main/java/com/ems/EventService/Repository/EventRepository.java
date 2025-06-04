package com.ems.EventService.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ems.EventService.Entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	List<Event> findByCategoryIgnoreCase(String category);

	List<Event> findByLocationIgnoreCase(String category);
	
	@Query("SELECT e FROM Event e WHERE e.startDate >= :startDate AND e.endDate <= :endDate")
	List<Event> findEventsByDateRange(LocalDate startDate, LocalDate endDate);

	List<Event> findByNameContainingIgnoreCase(String keyword);

	List<Event> findByOrganizerId(int organizerId);

	Optional<Event> findByEventId(int eventId);

}
