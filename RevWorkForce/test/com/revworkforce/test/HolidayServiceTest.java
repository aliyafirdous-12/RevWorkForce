package com.revworkforce.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.revworkforce.model.Holiday;
import com.revworkforce.service.HolidayService;

public class HolidayServiceTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private HolidayService HolidayService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        HolidayService = new HolidayService(connection);
    }

    // -------------------------------
    // 1. ADD HOLIDAY - SUCCESS
    // -------------------------------
    @Test
    public void testAddHoliday() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        Holiday holiday = new Holiday();
        holiday.setHolidayName("Republic Day");
        holiday.setHolidayDate(Date.valueOf("2026-01-26"));

        HolidayService.addHoliday(holiday);

        verify(preparedStatement).setString(1, "Republic Day");
        verify(preparedStatement).setDate(2, Date.valueOf("2026-01-26"));
        verify(preparedStatement).executeUpdate();
    }

    // -------------------------------
    // 2. ADD HOLIDAY - DB ERROR
    // -------------------------------
    @Test
    public void testAddHoliday_Exception() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException("DB Error"));

        Holiday holiday = new Holiday();
        holiday.setHolidayName("Error Day");

        // should NOT throw exception
        HolidayService.addHoliday(holiday);
    }

    // -------------------------------
    // 3. GET HOLIDAYS - DATA EXISTS
    // -------------------------------
    @Test
    public void testGetHolidays() throws Exception {

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getInt("HOLIDAY_ID")).thenReturn(1);
        when(resultSet.getString("HOLIDAY_NAME")).thenReturn("Independence Day");
        when(resultSet.getDate("HOLIDAY_DATE"))
                .thenReturn(Date.valueOf("2026-08-15"));

        List<Holiday> list = HolidayService.getHolidays();

        assertEquals(1, list.size());
        assertEquals("Independence Day", list.get(0).getHolidayName());
    }

    // -------------------------------
    // 4. GET HOLIDAYS - NO DATA
    // -------------------------------
    @Test
    public void testGetHolidays_NoData() throws Exception {

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        List<Holiday> list = HolidayService.getHolidays();

        assertTrue(list.isEmpty());
    }
}