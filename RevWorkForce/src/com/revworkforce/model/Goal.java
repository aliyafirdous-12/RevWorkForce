package com.revworkforce.model;

import java.sql.Date;

public class Goal {
	
	private int goalId;
    private int empId;
    private String description;
    private Date deadline;
    private String priority;
    private String status;
    private String successMetrics;
    private int progress;
    
    public Goal() {}
    
	public Goal(int goalId, int empId, String description, Date deadline,
			String priority, String status, String successMetrics, int progress) {
		
		this.goalId = goalId;
		this.empId = empId;
		this.description = description;
		this.deadline = deadline;
		this.priority = priority;
		this.status = status;
		this.successMetrics = successMetrics;
		this.progress = progress;
		
	}
	public int getGoalId() {
		return goalId;
	}
	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSuccessMetrics() {
		return successMetrics;
	}
	public void setSuccessMetrics(String successMetrics) {
		this.successMetrics = successMetrics;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}

}
