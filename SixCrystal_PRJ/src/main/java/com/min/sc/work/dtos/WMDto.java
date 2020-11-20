package com.min.sc.work.dtos;


import java.io.Serializable;

public class WMDto implements Serializable{

	private static final long serialVersionUID = -6991189971078433069L;
	
	private int sch_code;
	private String ws_code;
	private String user_id;
	private String employee_rank;
	private String sch_month;
	private String work_check;
	
	public WMDto() {
		// TODO Auto-generated constructor stub
	}

	public int getSch_code() {
		return sch_code;
	}

	public void setSch_code(int sch_code) {
		this.sch_code = sch_code;
	}

	public String getWs_code() {
		return ws_code;
	}

	public void setWs_code(String ws_code) {
		this.ws_code = ws_code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getEmployee_rank() {
		return employee_rank;
	}

	public void setEmployee_rank(String employee_rank) {
		this.employee_rank = employee_rank;
	}

	public String getSch_month() {
		return sch_month;
	}

	public void setSch_month(String sch_month) {
		this.sch_month = sch_month;
	}

	public String getWork_check() {
		return work_check;
	}

	public void setWork_check(String work_check) {
		this.work_check = work_check;
	}

	@Override
	public String toString() {
		return "WorkManDTO [sch_code=" + sch_code + ", ws_code=" + ws_code + ", user_id=" + user_id + ", employee_rank="
				+ employee_rank + ", sch_month=" + sch_month + ", work_check=" + work_check + "]";
	}


}
