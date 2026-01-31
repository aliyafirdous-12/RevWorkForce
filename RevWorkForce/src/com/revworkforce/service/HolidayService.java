package com.revworkforce.service;

import java.sql.*;
import java.util.*;

import com.revworkforce.dao.HolidayDao;
import com.revworkforce.model.Holiday;

import com.revworkforce.db.DBConnection;

import org.apache.log4j.Logger;

public class HolidayService implements HolidayDao{
	
	private static final Logger logger = Logger.getLogger(HolidayService.class);
	
	Connection connection = DBConnection.getConnection();

	public HolidayService(Connection connection) {
		this.connection = connection;
	}
	
	public HolidayService() {}
	
	public void addHoliday(Holiday holiday) {
		
		logger.info("Adding holiday: " + holiday.getHolidayName());;
		
		try {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO HOLIDAY VALUES (HOLIDAY_SEQ.NEXTVAL,?,?)");

            ps.setString(1, holiday.getHolidayName());
            ps.setDate(2, holiday.getHolidayDate());
            ps.executeUpdate();
            logger.info("Holiday added successfully: " + holiday.getHolidayName());
            
        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while adding holiday: " + holiday.getHolidayName(), e);
        }
		
	}

	public List<Holiday> getHolidays() {
		
		logger.info("Fetching all holidays");
		
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
            
            logger.info("Total holidays fetched: " + list.size());

        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("Error while fetching holidays", e);
        }
        
		return list;		
	}

}
