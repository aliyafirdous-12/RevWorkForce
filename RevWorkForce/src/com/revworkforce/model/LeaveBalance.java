package com.revworkforce.model;

public class LeaveBalance {
	
	private int empId;
    private int cl;
    private int sl;
    private int pl;
    private int privilege;
    
    public LeaveBalance() {}
    
	public LeaveBalance(int empId, int cl, int sl, int pl, int privilege) {
		
		this.empId = empId;
		this.cl = cl;
		this.sl = sl;
		this.pl = pl;
		this.privilege = privilege;
		
	}
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getCl() {
		return cl;
	}
	public void setCl(int cl) {
		this.cl = cl;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public int getPl() {
		return pl;
	}
	public void setPl(int pl) {
		this.pl = pl;
	}
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	
}