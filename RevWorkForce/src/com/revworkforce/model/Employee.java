package com.revworkforce.model;

import java.sql.Date;

public class Employee {
	
	private int empId;                
    private String name;              
    private String email;             
    private String password;          
    private String phone;             
    private String address;           
    private String emergencyContact;  
    private Date dob;                 
    private String role;              
    private Integer managerId;        
    private String status;
    private double salary;            
    private String designation;       
    private Integer deptId;
    
    public Employee() {}
    
    public Employee(int empId, String name, String email, String password,
			String phone, String address, String emergencyContact, Date dob,
			String role, Integer managerId, String status, double salary,
			String designation, Integer deptId) {
		
		this.empId = empId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.emergencyContact = emergencyContact;
		this.dob = dob;
		this.role = role;
		this.managerId = managerId;
		this.status = status;
		this.salary = salary;
		this.designation = designation;
		this.deptId = deptId;
		
	}

	public int getEmpId() {
		return empId;
	}
    
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	  	
}