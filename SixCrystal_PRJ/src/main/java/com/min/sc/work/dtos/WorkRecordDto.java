package com.min.sc.work.dtos;

import java.io.Serializable;

public class WorkRecordDto implements Serializable{

	private static final long serialVersionUID = -3536735015334690511L;
	
	private int seq;
	private String day;
	private String startDay;
	private String endDay;
	private String startState;
	private String endState;
	
	private String user_id;
	private String employee_rank;
	
	
	public WorkRecordDto() {
		// TODO Auto-generated constructor stub
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public String getStartDay() {
		return startDay;
	}


	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}


	public String getEndDay() {
		return endDay;
	}


	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}


	public String getStartState() {
		return startState;
	}


	public void setStartState(String startState) {
		this.startState = startState;
	}


	public String getEndState() {
		return endState;
	}


	public void setEndState(String endState) {
		this.endState = endState;
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


	@Override
	public String toString() {
		return "WorkRecordDto [seq=" + seq + ", day=" + day + ", startDay=" + startDay + ", endDay=" + endDay
				+ ", startState=" + startState + ", endState=" + endState + ", user_id=" + user_id + ", employee_rank="
				+ employee_rank + "]";
	}


	

}
