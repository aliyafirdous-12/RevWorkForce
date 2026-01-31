package com.revworkforce.service;

import java.sql.*;
import java.util.*;

import com.revworkforce.dao.EmployeeDao;
import com.revworkforce.model.Employee;

import com.revworkforce.db.DBConnection;

import org.apache.log4j.Logger;

public class EmployeeService implements EmployeeDao {
	
	private static final Logger logger =
            Logger.getLogger(EmployeeService.class);
	
	Connection connection = DBConnection.getConnection();

    public EmployeeService(Connection connection) {
		this.connection = connection;
	}

	public EmployeeService() {}

	//1. Add an Employee
    public boolean addEmployee(Employee e) {
    	
    	logger.info("Add Employee started for email=" + e.getEmail());

        try {
        	PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO EMPLOYEE " +
                    "(NAME, EMAIL, PASSWORD, PHONE, ADDRESS, EMERGENCY_CONTACT, DOB, ROLE, MANAGER_ID, STATUS, SALARY, DESIGNATION, DEPT_ID) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"
                );

            ps.setString(1, e.getName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPassword());
            ps.setString(4, e.getPhone());
            ps.setString(5, e.getAddress());
            ps.setString(6, e.getEmergencyContact());
            ps.setDate(7, e.getDob());
            ps.setString(8, e.getRole());
            if (e.getManagerId() == null)
                ps.setNull(9, Types.INTEGER);
            else
            ps.setInt(9, e.getManagerId());
            ps.setString(10, e.getStatus());
            ps.setDouble(11, e.getSalary());
            ps.setString(12, e.getDesignation());
            ps.setObject(13, e.getDeptId());

            ps.executeUpdate();
            
            logger.info("Employee added successfully: " + e.getEmail());
            return true;

        } catch (Exception ex) {
        	//ex.printStackTrace();
        	logger.error("Error while adding employee: " + e.getEmail(), ex);
        }
        return false;
    }

    //2. Update Employee
    public void updateEmployee(Employee e) {

    	logger.info("Updating employee empId=" + e.getEmpId());
    	
        try {
        	PreparedStatement ps = connection.prepareStatement(
        			"UPDATE EMPLOYEE SET NAME=?, PHONE=?, ADDRESS=?, EMERGENCY_CONTACT=?, SALARY=?, DESIGNATION=?, DEPT_ID=? WHERE EMP_ID=?");

            ps.setString(1, e.getName());
            ps.setString(2, e.getPhone());
            ps.setString(3, e.getAddress());
            ps.setString(4, e.getEmergencyContact());
            ps.setDouble(5, e.getSalary());
            ps.setString(6, e.getDesignation());
            ps.setObject(7, e.getDeptId());
//            ps.setString(8, e.getStatus());
            ps.setInt(8, e.getEmpId());

            ps.executeUpdate();
            logger.info("Employee updated successfully empId=" + e.getEmpId());

        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error while updating employee empId=" + e.getEmpId(), ex);
        }
      
    }

    //3. Get Employee by id
    public Employee getEmployeeById(int empId) {

    	logger.debug("Fetching employee by id=" + empId);
    	
        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM EMPLOYEE WHERE EMP_ID=?");

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	return map(rs);
            }

        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error while fetching employee empId=" + empId, ex);
        }
        return null;
    }

    //4. View all Employees
    public List<Employee> getAllEmployees() {

    	logger.info("Fetching all employees");
    	
        List<Employee> list = new ArrayList<Employee>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM EMPLOYEE");

            while (rs.next()) {

            	Employee e = map(rs);
                list.add(e);

            }

        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error while fetching employees", ex);
        }
        return list;
    }

    //5. Update Employee Status
	public void updateStatus(int empId, String status) {
		
		logger.info("Updating status for empId=" + empId + ", status=" + status);
		
		try {
			
            PreparedStatement ps = connection.prepareStatement(
              "UPDATE EMPLOYEE SET STATUS=? WHERE EMP_ID=?");
            ps.setString(1, status);
            ps.setInt(2, empId);
            ps.executeUpdate();
            
        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error updating status for empId=" + empId, ex);
        }
		
	}

	//6. Search Employee
	public List<Employee> searchEmployee(String key) {
		
		logger.debug("Searching employee with key=" + key);
		
        List<Employee> list = new ArrayList<Employee>();
        try {
            PreparedStatement ps = connection.prepareStatement(
              "SELECT * FROM EMPLOYEE WHERE NAME LIKE ? OR DESIGNATION LIKE ?");
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	list.add(map(rs));
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error searching employee with key=" + key, ex);
        }
        return list;
    }

	//7. Reset / Forgot Password
	public void resetPassword(int empId, String pwd) {
		
		logger.warn("Resetting password for empId=" + empId);

		try {
			
            PreparedStatement ps = connection.prepareStatement(
              "UPDATE EMPLOYEE SET PASSWORD=? WHERE EMP_ID=?");
            ps.setString(1, pwd);
            ps.setInt(2, empId);
            ps.executeUpdate();
            
        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error resetting password for empId=" + empId, ex);
        }
		
	}

	//8. Update / change Manager
    public void updateManager(int empId, int managerId) {
    	
    	logger.info("Updating manager for empId=" + empId);
    	
        try {
        	
            PreparedStatement ps = connection.prepareStatement(
              "UPDATE EMPLOYEE SET MANAGER_ID=? WHERE EMP_ID=?");
            
            ps.setInt(1, managerId);
            ps.setInt(2, empId);
            ps.executeUpdate();
            
        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error updating manager for empId=" + empId, ex);
        }
        
    }

    //9. View Team Members
    public List<Employee> getTeamMembers(int managerId) {
    	
    	logger.info("Fetching team members for managerId=" + managerId);
    	
        List<Employee> list = new ArrayList<Employee>();
        try {
            PreparedStatement ps = connection.prepareStatement(
              "SELECT * FROM EMPLOYEE WHERE MANAGER_ID=?");
            
            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error fetching team members for managerId=" + managerId, ex);
        }
        return list;
    }

    //10. View Manager Details
    public Employee getManagerDetails(int empId) {
    	
    	logger.debug("Fetching manager details for empId=" + empId);
    	
        try {
            PreparedStatement ps = connection.prepareStatement(
              "SELECT M.* FROM EMPLOYEE E JOIN EMPLOYEE M ON E.MANAGER_ID = M.EMP_ID WHERE E.EMP_ID=?");

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error fetching manager details for empId=" + empId, ex);
        }
        return null;
    }

    //10. Change Password
    public void changePassword(int empId, String oldPwd, String newPwd) {

    	logger.warn("Change password request for empId=" + empId);
//        // -------- BASIC VALIDATION --------
//        if (newPwd.length() < 8) {
//            System.out.println("Password must be at least 8 characters");
//            return;
//        }

        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE EMPLOYEE SET PASSWORD=? WHERE EMP_ID=? AND PASSWORD=?");

            ps.setString(1, newPwd);
            ps.setInt(2, empId);
            ps.setString(3, oldPwd);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                //System.out.println("Password Changed Successfully");
            	logger.info("Password changed successfully for empId=" + empId);
            } else {
                //System.out.println("Old password is incorrect");
            	logger.warn("Password change failed (old password mismatch) for empId=" + empId);
            }

        } catch (Exception e) {
            //System.out.println("Error while changing password");
            //e.printStackTrace();
            logger.error("Error while changing password for empId=" + empId, e);
        }
        
    }


    //12. Update Profile
    public void updateProfile(int empId, String phone, String address, String emergency) {
    	
    	logger.info("Updating profile for empId=" + empId);
    	
        try {
            PreparedStatement ps = connection.prepareStatement(
              "UPDATE EMPLOYEE SET PHONE=?, ADDRESS=?, EMERGENCY_CONTACT=? WHERE EMP_ID=?");

            ps.setString(1, phone);
            ps.setString(2, address);
            ps.setString(3, emergency);
            ps.setInt(4, empId);
            ps.executeUpdate();

        } catch (Exception ex) {
            //ex.printStackTrace();
        	logger.error("Error updating profile for empId=" + empId, ex);
        }
        
    }
	
    private Employee map(ResultSet rs) throws Exception {

        Employee e = new Employee();
        e.setEmpId(rs.getInt("EMP_ID"));
        e.setName(rs.getString("NAME"));
        e.setEmail(rs.getString("EMAIL"));
        e.setPhone(rs.getString("PHONE"));
        e.setAddress(rs.getString("ADDRESS"));
        e.setEmergencyContact(rs.getString("EMERGENCY_CONTACT"));
        e.setDob(rs.getDate("DOB"));
        e.setRole(rs.getString("ROLE"));
        //e.setManagerId((Integer) rs.getObject("MANAGER_ID"));
        int mgrId = rs.getInt("MANAGER_ID");
        if (!rs.wasNull()) {
            e.setManagerId(mgrId);
        }
        e.setStatus(rs.getString("STATUS"));
        e.setSalary(rs.getDouble("SALARY"));
        e.setDesignation(rs.getString("DESIGNATION"));
        //e.setDeptId(rs.getInt("DEPT_ID"));
        int deptId = rs.getInt("DEPT_ID");
        if (!rs.wasNull()) {
            e.setDeptId(deptId);
        }
        return e;
    }

}