package com.revworkforce.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.revworkforce.model.LeaveRequest;
import com.revworkforce.service.LeaveRequestService;

public class LeaveRequestServiceTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private LeaveRequestService LeaveRequestService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        LeaveRequestService = new LeaveRequestService(connection);
    }

    @Test
    public void testApplyLeave() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        LeaveRequest leave = new LeaveRequest();
        leave.setEmpId(101);
        leave.setFromDate(Date.valueOf("2026-02-01"));
        leave.setToDate(Date.valueOf("2026-02-03"));
        leave.setLeaveType("CL");
        leave.setReason("Personal");
        leave.setManagerComment(null);

        LeaveRequestService.applyLeave(leave);

        verify(preparedStatement).setInt(1, 101);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetLeavesByEmp() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getInt("LEAVE_ID")).thenReturn(1);
        when(resultSet.getInt("EMP_ID")).thenReturn(101);
        when(resultSet.getString("STATUS")).thenReturn("PENDING");

        List<LeaveRequest> list = LeaveRequestService.getLeavesByEmp(101);

        assertEquals(1, list.size());
    }

    @Test
    public void testCancelLeave_Success() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate())
                .thenReturn(1);

        boolean result = LeaveRequestService.cancelLeave(10);

        assertTrue(result);
    }

    @Test
    public void testCancelLeave_Failure() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate())
                .thenReturn(0);

        boolean result = LeaveRequestService.cancelLeave(11);

        assertFalse(result);
    }

    @Test
    public void testGetPendingLeaves() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);

        List<LeaveRequest> list = LeaveRequestService.getPendingLeaves(201);

        assertEquals(1, list.size());
    }

    @Test
    public void testUpdateLeaveStatus() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        LeaveRequestService.updateLeaveStatus(5, "APPROVED", "Approved by manager");

        verify(preparedStatement).setString(1, "APPROVED");
        verify(preparedStatement).setString(2, "Approved by manager");
        verify(preparedStatement).setInt(3, 5);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetLeaveReport() throws Exception {

        when(connection.createStatement())
                .thenReturn(statement);
        when(statement.executeQuery(anyString()))
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);

        LeaveRequestService.getLeaveReport();

        verify(statement).executeQuery(anyString());
    }
    
}