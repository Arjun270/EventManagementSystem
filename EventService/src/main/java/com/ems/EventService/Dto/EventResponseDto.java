package com.ems.EventService.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {
	private int eventId;
    private String eventName;
    private String category;
    private String location;
    private LocalDate startDate;
}
