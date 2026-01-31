package com.revworkforce.service;

import java.sql.*;
import java.util.*;

import com.revworkforce.dao.PerformanceDao;
import com.revworkforce.model.Goal;
import com.revworkforce.model.PerformanceReview;

import com.revworkforce.db.DBConnection;

import org.apache.log4j.Logger;

public class PerformanceService implements PerformanceDao{
	
	private static final Logger logger =
            Logger.getLogger(PerformanceService.class);

	Connection connection = DBConnection.getConnection();
	
	public PerformanceService(Connection connection) {
        this.connection = connection;
    }

    public PerformanceService() {}
	
	public void submitReview(PerformanceReview performance) {
		
		logger.info("Submitting performance review for empId=" + performance.getEmpId());
		
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
                //System.out.println("Performance Review Submitted Successfully");
            	logger.info("Performance review submitted successfully for empId=" + performance.getEmpId());
            } else {
                System.out.println("Performance Review Submission Failed");
                logger.warn("Performance review submission failed for empId=" + performance.getEmpId());
            }

        } catch (Exception e) {
        	//System.out.println("Error while submitting performance review");
            //e.printStackTrace();
        	logger.error("Error while submitting performance review for empId=" + performance.getEmpId(), e);

        }
		
    }
	
	public List<PerformanceReview> getAllReviews() {
		
		logger.info("Fetching all performance reviews");
		
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
            
            logger.info("Total performance reviews fetched = " + list.size());

        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while fetching performance reviews", e);
        }
        return list;
        
	}
	
	public void updateFeedback(int reviewId, String feedback, int rating) {

		logger.info("Updating feedback for reviewId=" + reviewId);
		
        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE PERFORMANCE_REVIEW SET MANAGER_FEEDBACK=?, MANAGER_RATING=? WHERE REVIEW_ID=?");

            ps.setString(1, feedback);
            ps.setInt(2, rating);
            ps.setInt(3, reviewId);
            ps.executeUpdate();
            
            logger.info("Feedback updated successfully for reviewId=" + reviewId);
            
        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while updating feedback for reviewId=" + reviewId, e);
        }
    }
	
	public void addGoal(Goal goal) {
		
		logger.info("Adding goal for empId=" + goal.getEmpId());
		
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
                //System.out.println("Goal Added Successfully");
                logger.info("Goal added successfully for empId=" + goal.getEmpId());
            } else {
                //System.out.println("Failed to add goal");
            	logger.warn("Failed to add goal for empId=" + goal.getEmpId());
            }
            
        } catch (Exception e) {
        	//System.out.println("Error while adding goal");
            //e.printStackTrace();
        	 logger.error("Error while adding goal for empId=" + goal.getEmpId(), e);
        }

	}
	
	public List<Goal> getGoals(int empId) {
		
		logger.debug("Fetching goals for empId=" + empId);
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
            
            logger.info("Total goals fetched for empId=" + empId + " = " + list.size());

        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while fetching goals for empId=" + empId, e);
        }
        return list;
    }

	public List<Goal> getTeamGoals(int managerId) {

		logger.info("Fetching team goals for managerId=" + managerId);
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
            
            logger.info("Total team goals fetched for managerId=" + managerId + " = " + list.size());

        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while fetching team goals for managerId=" + managerId, e);
        }
        return list;
    }
	
	public void updateGoalProgress(int goalId, int progress) {
		
		 logger.info("Updating goal progress goalId=" + goalId + ", progress=" + progress + "%");

        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE GOAL SET PROGRESS=? WHERE GOAL_ID=?");

            ps.setInt(1, progress);
            ps.setInt(2, goalId);
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Goal Progress Updated Successfully");
                logger.info("Goal progress updated successfully goalId=" + goalId);
            } else {
                System.out.println("Invalid Goal ID");
                logger.warn("Invalid goalId while updating progress goalId=" + goalId);
            }

        } catch (Exception e) {
        	//System.out.println("Error while updating goal progress");
            //e.printStackTrace();
        	logger.error("Error while updating goal progress goalId=" + goalId, e);
        }
    }

	public void teamPerformanceReport(int managerId) {
		
		logger.info("Generating team performance report for managerId=" + managerId);
		
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

	            //System.out.println(
	            logger.debug(
	                "EmpId: " + rs.getInt("EMP_ID") +
	                " | Name: " + rs.getString("NAME") +
	                " | Goal: " + rs.getString("DESCRIPTION") +
	                " | Progress: " + rs.getInt("PROGRESS") + "%"
	            );
	        }

	        if (!hasData) {
	            //System.out.println("No performance data available for your team");
	        	logger.info("Team performance report generated for managerId=" + managerId);
	        }

	        //System.out.println("Team Performance Report Generated");
	        logger.info("Team performance report generated for managerId=" + managerId);
	    } catch (Exception e) {
	        //e.printStackTrace();
	    	logger.error("Error while generating team performance report for managerId=" + managerId, e);
	    }
		
	}
	
}