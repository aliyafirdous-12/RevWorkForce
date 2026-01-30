package com.revworkforce.dao;

import java.util.List;

import com.revworkforce.model.LeaveRequest;

public interface LeaveRequestDao {
	
	void applyLeave(LeaveRequest leave);
	
	List<LeaveRequest> getLeavesByEmp(int empId);

	boolean cancelLeave(int leaveId);

	List<LeaveRequest> getPendingLeaves(int managerId);

	void updateLeaveStatus(int leaveId, String status, String comment);
	
	void getLeaveReport();

}