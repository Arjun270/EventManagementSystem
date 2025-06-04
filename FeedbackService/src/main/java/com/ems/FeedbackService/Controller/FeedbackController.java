package com.ems.FeedbackService.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.FeedbackService.Dto.FeedbackDto;
import com.ems.FeedbackService.Service.FeedbackService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/feedbacks")
@AllArgsConstructor
public class FeedbackController {
	
	private final FeedbackService feedbackService;
	
	//create
	@PostMapping("/create")
    public ResponseEntity<FeedbackDto> saveFeedback(@RequestBody FeedbackDto feedback) {
		return new ResponseEntity<>(feedbackService.saveFeedback(feedback),HttpStatus.CREATED);
	}
	
	//read
	@GetMapping("/getFeedbackById/{feedbackId}")
    public FeedbackDto getFeedbackById(@PathVariable int feedbackId) {
        return feedbackService.getFeedbackById(feedbackId);
    }
	
	@GetMapping("/getAllFeedbacksByUser/{userId}")
	public List<FeedbackDto> getAllFeedbacksByUser(@PathVariable int userId) {
        return feedbackService.getAllFeedbacksByUser(userId);
    }
	
	@GetMapping("/getAllFeedbacksByEvent/{eventId}")
    public List<FeedbackDto> getAllFeedbacksByEvent(@PathVariable int eventId) {
        return feedbackService.getAllFeedbacksByEvent(eventId);
    }

    @GetMapping("/getAverageRatingByEvent/{eventId}")
    public String getAverageRatingByEvent(@PathVariable int eventId) {
        float averageRating = feedbackService.getAverageRatingByEvent(eventId);
        return "The average rating for the event with ID " + eventId + " is: " + averageRating;
    }
    
    //update
    @PutMapping("/updateByFeedbackId/{feedbackId}")
    public FeedbackDto updateFeedback(@PathVariable int feedbackId, @RequestBody FeedbackDto feedback) {
        return feedbackService.updateFeedback(feedbackId,feedback);
    }
    
    //delete
    @DeleteMapping("/deleteByFeedbackId/{feedbackId}")
    public String deleteFeedback(@PathVariable int feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return "Feedback deleted successfully!";
    }
}
