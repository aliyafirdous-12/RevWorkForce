package com.revworkforce.dao;

import java.util.List;

import com.revworkforce.model.Holiday;

public interface HolidayDao {

	void addHoliday(Holiday h);

	List<Holiday> getHolidays();

}