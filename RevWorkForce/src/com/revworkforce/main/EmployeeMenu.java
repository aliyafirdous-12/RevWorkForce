package com.revworkforce.main;

import java.util.*;
import java.sql.Date;   

import com.revworkforce.model.Employee;
import com.revworkforce.model.Goal;
import com.revworkforce.model.Holiday;
import com.revworkforce.model.LeaveBalance;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.model.Notification;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.service.EmployeeService;
import com.revworkforce.dao.EmployeeDao;

import com.revworkforce.service.LeaveBalanceService;
import com.revworkforce.dao.LeaveBalanceDao;

import com.revworkforce.service.LeaveRequestService;
import com.revworkforce.dao.LeaveRequestDao;

import com.revworkforce.service.HolidayService;
import com.revworkforce.dao.HolidayDao;

import com.revworkforce.service.NotificationService;
import com.revworkforce.dao.NotificationDao;

import com.revworkforce.service.PerformanceService;
import com.revworkforce.dao.PerformanceDao;
import com.revworkforce.util.SessionManager;
import com.revworkforce.util.ValidationUtil;

public class EmployeeMenu {

    int empId;
    Scanner sc = new Scanner(System.in);
    EmployeeDao empDao = new EmployeeService();
    LeaveRequestDao leaveRequestDao = new LeaveRequestService();
    LeaveBalanceDao balanceDao = new LeaveBalanceService();
    HolidayDao holidayDao = new HolidayService();
    PerformanceDao perfDao = new PerformanceService();
    NotificationDao notifDao = new NotificationService();


    public EmployeeMenu(int empId) {
        this.empId = empId;
    }

    public void show() {
    	
    	// Session check
        if (SessionManager.isSessionExpired()) {
            System.out.println("Session expired. Please login again.");
            new LoginApp().login();
            return;
        }
        SessionManager.refresh();

        while (true) {
        	System.out.println("Login Successful (EMPLOYEE) ");
            System.out.println("\n===== EMPLOYEE MENU =====");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Manager Details");
            System.out.println("4. Apply Leave");
            System.out.println("5. View Leave Status");
            System.out.println("6. Cancel Leave");
            System.out.println("7. Leave Balance");
            System.out.println("8. View Holidays");
            System.out.println("9. View Notifications");
            System.out.println("10. Submit Review");
            System.out.println("11. Add Goal");
            System.out.println("12. View Goals");
            System.out.println("13. Update Goal");
            System.out.println("14. Change Password");
            System.out.println("15. Logout");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
            case 1:
                System.out.println("View Profile");
                viewProfile();
                break;
            case 2: 
            	System.out.println("Update Profile");
            	System.out.print("Phone: ");
                String phone = sc.next();

                sc.nextLine();
                System.out.print("Address: ");
                String address = sc.next();

                System.out.print("Emergency Contact: ");
                String emergency = sc.next();
            	empDao.updateProfile(empId, phone, address, emergency);
            	break;
            case 3: 
            	System.out.println("Manager Details");
            	viewManager(); 
            	break;
            case 4:
            	System.out.println("Apply Leave");
            	applyLeave();
            	break;
            case 5:
                System.out.println("View Leave Status");
                viewLeaves(); 
                break;
            case 6:
                System.out.println("Cancel Leave");
                System.out.print("Enter Leave ID: ");
                int leaveId = sc.nextInt();

                if (leaveRequestDao.cancelLeave(leaveId)) {
                    System.out.println("Leave Cancelled Successfully");
                } else {
                    System.out.println("Cannot cancel leave or (Already approved/rejected or invalid ID)");
                }
                break;

            case 7:
            	System.out.println("Leave Balance");
            	LeaveBalance lb = balanceDao.getBalance(empId);

            	if (lb != null) {
            	    System.out.println("CL: " + lb.getCl());
            	    System.out.println("SL: " + lb.getSl());
            	    System.out.println("PL: " + lb.getPl());
            	    System.out.println("PRIVILEGE: " + lb.getPrivilege());
            	} else {
            	    System.out.println("Leave balance not found");
            	}
            	break;
            case 8: 
            	System.out.println("View Holidays");
            	viewHolidays(); 
            	break;
            case 9: 
            	System.out.println("View Notifications");
            	viewNotifications(); 
            	break;
            case 10:
            	System.out.println("Submit Review");
            	submitReview(); 
            	break;
            case 11:
            	System.out.println("Add Goal");
            	addGoal(); 
            	break;
            case 12:
            	System.out.println("View Goals");
            	viewGoals(); 
            	break;
            case 13:
            	System.out.println("Update Goal");

                System.out.print("Enter Goal ID: ");
                int goalId = sc.nextInt();

                System.out.print("Enter Progress (0–100): ");
                int progress = sc.nextInt();
                
            	perfDao.updateGoalProgress(goalId, progress);
            	break;
            case 14:
                System.out.println("Change Password");

                System.out.print("Enter Old Password: ");
                String oldPwd = sc.next();

                //System.out.print("Enter New Password: ");
                //String newPwd = sc.next();
                //empDao.changePassword(empId, oldPwd, newPwd);
                while (true) {
                    try {
                        System.out.print("Enter New Password: ");
                        String newPwd = sc.next();

                        ValidationUtil.validatePassword(newPwd);

                        empDao.changePassword(empId, oldPwd, newPwd);
                        System.out.println("Password Changed Successfully ✅");
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }        
                break;
            case 15:
                System.out.println("Logout successful");
                new LoginApp().login();
                return;
            default:
                System.out.println("Invalid choice");
            }
            
        }
        
    }

