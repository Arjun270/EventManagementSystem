package com.ems.NotificationService.Exception;

import com.ems.NotificationService.Entity.Notification;

public class NotificationsNotFoundException extends RuntimeException{
	public NotificationsNotFoundException(String message) {
		super(message);
	}
}
