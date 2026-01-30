package com.revworkforce.main;

import java.util.*;

import com.revworkforce.dao.EmployeeDao;
import com.revworkforce.dao.LeaveBalanceDao;
import com.revworkforce.dao.LeaveRequestDao;
import com.revworkforce.dao.PerformanceDao;
import com.revworkforce.model.Employee;
import com.revworkforce.model.Goal;
import com.revworkforce.model.LeaveBalance;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.service.EmployeeService;
import com.revworkforce.service.LeaveBalanceService;
import com.revworkforce.service.LeaveRequestService;
import com.revworkforce.service.PerformanceService;

public class ManagerMenu {

    int managerId;
    Scanner sc = new Scanner(System.in);
    
    EmployeeDao empDao = new EmployeeService();
    LeaveRequestDao leaveRequestDao = new LeaveRequestService();
    LeaveBalanceDao balanceDao = new LeaveBalanceService();
    PerformanceDao perfDao = new PerformanceService();

    public ManagerMenu(int managerId) {
        this.managerId = managerId;
    }
    
    public void show() {

        while (true) {
        	System.out.println("Login Successful (MANAGER) ");
            System.out.println("\n===== MANAGER MENU =====");
            System.out.println("1. View Team");
            System.out.println("2. View Pending Leaves");
            System.out.println("3. Approve / Reject Leave");
            System.out.println("4. Review Performance");
            System.out.println("5. View Team Goals");
            System.out.println("6. Team Performance Report");
            System.out.println("7. View Team Leave Balance");
            System.out.println("8. Logout");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
            case 1:
                System.out.println("Team Module");

                List<Employee> team = empDao.getTeamMembers(managerId);

                System.out.println("Logged-in Manager ID: " + managerId);
                System.out.println("DEBUG → Team size = " + team.size());
                
                if (team.isEmpty()) {
                    System.out.println("No team members assigned");
                } else {
                    System.out.println("EmpId | Name | Designation");
                    for (Employee e : team) {
                        System.out.println(
                            e.getEmpId() + " | " +
                            e.getName() + " | " +
                            e.getDesignation()
                        );
                    }
                }
                break;
            case 2:
                System.out.println("View Pending Leaves");

                List<LeaveRequest> pending = leaveRequestDao.getPendingLeaves(managerId);
                System.out.println("DEBUG → Pending list size = " + pending.size());
                if (pending == null || pending.isEmpty()) {
                    System.out.println("No pending leave requests");
                } else {
                    for (LeaveRequest leave : pending) {
                        System.out.println(
                            "LeaveId: " + leave.getLeaveId() +
                            " | EmpId: " + leave.getEmpId() +
                            " | Type: " + leave.getLeaveType() +
                            " | Status: " + leave.getStatus()
                        );
                    }
                }
                break;

            case 3:
                System.out.println("Approve Leave Module ");
                System.out.print("Enter Leave Id: ");
                int leaveId = sc.nextInt();

                System.out.print("Status (APPROVED / REJECTED): ");       
                String status = sc.next();

                sc.nextLine();
                System.out.print("Comment: ");
                String comment = sc.nextLine();

                leaveRequestDao.updateLeaveStatus(leaveId, status, comment);
                System.out.println("Leave Updated Successfully");
                break;
            case 4:
            	System.out.println("Review Performance");
            	for (PerformanceReview p : perfDao.getAllReviews()) {
                    System.out.println("ReviewId: " + p.getReviewId() +
                                       " | EmpId: " + p.getEmpId() +
                                       " | Self Rating: " + p.getSelfRating());
                }
            	System.out.print("Enter Review Id: ");
                int rid = sc.nextInt();

                sc.nextLine();
                System.out.print("Feedback: ");
                String fb = sc.nextLine();

                System.out.print("Rating (1-5): ");
                int rating = sc.nextInt();

                perfDao.updateFeedback(rid, fb, rating);
                System.out.println("Feedback Submitted Successfully");
                break;
            case 5:
                System.out.println("View Team Goals");

                List<Goal> goals = perfDao.getTeamGoals(managerId);

                if (goals == null || goals.isEmpty()) {
                    System.out.println("No goals found for your team");
                    System.out.println("(Either team has no goals or employees are not mapped)");
                } else {
                    System.out.println("GoalId | Description | Progress");
                    for (Goal g : goals) {
                        System.out.println(
                            g.getGoalId() + " | " +
                            g.getDescription() + " | " +
                            g.getProgress() + "%"
                        );
                    }
                }
                break;
            case 6:
            	System.out.println("Team Performance Report");
                perfDao.teamPerformanceReport(managerId);
                break;
            case 7:
            	System.out.println("View Team Leave Balance");
                for (Employee e : empDao.getTeamMembers(managerId)) {
                    LeaveBalance lb = balanceDao.getBalance(e.getEmpId());
                    if (lb != null) {
                        System.out.println(
                            e.getName() +
                            " | CL: " + lb.getCl() +
                            " | SL: " + lb.getSl() +
                            " | PL: " + lb.getPl() +
                            " | PRIV: " + lb.getPrivilege());
                    }
                }
                break;
            case 8:
                System.out.println("Logout successful");
                new LoginApp().login();
                return;
            default:
                System.out.println("Invalid choice");
            }
            
        }
        
    }
    
}