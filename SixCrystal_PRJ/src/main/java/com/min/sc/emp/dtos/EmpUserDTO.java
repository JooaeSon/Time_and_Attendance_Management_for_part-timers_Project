package com.min.sc.emp.dtos;

public class EmpUserDTO {

	private String user_id;
	private String user_name;
	private String user_address;
	private String user_birth;
	private String user_email;
	private String user_gender;
	private String user_phone;
	private String emp_code;
	private String emp_apply;
	private String emp_confirm;
	private String emp_hiredate;
	private String emp_hiredate_end;
	private String emp_holiday;
	private String emp_position;
	private String emp_rank;
	private String emp_salary;
	private String emp_salary_day;
	private String emp_worktime_time;
	private String ws_code;

	public EmpUserDTO() {
		// TODO Auto-generated constructor stub
	}

	public EmpUserDTO(String user_id, String user_name, String user_address, String user_birth, String user_email,
			String user_gender, String user_phone, String emp_code, String emp_apply, String emp_confirm,
			String emp_hiredate, String emp_hiredate_end, String emp_holiday, String emp_position, String emp_rank,
			String emp_salary, String emp_salary_day, String emp_worktime_time, String ws_code) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_address = user_address;
		this.user_birth = user_birth;
		this.user_email = user_email;
		this.user_gender = user_gender;
		this.user_phone = user_phone;
		this.emp_code = emp_code;
		this.emp_apply = emp_apply;
		this.emp_confirm = emp_confirm;
		this.emp_hiredate = emp_hiredate;
		this.emp_hiredate_end = emp_hiredate_end;
		this.emp_holiday = emp_holiday;
		this.emp_position = emp_position;
		this.emp_rank = emp_rank;
		this.emp_salary = emp_salary;
		this.emp_salary_day = emp_salary_day;
		this.emp_worktime_time = emp_worktime_time;
		this.ws_code = ws_code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getUser_birth() {
		return user_birth;
	}

	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getEmp_apply() {
		return emp_apply;
	}

	public void setEmp_apply(String emp_apply) {
		this.emp_apply = emp_apply;
	}

	public String getEmp_confirm() {
		return emp_confirm;
	}

	public void setEmp_confirm(String emp_confirm) {
		this.emp_confirm = emp_confirm;
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

	public String getEmp_holiday() {
		return emp_holiday;
	}

	public void setEmp_holiday(String emp_holiday) {
		this.emp_holiday = emp_holiday;
	}

	public String getEmp_position() {
		return emp_position;
	}

	public void setEmp_position(String emp_position) {
		this.emp_position = emp_position;
	}

	public String getEmp_rank() {
		return emp_rank;
	}

	public void setEmp_rank(String emp_rank) {
		this.emp_rank = emp_rank;
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

	public String getEmp_worktime_time() {
		return emp_worktime_time;
	}

	public void setEmp_worktime_time(String emp_worktime_time) {
		this.emp_worktime_time = emp_worktime_time;
	}

	public String getWs_code() {
		return ws_code;
	}

	public void setWs_code(String ws_code) {
		this.ws_code = ws_code;
	}

	@Override
	public String toString() {
		return "EmpUserDTO [user_id=" + user_id + ", user_name=" + user_name + ", user_address=" + user_address
				+ ", user_birth=" + user_birth + ", user_email=" + user_email + ", user_gender=" + user_gender
				+ ", user_phone=" + user_phone + ", emp_code=" + emp_code + ", emp_apply=" + emp_apply
				+ ", emp_confirm=" + emp_confirm + ", emp_hiredate=" + emp_hiredate + ", emp_hiredate_end="
				+ emp_hiredate_end + ", emp_holiday=" + emp_holiday + ", emp_position=" + emp_position + ", emp_rank="
				+ emp_rank + ", emp_salary=" + emp_salary + ", emp_salary_day=" + emp_salary_day
				+ ", emp_worktime_time=" + emp_worktime_time + ", ws_code=" + ws_code + "]";
	}
	
}
