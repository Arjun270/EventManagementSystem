package com.ems.EventService.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;

    private String name;

    private String category;

    private String location;

    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private LocalTime startTime;
    
    private LocalTime endTime;
    
    private String address;
    
    private String description;
    
    private int organizerId;

    private int ticketCount;
}
