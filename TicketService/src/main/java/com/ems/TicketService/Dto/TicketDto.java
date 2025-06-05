package com.ems.TicketService.Dto;

import java.time.LocalDateTime;

import com.ems.TicketService.Utility.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
	private int ticketId;
    @NotNull(message = "Event ID cannot be null")
    @Min(value = 1, message = "Event ID must be greater than 0")
    private int eventId;

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be greater than 0")
    private int userId;

    private LocalDateTime bookingDate = LocalDateTime.now();
    
    private LocalDateTime cancelingDate;

    @Enumerated(EnumType.STRING)
    private Status status=Status.BOOKED;

}
