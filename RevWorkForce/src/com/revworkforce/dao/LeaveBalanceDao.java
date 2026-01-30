package com.revworkforce.dao;

import com.revworkforce.model.LeaveBalance;

public interface LeaveBalanceDao {

	boolean createBalance(int empId);

	void updateBalance(int empId, int cl, int sl, int pl, int privilege);

	LeaveBalance getBalance(int empId);

}
