package com.ems.FeedbackService.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ems.FeedbackService.Client.EventClient;
import com.ems.FeedbackService.Client.UserClient;
import com.ems.FeedbackService.Dto.FeedbackDto;
import com.ems.FeedbackService.Dto.UserResponse;
import com.ems.FeedbackService.Entity.Feedback;
import com.ems.FeedbackService.Exceptions.FeedbackAlreadyExistsException;
import com.ems.FeedbackService.Exceptions.FeedbackNotFoundException;
import com.ems.FeedbackService.Repository.FeedbackRepository;
import com.ems.FeedbackService.Utility.EntityDtoConversion;

import com.ems.FeedbackService.Dto.EventResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeedbackService {
	
	private final FeedbackRepository feedbackRepository;
	private final EventClient eventClient;
	private final UserClient userClient;
	
	//create
	private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    public FeedbackDto saveFeedback(FeedbackDto feedback) {
        if (feedbackRepository.feedbackAlreadyExists(feedback.getUserId(), feedback.getEventId())) {
            throw new FeedbackAlreadyExistsException("Feedback already exists for user ID " + feedback.getUserId() + " and event ID " + feedback.getEventId());
        }

        logger.info("Fetching event details for eventId: {}", feedback.getEventId());
        EventResponse event = eventClient.getByEventSummary(feedback.getEventId());

        logger.info("Fetching user details for userId: {}", feedback.getUserId());
        UserResponse user = userClient.getUserDetails(feedback.getUserId());

        Feedback feedbackEntity = EntityDtoConversion.dtoToEntity(feedback);
        feedbackEntity.setEventName(event.getEventName());
        feedbackEntity.setCategory(event.getCategory());
        feedbackEntity.setLocation(event.getLocation());
        feedbackEntity.setStartDate(event.getStartDate());
        feedbackEntity.setName(user.getName());

        Feedback savedFeedback = feedbackRepository.save(feedbackEntity);
        logger.info("Successfully saved feedback for event: {}", feedbackEntity.getEventName());

        return EntityDtoConversion.entityToDto(savedFeedback);
    }

	
	//read
	public FeedbackDto getFeedbackById(int feedbackId) {
	    Feedback feedbackById = feedbackRepository.findById(feedbackId).orElseThrow(()-> new FeedbackNotFoundException("No feedback found with Id:"+feedbackId));
	    return EntityDtoConversion.entityToDto(feedbackById);
	}


	public List<FeedbackDto> getAllFeedbacksByUser(int userId) {
		//check whether userId is valid
		List<Feedback> feedbackByUserId = feedbackRepository.findByUserId(userId);
		if(feedbackByUserId.isEmpty()) {
			throw new FeedbackNotFoundException("No feedbacks has been given by userId:"+userId);
		}
		return feedbackByUserId.stream().map(EntityDtoConversion::entityToDto).toList();
	}
	
	public List<FeedbackDto> getAllFeedbacksByEvent(int eventId) {
		//check whether eventId is valid
        List<Feedback> feedbacksByEventId = feedbackRepository.findByEventId(eventId);
        if (feedbacksByEventId.isEmpty()) {
            throw new FeedbackNotFoundException("No feedback found for eventId: " + eventId);
        }
        return feedbacksByEventId.stream().map(EntityDtoConversion::entityToDto).toList();
    }

	public float getAverageRatingByEvent(int eventId) {
        float averageRating = feedbackRepository.findAverageRatingByEventId(eventId);
        return averageRating;
    }
	
	//update
	public FeedbackDto updateFeedback(int feedbackId, FeedbackDto feedback) {
	    Feedback existingFeedback = feedbackRepository.findById(feedbackId)
	        .orElseThrow(() -> new FeedbackNotFoundException("Feedback not found with ID: " + feedbackId));

	    existingFeedback.setComments(feedback.getComments());
	    existingFeedback.setRating(feedback.getRating());
	    existingFeedback.setTimestamp(LocalDateTime.now()); 

	    feedbackRepository.save(existingFeedback);
	    return EntityDtoConversion.entityToDto(existingFeedback);
	}

		
	//Delete
	public String deleteFeedback(int feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new FeedbackNotFoundException("Feedback not found with ID: " + feedbackId));
        feedbackRepository.delete(feedback);
        return "Feedback deleted successfully!";
    }
	
	
	

}
