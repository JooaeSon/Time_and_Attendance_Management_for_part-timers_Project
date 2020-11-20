package com.min.sc.wn.dtos;

import java.io.Serializable;

public class WNBoardDTO implements Serializable{

	private static final long serialVersionUID = 5311330186456629541L;
	private int wn_seq     ;
	private String user_id    ;
	private String wn_title   ;
	private String wn_content ;
	private String wn_regdate ;
	private int wn_hit     ;
	private char wn_enabled ;
	public WNBoardDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WNBoardDTO(int wn_seq, String user_id, String wn_title, String wn_content, String wn_regdate, int wn_hit,
			char wn_enabled) {
		super();
		this.wn_seq = wn_seq;
		this.user_id = user_id;
		this.wn_title = wn_title;
		this.wn_content = wn_content;
		this.wn_regdate = wn_regdate;
		this.wn_hit = wn_hit;
		this.wn_enabled = wn_enabled;
	}
	public int getWn_seq() {
		return wn_seq;
	}
	public void setWn_seq(int wn_seq) {
		this.wn_seq = wn_seq;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getWn_title() {
		return wn_title;
	}
	public void setWn_title(String wn_title) {
		this.wn_title = wn_title;
	}
	public String getWn_content() {
		return wn_content;
	}
	public void setWn_content(String wn_content) {
		this.wn_content = wn_content;
	}
	public String getWn_regdate() {
		return wn_regdate;
	}
	public void setWn_regdate(String wn_regdate) {
		this.wn_regdate = wn_regdate;
	}
	public int getWn_hit() {
		return wn_hit;
	}
	public void setWn_hit(int wn_hit) {
		this.wn_hit = wn_hit;
	}
	public char getWn_enabled() {
		return wn_enabled;
	}
	public void setWn_enabled(char wn_enabled) {
		this.wn_enabled = wn_enabled;
	}
	@Override
	public String toString() {
		return "WNBoardDTO [wn_seq=" + wn_seq + ", user_id=" + user_id + ", wn_title=" + wn_title + ", wn_content="
				+ wn_content + ", wn_regdate=" + wn_regdate + ", wn_hit=" + wn_hit + ", wn_enabled=" + wn_enabled + "]";
	}
	
}
