package com.revworkforce.service;

import java.sql.*;
import java.util.*;

import com.revworkforce.dao.PerformanceDao;
import com.revworkforce.model.Goal;
import com.revworkforce.model.PerformanceReview;

import com.revworkforce.db.DBConnection;

public class PerformanceService implements PerformanceDao{

	Connection connection = DBConnection.getConnection();
	
	public void submitReview(PerformanceReview performance) {
		
		try {
			 PreparedStatement ps = connection.prepareStatement(
			            "INSERT INTO PERFORMANCE_REVIEW " +
			            "(REVIEW_ID, EMP_ID, DELIVERABLES, ACHIEVEMENTS, IMPROVEMENTS, SELF_RATING, MANAGER_FEEDBACK, MANAGER_RATING) " +
			            "VALUES (PERF_SEQ.NEXTVAL, ?, ?, ?, ?, ?, NULL, 0)"
			 );

            ps.setInt(1, performance.getEmpId());
            ps.setString(2, performance.getDeliverables());
            ps.setString(3, performance.getAchievements());
            ps.setString(4, performance.getImprovements());
            ps.setInt(5, performance.getSelfRating());
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Performance Review Submitted Successfully");
            } else {
                System.out.println("Performance Review Submission Failed");
            }

        } catch (Exception e) {
        	System.out.println("Error while submitting performance review");
            e.printStackTrace();
        }
		
    }
	
	public List<PerformanceReview> getAllReviews() {
		
		List<PerformanceReview> list = new ArrayList<PerformanceReview>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PERFORMANCE_REVIEW");

            while (rs.next()) {

                PerformanceReview p = new PerformanceReview();
                p.setReviewId(rs.getInt("REVIEW_ID"));
                p.setEmpId(rs.getInt("EMP_ID"));
                p.setDeliverables(rs.getString("DELIVERABLES"));
                p.setAchievements(rs.getString("ACHIEVEMENTS"));
                p.setImprovements(rs.getString("IMPROVEMENTS"));
                p.setSelfRating(rs.getInt("SELF_RATING"));
                p.setManagerFeedback(rs.getString("MANAGER_FEEDBACK"));
                p.setManagerRating(rs.getInt("MANAGER_RATING"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        
	}
	
	public void updateFeedback(int reviewId, String feedback, int rating) {

        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE PERFORMANCE_REVIEW SET MANAGER_FEEDBACK=?, MANAGER_RATING=? WHERE REVIEW_ID=?");

            ps.setString(1, feedback);
            ps.setInt(2, rating);
            ps.setInt(3, reviewId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void addGoal(Goal goal) {
		
		try {
			PreparedStatement ps = connection.prepareStatement(
		            "INSERT INTO GOAL " +
		            "(GOAL_ID, EMP_ID, DESCRIPTION, DEADLINE, PRIORITY, PROGRESS, SUCCESS_METRICS) " +
		            "VALUES (GOAL_SEQ.NEXTVAL, ?, ?, ?, ?, 0, ?)"
		    );
			
            ps.setInt(1, goal.getEmpId());
            ps.setString(2, goal.getDescription());
            ps.setDate(3, goal.getDeadline());
            ps.setString(4, goal.getPriority());
            ps.setString(5, goal.getSuccessMetrics());
            
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Goal Added Successfully");
            } else {
                System.out.println("Failed to add goal");
            }
            
        } catch (Exception e) {
        	System.out.println("Error while adding goal");
            e.printStackTrace();
        }

	}
	
	public List<Goal> getGoals(int empId) {

        List<Goal> list = new ArrayList<Goal>();

        try {
            PreparedStatement ps = connection.prepareStatement(
            		"SELECT * FROM GOAL WHERE EMP_ID=? ORDER BY DEADLINE");
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Goal goal = new Goal();
                
                goal.setGoalId(rs.getInt("GOAL_ID"));
                goal.setEmpId(rs.getInt("EMP_ID"));
                goal.setDescription(rs.getString("DESCRIPTION"));
                goal.setDeadline(rs.getDate("DEADLINE"));
                goal.setPriority(rs.getString("PRIORITY"));
                goal.setProgress(rs.getInt("PROGRESS"));
                goal.setSuccessMetrics(rs.getString("SUCCESS_METRICS"));
                list.add(goal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

	public List<Goal> getTeamGoals(int managerId) {

        List<Goal> list = new ArrayList<Goal>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT G.* FROM GOAL G JOIN EMPLOYEE E ON G.EMP_ID=E.EMP_ID WHERE E.MANAGER_ID=?");

            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Goal goal = new Goal();
                goal.setGoalId(rs.getInt("GOAL_ID"));
                goal.setDescription(rs.getString("DESCRIPTION"));
                goal.setProgress(rs.getInt("PROGRESS"));
                list.add(goal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public void updateGoalProgress(int goalId, int progress) {

        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE GOAL SET PROGRESS=? WHERE GOAL_ID=?");

            ps.setInt(1, progress);
            ps.setInt(2, goalId);
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Goal Progress Updated Successfully");
            } else {
                System.out.println("Invalid Goal ID");
            }

        } catch (Exception e) {
        	System.out.println("Error while updating goal progress");
            e.printStackTrace();
        }
    }

	public void teamPerformanceReport(int managerId) {
		
		System.out.println("TEAM PERFORMANCE REPORT");

	    try {
	        PreparedStatement ps = connection.prepareStatement(
	            "SELECT E.EMP_ID, E.NAME, G.DESCRIPTION, G.PROGRESS " +
	            "FROM EMPLOYEE E " +
	            "JOIN GOAL G ON E.EMP_ID = G.EMP_ID " +
	            "WHERE E.MANAGER_ID = ?");

	        ps.setInt(1, managerId);
	        ResultSet rs = ps.executeQuery();

	        boolean hasData = false;

	        while (rs.next()) {
	            hasData = true;

	            System.out.println(
	                "EmpId: " + rs.getInt("EMP_ID") +
	                " | Name: " + rs.getString("NAME") +
	                " | Goal: " + rs.getString("DESCRIPTION") +
	                " | Progress: " + rs.getInt("PROGRESS") + "%"
	            );
	        }

	        if (!hasData) {
	            System.out.println("No performance data available for your team");
	        }

	        System.out.println("Team Performance Report Generated");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}
	
}