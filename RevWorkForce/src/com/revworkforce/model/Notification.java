package com.revworkforce.model;

import java.sql.Date;

public class Notification {

	private int notifId;
    private int empId;
    private String message;
    private String status;
    private Date createdDate;
    private String read;
    
    public Notification() {}
    
	public Notification(int notifId, int empId, String message, String status,
			Date createdDate, String read) {
		
		this.notifId = notifId;
		this.empId = empId;
		this.message = message;
		this.status = status;
		this.createdDate = createdDate;
		this.read = read;
	}
	
	public int getNotifId() {
		return notifId;
	}
	public void setNotifId(int notifId) {
		this.notifId = notifId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String string) {
		this.read = read;
	}
	
}
