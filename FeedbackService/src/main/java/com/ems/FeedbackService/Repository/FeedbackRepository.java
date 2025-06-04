package com.ems.FeedbackService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ems.FeedbackService.Entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	List<Feedback> findByUserId(int userId);

	List<Feedback> findByEventId(int eventId);
	
	@Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.eventId = :eventId")
	float findAverageRatingByEventId(int eventId);

	@Query("SELECT COUNT(f) > 0 FROM Feedback f WHERE f.userId = :userId AND f.eventId = :eventId")
	boolean feedbackAlreadyExists(int userId, int eventId);

	
}
