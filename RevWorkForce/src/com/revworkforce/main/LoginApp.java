package com.revworkforce.main;

import java.sql.*;
import java.util.*;

import com.revworkforce.db.DBConnection;
import com.revworkforce.util.ValidationUtil;

public class LoginApp {

    Scanner sc = new Scanner(System.in);

    public void login() {

        System.out.println("\n===== RevWorkForce Login =====");
        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();

        //System.out.print("Enter Password: ");
        //String password = sc.next();
        String password;
        while (true) {
            try {
                System.out.print("Enter Password: ");
                password = sc.next();
                ValidationUtil.validatePassword(password);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) {
                System.out.println("Database connection failed. Please restart Oracle.");
                return;
            }

            PreparedStatement ps = connection.prepareStatement(
                "SELECT ROLE, NAME FROM EMPLOYEE WHERE EMP_ID=? AND PASSWORD=? AND STATUS='ACTIVE'");
            
            ps.setInt(1, empId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("ROLE");
                String name = rs.getString("ROLE");

                System.out.println("\nWelcome " + name + " (" + role + ")");

                if (role.equalsIgnoreCase("ADMIN")) {
                    new AdminMenu().show();
                } 
                else if (role.equalsIgnoreCase("MANAGER")) {
                    new ManagerMenu(empId).show();
                } 
                else {
                    new EmployeeMenu(empId).show();
                }
            } 
            else {
                System.out.println("Invalid Credentials");
                login();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}