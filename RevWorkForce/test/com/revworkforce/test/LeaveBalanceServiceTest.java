package com.revworkforce.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.revworkforce.model.LeaveBalance;
import com.revworkforce.service.LeaveBalanceService;

public class LeaveBalanceServiceTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement PreparedStatement;

    @Mock
    private ResultSet resultSet;

    private LeaveBalanceService LeaveBalanceService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        LeaveBalanceService = new LeaveBalanceService(connection);
    }

    @Test
    public void testCreateBalance_Success() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(PreparedStatement);

        when(PreparedStatement.executeUpdate())
                .thenReturn(1);

        boolean result = LeaveBalanceService.createBalance(101);

        assertTrue(result);
        verify(PreparedStatement).setInt(1, 101);
        verify(PreparedStatement).executeUpdate();
    }

    @Test
    public void testCreateBalance_Failure() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(PreparedStatement);

        when(PreparedStatement.executeUpdate())
                .thenReturn(0);

        boolean result = LeaveBalanceService.createBalance(102);

        assertFalse(result);
    }

    @Test
    public void testUpdateBalance() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(PreparedStatement);

        LeaveBalanceService.updateBalance(101, 8, 7, 6, 5);

        verify(PreparedStatement).setInt(1, 8);
        verify(PreparedStatement).setInt(2, 7);
        verify(PreparedStatement).setInt(3, 6);
        verify(PreparedStatement).setInt(4, 5);
        verify(PreparedStatement).setInt(5, 101);
        verify(PreparedStatement).executeUpdate();
    }

    @Test
    public void testGetBalance_WithData() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(PreparedStatement);

        when(PreparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);

        when(resultSet.getInt("EMP_ID")).thenReturn(101);
        when(resultSet.getInt("CL")).thenReturn(10);
        when(resultSet.getInt("SL")).thenReturn(9);
        when(resultSet.getInt("PL")).thenReturn(8);
        when(resultSet.getInt("PRIVILEGE")).thenReturn(7);

        LeaveBalance lb = LeaveBalanceService.getBalance(101);

        assertNotNull(lb);
        assertEquals(101, lb.getEmpId());
        assertEquals(10, lb.getCl());
        assertEquals(9, lb.getSl());
        assertEquals(8, lb.getPl());
        assertEquals(7, lb.getPrivilege());
    }

    @Test
    public void testGetBalance_NoData() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(PreparedStatement);

        when(PreparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        LeaveBalance lb = LeaveBalanceService.getBalance(999);

        assertNull(lb);
    }
}