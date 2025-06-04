package com.ems.EventService.Utility;

import com.ems.EventService.Dto.EventDto;
import com.ems.EventService.Entity.Event;

public class EntityDtoConversion {

    public static EventDto EntitytoDto(Event event) {
        return EventDto.builder()
                .eventId(event.getEventId())
                .name(event.getName())
                .category(event.getCategory())
                .location(event.getLocation())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate()) 
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .address(event.getAddress())
                .description(event.getDescription())
                .organizerId(event.getOrganizerId())
                .ticketCount(event.getTicketCount())
                .build();
    }

    public static Event DtotoEntity(EventDto eventDto) {
        return Event.builder()
                .eventId(eventDto.getEventId())
                .name(eventDto.getName())
                .category(eventDto.getCategory())
                .location(eventDto.getLocation())
                .startDate(eventDto.getStartDate())
                .endDate(eventDto.getEndDate())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .address(eventDto.getAddress())
                .description(eventDto.getDescription())
                .organizerId(eventDto.getOrganizerId())
                .ticketCount(eventDto.getTicketCount())
                .build();
    }
}

