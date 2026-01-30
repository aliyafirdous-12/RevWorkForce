package com.revworkforce.main;

import java.util.*;
import java.sql.Date;

import com.revworkforce.dao.EmployeeDao;
import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;

import com.revworkforce.dao.LeaveBalanceDao;
import com.revworkforce.dao.LeaveRequestDao;
import com.revworkforce.service.LeaveBalanceService;
import com.revworkforce.service.LeaveRequestService;

import com.revworkforce.dao.HolidayDao;
import com.revworkforce.model.Holiday;
import com.revworkforce.service.HolidayService;

import com.revworkforce.util.ValidationUtil;
import com.revworkforce.util.SessionManager;

public class AdminMenu {

    Scanner sc = new Scanner(System.in);
    private EmployeeDao empDao = new EmployeeService();
    private LeaveBalanceDao balanceDao = new LeaveBalanceService();
    private HolidayDao holidayDao = new HolidayService();
    private LeaveRequestDao leaveRequestDao = new LeaveRequestService();

    public void show() {
    	
    	// Session check
        if (SessionManager.isSessionExpired()) {
            System.out.println("Session expired. Please login again.");
            new LoginApp().login();
            return;
        }
        SessionManager.refresh();

        while (true) {
        	System.out.println("Login Successful (ADMIN) ");
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Activate / Deactivate Employee");
            System.out.println("5. Search Employee");
            System.out.println("6. Assign Leave Balance");
            System.out.println("7. Add Holiday");
            System.out.println("8. Reset Employee Password");
            System.out.println("9. View Leave Report");
            System.out.println("10. Assign Manager");
            System.out.println("11. Logout");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
            case 1:
                System.out.println("Add Employee");
                addEmployee();
                break;

            case 2:
                System.out.println("View Employee");
                viewEmployees();
                break;
            case 3:
            	System.out.println("Update Employee");
                updateEmployee();
                break;

            case 4:
            	System.out.println("Change Status");
                changeStatus();
                break;

            case 5:
            	System.out.println("Search Employee");
                searchEmployee();
                break;

            case 6:
            	System.out.println("Assign Leave Balance");
                assignLeaveBalance();
                break;

            case 7:
            	System.out.println("Add Holiday");
                addHoliday();
                break;

            case 8:
            	System.out.println("Reset Password");
                resetPassword();
                break;


            case 9:
            	System.out.println("Leave Report");
                leaveRequestDao.getLeaveReport();
                break;
            case 10:
            	System.out.println("Assign Manager");
                System.out.print("Enter Employee ID: ");
                int empId = sc.nextInt();

                System.out.print("Enter Manager ID: ");
                int managerId = sc.nextInt();

                empDao.updateManager(empId, managerId);
                System.out.println("Manager Assigned Successfully");
                break;

            case 11:
                System.out.println("Logged out successfully");
                new LoginApp().login();
                return;

            default:
                System.out.println("Invalid choice");
            }  
        }
        
    }	

    //1. Add Employee
	private void addEmployee() {
		
		Employee emp = new Employee();

        System.out.print("Enter ID: ");
        emp.setEmpId(sc.nextInt());

        System.out.print("Enter Name: ");
        emp.setName(sc.next());

        System.out.print("Enter Email: ");
        emp.setEmail(sc.next());

        System.out.print("Enter Password: ");
        emp.setPassword(sc.next());

        System.out.print("Enter Phone: ");
        emp.setPhone(sc.next());

        System.out.print("Enter Address: ");
        emp.setAddress(sc.next());
        
        System.out.print("Enter Emergency Contact: ");
        emp.setEmergencyContact(sc.next());
        
        System.out.print("DOB (yyyy-mm-dd): ");
        emp.setDob(Date.valueOf(sc.next()));
        
        System.out.print("Enter Role (EMPLOYEE/MANAGER): ");
        emp.setRole(sc.next());

        System.out.print("Enter Manager ID (or 0): ");
        int mid = sc.nextInt();
        emp.setManagerId(mid == 0 ? null : mid);

        emp.setStatus("ACTIVE");
        
        System.out.print("Salary: ");
        emp.setSalary(sc.nextDouble());
        sc.nextLine();

        System.out.print("Designation: ");
//        sc.nextLine();
        emp.setDesignation(sc.nextLine());

        System.out.print("Enter Department ID: ");
        emp.setDeptId(sc.nextInt());

        if (empDao.addEmployee(emp)) {
	        balanceDao.createBalance(emp.getEmpId());
	        System.out.println("Employee Added Successfully");
        }
    }
	
	//2. View Employee
	private void viewEmployees() {
		
		for (Employee e : empDao.getAllEmployees()) {
            System.out.println(
                e.getEmpId() + " | " +
                e.getName() + " | " +
                e.getRole() + " | " +
                e.getStatus());
        }
		
	}

	//3. Update Employee
    private void updateEmployee() {

        Employee e = new Employee();

        System.out.print("Emp ID: ");
        e.setEmpId(sc.nextInt());
        sc.nextLine();
        
        System.out.print("Name: ");
//        sc.nextLine();
        e.setName(sc.nextLine());

        System.out.print("Phone: ");
        e.setPhone(sc.next());

        System.out.print("Address: ");
        e.setAddress(sc.next());

        System.out.print("Emergency Contact: ");
        e.setEmergencyContact(sc.next());
        
        e.setStatus("ACTIVE");

        System.out.print("Salary: ");
        e.setSalary(sc.nextDouble());
        sc.nextLine();
        
        System.out.print("Designation: ");
//        sc.nextLine();
        e.setDesignation(sc.nextLine());

//        System.out.print("Dept ID: ");
//        e.setDeptId(sc.nextInt());

        empDao.updateEmployee(e);
        System.out.println("Employee Updated Successfully");
    }

    //4. Change Status
    private void changeStatus() {

        System.out.print("Emp ID: ");
        int id = sc.nextInt();

        System.out.print("ACTIVE / INACTIVE: ");
        String status = sc.next();

        empDao.updateStatus(id, status);
        
    }

    //5. Search Employee
    private void searchEmployee() {
    	
    	sc.nextLine();
        System.out.print("Enter name / designation: ");
        String key = sc.nextLine();

        for (Employee e : empDao.searchEmployee(key)) {
            System.out.println(
                e.getEmpId() + " | " +
                e.getName() + " | " +
                e.getDesignation() + " | " +
                e.getStatus());
        }
        
    }

    //6. Assign Leave Balance
    private void assignLeaveBalance() {

        System.out.print("Employee ID: ");
        int empId = sc.nextInt();

        System.out.print("CL: ");
        int cl = sc.nextInt();

        System.out.print("SL: ");
        int sl = sc.nextInt();

        System.out.print("PL: ");
        int pl = sc.nextInt();

        System.out.print("PRIVILEGE: ");
        int priv = sc.nextInt();

        balanceDao.updateBalance(empId, cl, sl, pl, priv);
        System.out.println("Leave Balance Updated ✅");
    }

    //7.Add Holiday
    private void addHoliday() {

        Holiday h = new Holiday();

        System.out.print("Holiday Name: ");
        h.setHolidayName(sc.next());

        System.out.print("Holiday Date (yyyy-mm-dd): ");
        h.setHolidayDate(Date.valueOf(sc.next()));

        holidayDao.addHoliday(h);
        System.out.println("Holiday Added ✅");
    }

    //8.Reset Password
    private void resetPassword() {

        System.out.print("Employee ID: ");
        int id = sc.nextInt();

        System.out.print("New Password: ");
        String pwd = sc.next();

        empDao.resetPassword(id, pwd);
        System.out.println("Password Reset Successfully ✅");
    }
 
}