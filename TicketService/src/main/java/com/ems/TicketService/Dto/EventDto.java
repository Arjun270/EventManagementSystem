package com.ems.TicketService.Dto;


import java.time.LocalDate;
import java.time.LocalTime;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private int eventId;

    @NotBlank(message = "Event name cannot be blank")
    private String name;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Event date must be in the present or future")
    private LocalDate startDate;
    
    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Event date must be in the present or future")
    private LocalDate endDate;
    
    @NotNull(message = "Time cannot be null")
    private LocalTime startTime;
    
    @NotNull(message = "Time cannot be null")
    private LocalTime endTime;
    
    @NotBlank(message="Dont let description be blank")
    private String description;

    @NotBlank(message="Address cannot be blank")
    private String address;
    
    @NotNull(message = "OrganizerID cannot be blank")
    private int organizerId;

    @Min(value = 1, message = "Ticket count cannot be negative")
    private int ticketCount;
}