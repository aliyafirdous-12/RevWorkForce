package com.revworkforce.service;

import java.sql.*;

import com.revworkforce.dao.LeaveBalanceDao;
import com.revworkforce.model.LeaveBalance;

import com.revworkforce.db.DBConnection;

import org.apache.log4j.Logger;

public class LeaveBalanceService implements LeaveBalanceDao{
	
	private static final Logger logger =
            Logger.getLogger(LeaveBalanceService.class);

	Connection connection = DBConnection.getConnection();
	
	public LeaveBalanceService(Connection connection) {
        this.connection = connection;
    }
	
	public LeaveBalanceService() {}
	
	public boolean createBalance(int empId) {
		
		logger.info("Creating leave balance for empId=" + empId);

		try {
	        PreparedStatement ps = connection.prepareStatement(
	            "INSERT INTO LEAVE_BALANCE (EMP_ID, CL, SL, PL, PRIVILEGE) VALUES (?,10,10,10,10)");

	        ps.setInt(1, empId);

	        //return ps.executeUpdate() > 0;   
	        boolean created = ps.executeUpdate() > 0;

            if (created) {
                logger.info("Leave balance created successfully for empId=" + empId);
            } else {
                logger.warn("Leave balance creation failed for empId=" + empId);
            }

            return created;

	    } catch (Exception e) {
	        //e.printStackTrace();
	    	logger.error("Error while creating leave balance for empId=" + empId, e);
        }
	    return false;
		
	}
	
	public void updateBalance(int empId, int cl, int sl, int pl, int privilege) {
	
		logger.info("Updating leave balance for empId=" + empId);
		
		try {
	        PreparedStatement ps = connection.prepareStatement(
	            "UPDATE LEAVE_BALANCE SET CL=?, SL=?, PL=?, PRIVILEGE=? WHERE EMP_ID=?");

	        ps.setInt(1, cl);
	        ps.setInt(2, sl);
	        ps.setInt(3, pl);
	        ps.setInt(4, privilege);
	        ps.setInt(5, empId);

	        ps.executeUpdate();
	        
	        logger.info("Leave balance updated for empId=" + empId);

	    } catch (Exception e) {
	        //e.printStackTrace();
	    	logger.error("Error while updating leave balance for empId=" + empId, e);
	    }
		
	}

	public LeaveBalance getBalance(int empId) {
		
		logger.debug("Fetching leave balance for empId=" + empId);
		
		LeaveBalance lb = null;

	    try {
	        PreparedStatement ps = connection.prepareStatement(
	            "SELECT * FROM LEAVE_BALANCE WHERE EMP_ID=?");

	        ps.setInt(1, empId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            lb = new LeaveBalance();
	            lb.setEmpId(rs.getInt("EMP_ID"));
	            lb.setCl(rs.getInt("CL"));
	            lb.setSl(rs.getInt("SL"));
	            lb.setPl(rs.getInt("PL"));
	            lb.setPrivilege(rs.getInt("PRIVILEGE"));
	        
	            logger.info("Leave balance fetched for empId=" + empId);
            } else {
                logger.warn("No leave balance found for empId=" + empId);
            }

	    } catch (Exception e) {
	        //e.printStackTrace();
	    	logger.error("Error while fetching leave balance for empId=" + empId, e);
	    }
	    return lb;
	    
	}

}