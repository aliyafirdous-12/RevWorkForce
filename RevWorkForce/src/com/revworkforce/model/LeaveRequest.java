package com.revworkforce.model;

import java.sql.Date;

public class LeaveRequest {

    private int leaveId;
    private int empId;
    private Date fromDate;
    private Date toDate;
    private String leaveType;
    private String status;
    private String reason;
    private String managerComment;
    
    public LeaveRequest() {}
    
	public LeaveRequest(int leaveId, int empId, Date fromDate, Date toDate,
			String leaveType, String status, String reason,
			String managerComment) {
		
		this.leaveId = leaveId;
		this.empId = empId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.leaveType = leaveType;
		this.status = status;
		this.reason = reason;
		this.managerComment = managerComment;
	}
	
	public int getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getManagerComment() {
		return managerComment;
	}
	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}

}