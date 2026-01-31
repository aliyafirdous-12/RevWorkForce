package com.revworkforce.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.revworkforce.model.Goal;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.service.PerformanceService;

public class PerformanceServiceTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private PerformanceService PerformanceService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PerformanceService = new PerformanceService(connection);
    }

    @Test
    public void testSubmitReview() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        PerformanceReview pr = new PerformanceReview();
        pr.setEmpId(101);
        pr.setDeliverables("Modules");
        pr.setAchievements("Completed on time");
        pr.setImprovements("Code quality");
        pr.setSelfRating(4);

        PerformanceService.submitReview(pr);

        verify(preparedStatement).setInt(1, 101);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllReviews() throws Exception {

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString()))
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("REVIEW_ID")).thenReturn(1);
        when(resultSet.getInt("EMP_ID")).thenReturn(101);

        List<PerformanceReview> list = PerformanceService.getAllReviews();

        assertEquals(1, list.size());
    }

    @Test
    public void testUpdateFeedback() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        PerformanceService.updateFeedback(1, "Good work", 5);

        verify(preparedStatement).setString(1, "Good work");
        verify(preparedStatement).setInt(2, 5);
        verify(preparedStatement).setInt(3, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testAddGoal() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Goal goal = new Goal();
        goal.setEmpId(101);
        goal.setDescription("Learn Spring");
        goal.setDeadline(Date.valueOf("2026-06-01"));
        goal.setPriority("HIGH");
        goal.setSuccessMetrics("Certification");

        PerformanceService.addGoal(goal);

        verify(preparedStatement).setInt(1, 101);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetGoals() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("GOAL_ID")).thenReturn(1);

        List<Goal> list = PerformanceService.getGoals(101);

        assertEquals(1, list.size());
    }

    @Test
    public void testGetTeamGoals() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);

        List<Goal> list = PerformanceService.getTeamGoals(201);

        assertEquals(1, list.size());
    }

    @Test
    public void testUpdateGoalProgress() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        PerformanceService.updateGoalProgress(10, 80);

        verify(preparedStatement).setInt(1, 80);
        verify(preparedStatement).setInt(2, 10);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testTeamPerformanceReport() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);

        PerformanceService.teamPerformanceReport(201);

        verify(preparedStatement).setInt(1, 201);
    }
    
}