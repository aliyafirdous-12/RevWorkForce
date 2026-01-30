package com.revworkforce.dao;

import java.util.List;

import com.revworkforce.model.Notification;

public interface NotificationDao {
	
	void sendNotification(int empId, String message);

	List<Notification> getNotifications(int empId);
	
	int getUnreadCount(int empId);

	void markAsRead(int empId);

}
