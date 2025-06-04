package com.ems.FeedbackService.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
@Entity
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @NotNull(message = "User ID cannot be null")
    private int userId;
    private String name;

    @NotNull(message = "Event ID cannot be null")
    private int eventId;
    private String eventName;
    private String category;
    private String location;
    private LocalDate startDate;
    
    @NotBlank(message = "Comments cannot be blank")
    private String comments;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private float rating;
    
    @Builder.Default
    private LocalDateTime timestamp=LocalDateTime.now();
}
