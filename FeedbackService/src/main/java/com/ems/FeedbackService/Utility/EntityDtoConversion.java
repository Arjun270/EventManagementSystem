package com.ems.FeedbackService.Utility;

import com.ems.FeedbackService.Dto.FeedbackDto;
import com.ems.FeedbackService.Entity.Feedback;

public class EntityDtoConversion {

    // Convert Entity to DTO
    public static FeedbackDto entityToDto(Feedback feedback) {
        return FeedbackDto.builder()
                .userId(feedback.getUserId())
                .eventId(feedback.getEventId())
                .comments(feedback.getComments())
                .rating(feedback.getRating())
                .build();
    }

    // Convert DTO to Entity
    public static Feedback dtoToEntity(FeedbackDto feedbackDto) {
        return Feedback.builder()
                .userId(feedbackDto.getUserId())
                .eventId(feedbackDto.getEventId())
                .comments(feedbackDto.getComments())
                .rating(feedbackDto.getRating())
                .build();
    }
}
