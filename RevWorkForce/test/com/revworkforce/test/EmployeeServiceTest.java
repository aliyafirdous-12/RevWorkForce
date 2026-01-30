package com.revworkforce.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;

public class EmployeeServiceTest {

	@Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private EmployeeService EmployeeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        EmployeeService = new EmployeeService(connection);
    }

    //1. Add Employee
    @Test
    public void testAddEmployee() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Employee e = new Employee();
        e.setName("Aliya");
        e.setEmail("aliya@gmail.com");
        e.setPassword("Strong@123");
        e.setPhone("9999999999");
        e.setDob(Date.valueOf("1999-01-01"));
        e.setRole("EMPLOYEE");
        e.setStatus("ACTIVE");
        e.setSalary(50000);
        e.setDesignation("DEV");

        assertTrue(EmployeeService.addEmployee(e));
    }

    //2. Update Employee
    @Test
    public void testUpdateEmployee() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        EmployeeService.updateEmployee(new Employee());

        verify(preparedStatement).executeUpdate();
    }

    //3. Get Employee By Id
    @Test
    public void testGetEmployeeById() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getInt("EMP_ID")).thenReturn(1);
        when(resultSet.getString("NAME")).thenReturn("Aliya");

        assertNotNull(EmployeeService.getEmployeeById(1));
    }

    //4. Get All Employees
    @Test
    public void testGetAllEmployees() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        assertEquals(1, EmployeeService.getAllEmployees().size());
    }

    //5. Update Status
    @Test
    public void testUpdateStatus() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        EmployeeService.updateStatus(1, "ACTIVE");

        verify(preparedStatement).executeUpdate();
    }

    //6. Search Employee
    @Test
    public void testSearchEmployee() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        assertEquals(1, EmployeeService.searchEmployee("DEV").size());
    }

    //7. Reset Password
    @Test
    public void testResetPassword() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        EmployeeService.resetPassword(1, "New@1234");

        verify(preparedStatement).executeUpdate();
    }

    //8. Update Manager
    @Test
    public void testUpdateManager() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        EmployeeService.updateManager(1, 2);

        verify(preparedStatement).executeUpdate();
    }

    //9. Get Team Members
    @Test
    public void testGetTeamMembers() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        assertEquals(1, EmployeeService.getTeamMembers(2).size());
    }

    //10. Get Manager Details
    @Test
    public void testGetManagerDetails() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        assertNotNull(EmployeeService.getManagerDetails(1));
    }

    //11. Change Password
    @Test
    public void testChangePassword() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        EmployeeService.changePassword(1, "Old@1234", "New@1234");

        verify(preparedStatement).executeUpdate();
    }

    //12. Update Profile 
    @Test
    public void testUpdateProfile() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        EmployeeService.updateProfile(1, "9999", "Hyd", "8888");

        verify(preparedStatement).executeUpdate();
    }
	
}
