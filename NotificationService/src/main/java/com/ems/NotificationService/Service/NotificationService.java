package com.ems.NotificationService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.NotificationService.Entity.Notification;
import com.ems.NotificationService.Exception.NotificationAlreadyExistsException;
import com.ems.NotificationService.Exception.NotificationsNotFoundException;
import com.ems.NotificationService.Repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	NotificationRepository notificationRepository;

	public String sendNotification(Notification notification) {
		// TODO Auto-generated method stub
		//validation required for userID and EventID
		if (notificationRepository.existsByUserIdAndEventIdAndMessage(notification.getUserId(), notification.getEventId(), notification.getMessage())) {
            throw new NotificationAlreadyExistsException("Notification already exists for user ID: " + notification.getUserId() + ", for this event ID: " + notification.getEventId());
        }
        notificationRepository.save(notification);
		return "notification created";
	}

	public List<Notification> getAllNotificationsByUserId(int userId) {
		// TODO Auto-generated method stub
		List<Notification> NotificationsByUserId = notificationRepository.findByUserId(userId);
		if(NotificationsByUserId.isEmpty()) {
			throw new NotificationsNotFoundException("No notifications was found for this userId"+userId);
		}
		return NotificationsByUserId; 
	}

	public List<Notification> getAllNotificationsByEventId(int eventId) {
		// TODO Auto-generated method stub
		List<Notification> NotificationsByEventId = notificationRepository.findByEventId(eventId);
		if(NotificationsByEventId.isEmpty()) {
			throw new NotificationsNotFoundException("No notifications was found for this eventId"+eventId);
		}
		return NotificationsByEventId; 
	}

	public Optional<Notification> getNotificationById(int notificationId) throws NotificationsNotFoundException{
		// TODO Auto-generated method stub
		Optional<Notification> notificationById = notificationRepository.findById(notificationId);
		if(notificationById==null) {
			throw new NotificationsNotFoundException("No notification found by notificationID"+notificationId);
		}
		return notificationById;
	}
	
	
	
}
