package com.revworkforce.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	private static Connection connection = null;
	
	private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String userName = "REVWORKFORCE";
	private static String password = "admin";
	
	private DBConnection() {}
	
	public static Connection getConnection() {
		
        try {
        	if (connection == null || connection.isClosed()) {
        		
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            System.out.println("Driver is Loaded");
	            
	            connection = DriverManager.getConnection(url, userName, password);
	            System.out.println("Got Database Connection");
	            System.out.println("Database Connected Successfully");
	            
        	}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
	}
	
	public static void main(String[] args) {
		
		Connection connection = DBConnection.getConnection();
        if (connection != null) {
            System.out.println("Connection Test Successful!");
        }

	}

}