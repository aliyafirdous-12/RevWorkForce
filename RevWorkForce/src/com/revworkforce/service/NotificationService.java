package com.revworkforce.service;

import java.sql.*;
import java.util.*;

import com.revworkforce.dao.NotificationDao;
import com.revworkforce.model.Notification;

import com.revworkforce.db.DBConnection;

public class NotificationService implements NotificationDao{

	Connection connection = DBConnection.getConnection();
	
	public void sendNotification(int empId, String msg) {

        try {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO NOTIFICATION VALUES (NOTIF_SEQ.NEXTVAL,?,?, 'UNREAD', SYSDATE)");

            ps.setInt(1, empId);
            ps.setString(2, msg);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
	
	public List<Notification> getNotifications(int empId) {
		
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        
	}
	
    public void markAsRead(int empId) {
    	try {
    		PreparedStatement ps = connection.prepareStatement(
                    "UPDATE NOTIFICATION SET IS_READ='Y' WHERE EMP_ID=?");
                ps.setInt(1, empId);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
        }
                
    }
	
	public int getUnreadCount(int empId) {

        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT COUNT(*) FROM NOTIFICATION WHERE EMP_ID=? AND STATUS='UNREAD'");

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}