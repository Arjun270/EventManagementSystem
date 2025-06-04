package com.ems.NotificationService.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.NotificationService.Entity.Notification;
import com.ems.NotificationService.Service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	@PostMapping("/sendNotification")
    public String sendNotification(@RequestBody @Valid Notification notification) {
		return notificationService.sendNotification(notification);
	}
	
	@GetMapping("/getAllNotificationsByUserId/{userId}")
    public List<Notification> getAllNotificationsByUserId(@PathVariable int userId) {
        return notificationService.getAllNotificationsByUserId(userId);
    }
	
	@GetMapping("/getAllNotificationsByEventId/{eventId}")
    public List<Notification> getAllNotificationsByEventId(@PathVariable int eventId) {
        return notificationService.getAllNotificationsByEventId(eventId);
    }
	
	@GetMapping("/getNotificationById/{notificationId}")
    public Optional<Notification> getNotificationById(@PathVariable int notificationId) {
        return notificationService.getNotificationById(notificationId);
    }
	
	//NotificationScheduler need to done after connecting everything together
}
