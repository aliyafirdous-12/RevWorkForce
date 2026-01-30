package com.revworkforce.model;

public class PerformanceReview {
	
	private int reviewId;
    private int empId;
    private String deliverables;
    private String achievements;
    private String improvements;
    private int selfRating;
    private String managerFeedback;
    private Integer managerRating;
    
    public PerformanceReview() {}
    
	public PerformanceReview(int reviewId, int empId, String deliverables,
			String achievements, String improvements, int selfRating,
			String managerFeedback, Integer managerRating) {
		
		this.reviewId = reviewId;
		this.empId = empId;
		this.deliverables = deliverables;
		this.achievements = achievements;
		this.improvements = improvements;
		this.selfRating = selfRating;
		this.managerFeedback = managerFeedback;
		this.managerRating = managerRating;
		
	}
	
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDeliverables() {
		return deliverables;
	}
	public void setDeliverables(String deliverables) {
		this.deliverables = deliverables;
	}
	public String getAchievements() {
		return achievements;
	}
	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}
	public String getImprovements() {
		return improvements;
	}
	public void setImprovements(String improvements) {
		this.improvements = improvements;
	}
	public int getSelfRating() {
		return selfRating;
	}
	public void setSelfRating(int selfRating) {
		this.selfRating = selfRating;
	}
	public String getManagerFeedback() {
		return managerFeedback;
	}
	public void setManagerFeedback(String managerFeedback) {
		this.managerFeedback = managerFeedback;
	}
	public Integer getManagerRating() {
		return managerRating;
	}
	public void setManagerRating(Integer managerRating) {
		this.managerRating = managerRating;
	}
	
}