    private void viewProfile() {
        Employee e = empDao.getEmployeeById(empId);
        
        if(e == null) {
        	System.out.println("Employee details not found");
        	return;
        }
        System.out.println(e.getName()+" | "+e.getEmail()+" | "+e.getPhone());      
    }

    private void viewManager() {
        Employee m = empDao.getManagerDetails(empId);
        System.out.println(m.getName()+" | "+m.getPhone());
    }

    private void applyLeave() {
        LeaveRequest l = new LeaveRequest();
        l.setEmpId(empId);
        //l.setFromDate(Date.valueOf(sc.next()));
        //l.setToDate(Date.valueOf(sc.next()));
        while (true) {
            try {
                System.out.print("From Date (yyyy-MM-dd): ");
                String from = sc.next();

                l.setFromDate(java.sql.Date.valueOf(from));
                break;
               
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format and Use yyyy-MM-dd");
            }
        }
        
        while (true) {
            try {
                System.out.print("To Date (yyyy-MM-dd): ");
                String to = sc.next();

                l.setToDate(java.sql.Date.valueOf(to));
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format and Use yyyy-MM-dd");
            }
        }
        //l.setLeaveType(sc.next());
        //l.setReason(sc.next());
        System.out.print("Leave Type (CL / SL / PL): ");
        l.setLeaveType(sc.next());

        sc.nextLine();
        System.out.print("Reason: ");
        l.setReason(sc.nextLine());

        leaveRequestDao.applyLeave(l);

        System.out.println("Leave Applied Successfully");

    }

    private void viewLeaves() {
    	List<LeaveRequest> leaves = leaveRequestDao.getLeavesByEmp(empId);

        if (leaves.isEmpty()) {
            System.out.println("No leave applications found");
            return;
        }

        System.out.println("----- My Leave Applications -----");

        for (LeaveRequest l : leaves) {
            System.out.println(
                "Leave ID : " + l.getLeaveId() +
                " | From : " + l.getFromDate() +
                " | To : " + l.getToDate() +
                " | Type : " + l.getLeaveType() +
                " | Status : " + l.getStatus() +
                " | Manager Comment : " +
                (l.getManagerComment() == null ? "-" : l.getManagerComment())
            );
        }
          
    }

    private void viewHolidays() {
        for (Holiday holiday : holidayDao.getHolidays())
            System.out.println(holiday.getHolidayName()+" | "+holiday.getHolidayDate());
    }

    private void viewNotifications() {

        List<Notification> notifications = notifDao.getNotifications(empId);

        if (notifications.isEmpty()) {
            System.out.println("No notifications available");
            return;
        }

        System.out.println("----- Notifications -----");

        for (Notification n : notifications) {
            System.out.println(
                "[" + n.getCreatedDate() + "] " +
                n.getMessage() +
                (n.getRead().equals("N") ? " (NEW)" : "")
            );
        }

        notifDao.markAsRead(empId);
    }

    private void submitReview() {
    	
        PerformanceReview performance = new PerformanceReview();
        performance.setEmpId(empId);
        
        System.out.print("Enter Deliverables: ");
        performance.setDeliverables(sc.next());

        System.out.print("Enter Achievements: ");
        performance.setAchievements(sc.next());

        System.out.print("Enter Improvements: ");
        performance.setImprovements(sc.next());

        System.out.print("Self Rating (1-5): ");
        performance.setSelfRating(sc.nextInt());

        perfDao.submitReview(performance);
        
    }

    private void addGoal() {
        Goal goal = new Goal();
        goal.setEmpId(empId);
        
        System.out.print("Goal Description: ");
        goal.setDescription(sc.next());
        //goal.setDeadline(Date.valueOf(sc.next()));
        while (true) {
            try {
                System.out.print("Deadline (yyyy-MM-dd): ");
                String date = sc.next();
                goal.setDeadline(java.sql.Date.valueOf(date));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format OR Use yyyy-MM-dd");
            }
        }
        System.out.print("Priority (HIGH / MEDIUM / LOW): ");
        goal.setPriority(sc.next());
        System.out.print("Success Metrics: ");
        goal.setSuccessMetrics(sc.next());
        perfDao.addGoal(goal);
    }

    private void viewGoals() {
        
    	List<Goal> goals = perfDao.getGoals(empId);

        if (goals.isEmpty()) {
            System.out.println("No goals found");
            return;
        }

        System.out.println("----- My Goals -----");

        for (Goal g : goals) {
            System.out.println(
                "Goal ID : " + g.getGoalId() +
                " | Description : " + g.getDescription() +
                " | Deadline : " + g.getDeadline() +
                " | Priority : " + g.getPriority() +
                " | Progress : " + g.getProgress() + "%" +
                " | Metrics : " + g.getSuccessMetrics()
            );
        }
    	
    }
  
}