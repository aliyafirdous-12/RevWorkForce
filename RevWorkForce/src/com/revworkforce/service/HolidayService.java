package com.revworkforce.service;

import java.sql.*;
import java.util.*;

import com.revworkforce.dao.HolidayDao;
import com.revworkforce.model.Holiday;

import com.revworkforce.db.DBConnection;

public class HolidayService implements HolidayDao{
	
	Connection connection = DBConnection.getConnection();

	public HolidayService(Connection connection) {
		this.connection = connection;
	}
	
	public HolidayService() {}
	
	public void addHoliday(Holiday holiday) {
		
		try {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO HOLIDAY VALUES (HOLIDAY_SEQ.NEXTVAL,?,?)");

            ps.setString(1, holiday.getHolidayName());
            ps.setDate(2, holiday.getHolidayDate());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	public List<Holiday> getHolidays() {
		
		List<Holiday> list = new ArrayList<Holiday>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM HOLIDAY ORDER BY HOLIDAY_DATE");

            while (rs.next()) {

                Holiday holiday = new Holiday();
                holiday.setHolidayId(rs.getInt("HOLIDAY_ID"));
                holiday.setHolidayName(rs.getString("HOLIDAY_NAME"));
                holiday.setHolidayDate(rs.getDate("HOLIDAY_DATE"));
                list.add(holiday);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return list;		
	}

}
