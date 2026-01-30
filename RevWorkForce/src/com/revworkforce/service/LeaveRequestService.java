package com.revworkforce.service;

import java.sql.*;
import java.util.*;

import com.revworkforce.dao.LeaveRequestDao;
import com.revworkforce.model.LeaveRequest;

import com.revworkforce.db.DBConnection;

public class LeaveRequestService implements LeaveRequestDao{
	
	Connection connection = DBConnection.getConnection();
	
	public void applyLeave(LeaveRequest leave) {

        try {
        	PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO LEAVE_REQUEST " +
                    "(LEAVE_ID, EMP_ID, FROM_DATE, TO_DATE, LEAVE_TYPE, REASON, STATUS, MANAGER_COMMENT) " +
                    "VALUES (LEAVE_SEQ.NEXTVAL, ?, ?, ?, ?, ?, 'PENDING', ?)" 
            );

            ps.setInt(1, leave.getEmpId());
            ps.setDate(2, leave.getFromDate());
            ps.setDate(3, leave.getToDate());
            ps.setString(4, leave.getLeaveType());
            ps.setString(5, leave.getReason());
            ps.setString(6, leave.getManagerComment());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public List<LeaveRequest> getLeavesByEmp(int empId) {

        List<LeaveRequest> list = new ArrayList<LeaveRequest>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM LEAVE_REQUEST WHERE EMP_ID=?");
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest leave = map(rs);
                list.add(leave);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public boolean cancelLeave(int leaveId) {

	    try {
	        PreparedStatement ps = connection.prepareStatement(
	            "UPDATE LEAVE_REQUEST SET STATUS='CANCELLED' " +
	            "WHERE LEAVE_ID=? AND STATUS='PENDING'");

	        ps.setInt(1, leaveId);

	        int rows = ps.executeUpdate();

	        return rows > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	
	public List<LeaveRequest> getPendingLeaves(int managerId) {

        List<LeaveRequest> list = new ArrayList<LeaveRequest>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT L.* FROM LEAVE_REQUEST L JOIN EMPLOYEE E ON L.EMP_ID=E.EMP_ID WHERE E.MANAGER_ID=? AND L.STATUS='PENDING'");
            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        
    }
	
	public void updateLeaveStatus(int leaveId, String status, String comment) {

        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE LEAVE_REQUEST SET STATUS=?, MANAGER_COMMENT=? WHERE LEAVE_ID=?");

            ps.setString(1, status);
            ps.setString(2, comment);
            ps.setInt(3, leaveId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void getLeaveReport() {

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM LEAVE_REQUEST");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("LEAVE_ID") + " | " +
                    rs.getInt("EMP_ID") + " | " +
                    rs.getString("LEAVE_TYPE") + " | " +
                    rs.getString("STATUS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private LeaveRequest map(ResultSet rs) throws Exception {

        LeaveRequest leave = new LeaveRequest();
        
        leave.setLeaveId(rs.getInt("LEAVE_ID"));
        leave.setEmpId(rs.getInt("EMP_ID"));
        leave.setFromDate(rs.getDate("FROM_DATE"));
        leave.setToDate(rs.getDate("TO_DATE"));
        leave.setLeaveType(rs.getString("LEAVE_TYPE"));
        leave.setReason(rs.getString("REASON"));
        leave.setStatus(rs.getString("STATUS"));
        leave.setManagerComment(rs.getString("MANAGER_COMMENT"));
        return leave;
        
    }

}