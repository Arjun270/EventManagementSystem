package com.ems.FeedbackService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ems.FeedbackService.Dto.EventResponse;

@FeignClient(name = "EventService")
public interface EventClient {
    @GetMapping("/events/getEventById/{eventId}")
    public EventResponse getByEventSummary(@PathVariable int eventId);
}
