package com.min.sc.sch.dtos;

import java.io.Serializable;

public class SchBasicDTO implements Serializable{
	
	

	private static final long serialVersionUID = -3256007090485514805L;
	
	private int schbasic_seq;
	private String ws_code;
	private String schbasic_json;
	private String employee_rank;
	private String schbasic_record;
	public SchBasicDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SchBasicDTO(int schbasic_seq, String ws_code, String schbasic_json, String employee_rank,
			String schbasic_record) {
		super();
		this.schbasic_seq = schbasic_seq;
		this.ws_code = ws_code;
		this.schbasic_json = schbasic_json;
		this.employee_rank = employee_rank;
		this.schbasic_record = schbasic_record;
	}
	public int getSchbasic_seq() {
		return schbasic_seq;
	}
	public void setSchbasic_seq(int schbasic_seq) {
		this.schbasic_seq = schbasic_seq;
	}
	public String getWs_code() {
		return ws_code;
	}
	public void setWs_code(String ws_code) {
		this.ws_code = ws_code;
	}
	public String getSchbasic_json() {
		return schbasic_json;
	}
	public void setSchbasic_json(String schbasic_json) {
		this.schbasic_json = schbasic_json;
	}
	public String getEmployee_rank() {
		return employee_rank;
	}
	public void setEmployee_rank(String employee_rank) {
		this.employee_rank = employee_rank;
	}
	public String getSchbasic_record() {
		return schbasic_record;
	}
	public void setSchbasic_record(String schbasic_record) {
		this.schbasic_record = schbasic_record;
	}
	@Override
	public String toString() {
		return "SchBasicDTO [schbasic_seq=" + schbasic_seq + ", ws_code=" + ws_code + ", schbasic_json=" + schbasic_json
				+ ", employee_rank=" + employee_rank + ", schbasic_record=" + schbasic_record + "]";
	}
	
	
	
}
