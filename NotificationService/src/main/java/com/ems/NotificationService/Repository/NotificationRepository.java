package com.ems.NotificationService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.NotificationService.Entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	boolean existsByUserIdAndEventIdAndMessage(int userId, int eventId, String message);

	List<Notification> findByUserId(int userId);

	List<Notification> findByEventId(int eventId);

}
