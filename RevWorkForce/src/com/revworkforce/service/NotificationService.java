package com.revworkforce.service;

import java.sql.*;
import java.util.*;

import com.revworkforce.dao.NotificationDao;
import com.revworkforce.model.Notification;

import com.revworkforce.db.DBConnection;

import org.apache.log4j.Logger;

public class NotificationService implements NotificationDao{
	
	private static final Logger logger =
            Logger.getLogger(NotificationService.class);

	Connection connection = DBConnection.getConnection();
	
	public NotificationService(Connection connection) {
        this.connection = connection;
    }

    public NotificationService() {}
	
	public void sendNotification(int empId, String msg) {
		
		logger.info("Sending notification to empId=" + empId);

        try {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO NOTIFICATION VALUES (NOTIF_SEQ.NEXTVAL,?,?, 'UNREAD', SYSDATE)");

            ps.setInt(1, empId);
            ps.setString(2, msg);
            ps.executeUpdate();
            
            logger.info("Notification sent successfully to empId=" + empId);

        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while sending notification to empId=" + empId, e);
        }
        
    }
	
	public List<Notification> getNotifications(int empId) {
		
		logger.debug("Fetching notifications for empId=" + empId);
		
		List<Notification> list = new ArrayList<Notification>();

        try {
            PreparedStatement ps = connection.prepareStatement(
            		"SELECT * FROM NOTIFICATION WHERE EMP_ID=? ORDER BY CREATED_DATE DESC");

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Notification notification = new Notification();
                notification.setNotifId(rs.getInt("NOTIF_ID"));
                notification.setEmpId(rs.getInt("EMP_ID"));
                notification.setMessage(rs.getString("MESSAGE"));
                notification.setStatus(rs.getString("STATUS"));
                notification.setCreatedDate(rs.getDate("CREATED_DATE"));
                notification.setRead(rs.getString("IS_READ"));
                list.add(notification);
            }
            
            logger.info("Total notifications fetched for empId=" + empId + " = " + list.size());

        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while fetching notifications for empId=" + empId, e);
        	
        }
        return list;
        
	}
	
    public void markAsRead(int empId) {
    	
    	logger.info("Marking notifications as read for empId=" + empId);
    	
    	try {
    		PreparedStatement ps = connection.prepareStatement(
                    "UPDATE NOTIFICATION SET IS_READ='Y' WHERE EMP_ID=?");
                ps.setInt(1, empId);
                ps.executeUpdate();
                
                logger.info("Notifications marked as read for empId=" + empId);

            } catch (Exception e) {
               // e.printStackTrace();
            	logger.error("Error while marking notifications as read for empId=" + empId, e);
        }
                
    }
	
	public int getUnreadCount(int empId) {
		
		logger.debug("Fetching unread notification count for empId=" + empId);

        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT COUNT(*) FROM NOTIFICATION WHERE EMP_ID=? AND STATUS='UNREAD'");

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
            	//return rs.getInt(1);
            	int count = rs.getInt(1);
                logger.info("Unread notification count for empId=" + empId + " = " + count);
                return count;

            }

        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while fetching unread notification count for empId=" + empId, e);
        }
        return 0;
    }

}