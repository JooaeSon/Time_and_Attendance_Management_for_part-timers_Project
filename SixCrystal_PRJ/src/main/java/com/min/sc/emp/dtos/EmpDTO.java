package com.min.sc.emp.dtos;

public class EmpDTO {

	private String emp_code;
	private String user_id;
	private String ws_code;
	private String emp_rank;
	private String emp_hiredate;
	private String emp_hiredate_end;
	private String emp_position;
	private String emp_worktime_time;
	private String emp_holiday;
	private String emp_salary;
	private String emp_salary_day;
	private String emp_resign;
	private String emp_confirm;
	private String emp_apply;

	public EmpDTO() {
	}

	public EmpDTO(String emp_code, String user_id, String ws_code, String emp_rank, String emp_hiredate,
			String emp_hiredate_end, String emp_position, String emp_worktime_time, String emp_holiday,
			String emp_salary, String emp_salary_day, String emp_resign, String emp_confirm, String emp_apply) {
		super();
		this.emp_code = emp_code;
		this.user_id = user_id;
		this.ws_code = ws_code;
		this.emp_rank = emp_rank;
		this.emp_hiredate = emp_hiredate;
		this.emp_hiredate_end = emp_hiredate_end;
		this.emp_position = emp_position;
		this.emp_worktime_time = emp_worktime_time;
		this.emp_holiday = emp_holiday;
		this.emp_salary = emp_salary;
		this.emp_salary_day = emp_salary_day;
		this.emp_resign = emp_resign;
		this.emp_confirm = emp_confirm;
		this.emp_apply = emp_apply;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getWs_code() {
		return ws_code;
	}

	public void setWs_code(String ws_code) {
		this.ws_code = ws_code;
	}

	public String getEmp_rank() {
		return emp_rank;
	}

	public void setEmp_rank(String emp_rank) {
		this.emp_rank = emp_rank;
	}

	public String getEmp_hiredate() {
		return emp_hiredate;
	}

	public void setEmp_hiredate(String emp_hiredate) {
		this.emp_hiredate = emp_hiredate;
	}

	public String getEmp_hiredate_end() {
		return emp_hiredate_end;
	}

	public void setEmp_hiredate_end(String emp_hiredate_end) {
		this.emp_hiredate_end = emp_hiredate_end;
	}

	public String getEmp_position() {
		return emp_position;
	}

	public void setEmp_position(String emp_position) {
		this.emp_position = emp_position;
	}

	public String getEmp_worktime_time() {
		return emp_worktime_time;
	}

	public void setEmp_worktime_time(String emp_worktime_time) {
		this.emp_worktime_time = emp_worktime_time;
	}

	public String getEmp_holiday() {
		return emp_holiday;
	}

	public void setEmp_holiday(String emp_holiday) {
		this.emp_holiday = emp_holiday;
	}

	public String getEmp_salary() {
		return emp_salary;
	}

	public void setEmp_salary(String emp_salary) {
		this.emp_salary = emp_salary;
	}

	public String getEmp_salary_day() {
		return emp_salary_day;
	}

	public void setEmp_salary_day(String emp_salary_day) {
		this.emp_salary_day = emp_salary_day;
	}

	public String getEmp_resign() {
		return emp_resign;
	}

	public void setEmp_resign(String emp_resign) {
		this.emp_resign = emp_resign;
	}

	public String getEmp_confirm() {
		return emp_confirm;
	}

	public void setEmp_confirm(String emp_confirm) {
		this.emp_confirm = emp_confirm;
	}

	public String getEmp_apply() {
		return emp_apply;
	}

	public void setEmp_apply(String emp_apply) {
		this.emp_apply = emp_apply;
	}

	@Override
	public String toString() {
		return "EmpDto [emp_code=" + emp_code + ", user_id=" + user_id + ", ws_code=" + ws_code + ", emp_rank="
				+ emp_rank + ", emp_hiredate=" + emp_hiredate + ", emp_hiredate_end=" + emp_hiredate_end
				+ ", emp_position=" + emp_position + ", emp_worktime_time=" + emp_worktime_time + ", emp_holiday="
				+ emp_holiday + ", emp_salary=" + emp_salary + ", emp_salary_day=" + emp_salary_day + ", emp_resign="
				+ emp_resign + ", emp_confirm=" + emp_confirm + ", emp_apply=" + emp_apply + "]";
	}
}
