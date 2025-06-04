package com.ems.TicketService.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @NotNull(message = "Event ID cannot be null")
    @Min(value = 1, message = "Event ID must be greater than 0")
    private int eventId;

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be greater than 0")
    private int userId;

    private LocalDateTime bookingDate = LocalDateTime.now();
    
    private LocalDateTime cancelingDate;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private Status status=Status.BOOKED;

    public enum Status {
        BOOKED, CANCELLED
    }
}
