package com.revworkforce.service;

import java.sql.*;

import com.revworkforce.dao.LeaveBalanceDao;
import com.revworkforce.model.LeaveBalance;

import com.revworkforce.db.DBConnection;

public class LeaveBalanceService implements LeaveBalanceDao{

	Connection connection = DBConnection.getConnection();
	
	public boolean createBalance(int empId) {

		try {
	        PreparedStatement ps = connection.prepareStatement(
	            "INSERT INTO LEAVE_BALANCE (EMP_ID, CL, SL, PL, PRIVILEGE) VALUES (?,10,10,10,10)");

	        ps.setInt(1, empId);

	        return ps.executeUpdate() > 0;   

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
		
	}
	
	public void updateBalance(int empId, int cl, int sl, int pl, int privilege) {
	
		try {
	        PreparedStatement ps = connection.prepareStatement(
	            "UPDATE LEAVE_BALANCE SET CL=?, SL=?, PL=?, PRIVILEGE=? WHERE EMP_ID=?");

	        ps.setInt(1, cl);
	        ps.setInt(2, sl);
	        ps.setInt(3, pl);
	        ps.setInt(4, privilege);
	        ps.setInt(5, empId);

	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

	public LeaveBalance getBalance(int empId) {
		
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
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lb;
	    
	}

}