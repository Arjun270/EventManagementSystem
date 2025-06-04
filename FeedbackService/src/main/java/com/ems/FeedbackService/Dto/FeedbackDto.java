package com.ems.FeedbackService.Dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

	    private int userId;
	    
	    private int eventId;
	    
	    @NotBlank(message = "Comments cannot be blank")
	    private String comments;

	    @Min(value = 1, message = "Rating must be at least 1")
	    @Max(value = 5, message = "Rating must be at most 5")
	    private float rating;

}
