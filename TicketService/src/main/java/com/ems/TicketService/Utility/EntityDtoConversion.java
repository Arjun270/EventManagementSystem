package com.ems.TicketService.Utility;

import com.ems.TicketService.Dto.TicketDto;
import com.ems.TicketService.Entity.Ticket;

public class EntityDtoConversion {

    // Entity to DTO
    public static TicketDto entityToDto(Ticket ticket) {
        if (ticket == null) return null;
        TicketDto dto = new TicketDto();
        dto.setTicketId(ticket.getTicketId());
        dto.setEventId(ticket.getEventId());
        dto.setUserId(ticket.getUserId());
        dto.setBookingDate(ticket.getBookingDate());
        dto.setCancelingDate(ticket.getCancelingDate());
        dto.setStatus(ticket.getStatus());
        return dto;
    }

    // DTO to Entity
    public static Ticket dtoToEntity(TicketDto dto) {
        if (dto == null) return null;
        Ticket ticket = new Ticket();
        ticket.setTicketId(dto.getTicketId());
        ticket.setEventId(dto.getEventId());
        ticket.setUserId(dto.getUserId());
        ticket.setBookingDate(dto.getBookingDate());
        ticket.setCancelingDate(dto.getCancelingDate());
        ticket.setStatus(dto.getStatus());
        return ticket;
    }
}
