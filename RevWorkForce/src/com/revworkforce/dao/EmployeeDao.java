package com.revworkforce.dao;

import java.util.List;

import com.revworkforce.model.Employee;

public interface EmployeeDao {

    public boolean addEmployee(Employee e);
    
    public void updateEmployee(Employee e);
    
    public Employee getEmployeeById(int empId);
    
    public List<Employee> getAllEmployees();
    
    public void updateStatus(int empId, String status);
    
    public List<Employee> searchEmployee(String keyword);
    
    public void resetPassword(int empId, String pwd);
    
    public void updateManager(int empId, int managerId);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
    public List<Employee> getTeamMembers(int managerId);
    
    public Employee getManagerDetails(int empId);
    
    public void changePassword(int empId, String oldPwd, String newPwd);
    
    public void updateProfile(int empId, String phone, String address, String emergency);
    
    
}