package com.revworkforce.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.revworkforce.model.Notification;
import com.revworkforce.service.NotificationService;

public class NotificationServiceTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private NotificationService NotificationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        NotificationService = new NotificationService(connection);
    }

    @Test
    public void testSendNotification() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        NotificationService.sendNotification(101, "Welcome to RevWorkforce");

        verify(preparedStatement).setInt(1, 101);
        verify(preparedStatement).setString(2, "Welcome to RevWorkforce");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetNotifications_WithData() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getInt("NOTIF_ID")).thenReturn(1);
        when(resultSet.getInt("EMP_ID")).thenReturn(101);
        when(resultSet.getString("MESSAGE")).thenReturn("Test Message");
        when(resultSet.getString("STATUS")).thenReturn("UNREAD");
        when(resultSet.getDate("CREATED_DATE"))
                .thenReturn(Date.valueOf("2026-02-01"));
        when(resultSet.getString("IS_READ")).thenReturn("N");

        List<Notification> list = NotificationService.getNotifications(101);

        assertEquals(1, list.size());
        assertEquals("Test Message", list.get(0).getMessage());
    }

    @Test
    public void testMarkAsRead() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        NotificationService.markAsRead(101);

        verify(preparedStatement).setInt(1, 101);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetUnreadCount() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(3);

        int count = NotificationService.getUnreadCount(101);

        assertEquals(3, count);
    }
    
}