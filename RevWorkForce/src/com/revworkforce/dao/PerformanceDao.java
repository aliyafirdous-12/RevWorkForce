package com.revworkforce.dao;

import java.util.List;

import com.revworkforce.model.Goal;

import com.revworkforce.model.PerformanceReview;

public interface PerformanceDao {

	void submitReview(PerformanceReview performance);

	List<PerformanceReview> getAllReviews();

	void updateFeedback(int rid, String fb, int rating);
	
	void addGoal(Goal goal);
	
	List<Goal> getGoals(int empId);

	List<Goal> getTeamGoals(int managerId);
	
	void updateGoalProgress(int goalId, int progress);

	void teamPerformanceReport(int managerId);

}