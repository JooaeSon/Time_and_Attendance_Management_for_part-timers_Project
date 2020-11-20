package com.min.sc.user.dtos;

import java.io.Serializable;

public class UserLoginDTO implements Serializable{
	                              
	private static final long serialVersionUID = 9021433659125876173L;
	
	private String user_id;
	private String user_password;
	private String user_auth;
	private String user_delflag;
	private String user_deldate;
	private String user_lastlogin;
	
	public UserLoginDTO() {
	}

	public UserLoginDTO(String user_id, String user_password, String user_auth, String user_delflag,
			String user_deldate, String user_lastlogin) {
		super();
		this.user_id = user_id;
		this.user_password = user_password;
		this.user_auth = user_auth;
		this.user_delflag = user_delflag;
		this.user_deldate = user_deldate;
		this.user_lastlogin = user_lastlogin;
	}

	@Override
	public String toString() {
		return "UserLoginDTO [user_id=" + user_id + ", user_password=" + user_password + 

", user_auth=" + user_auth
				+ ", user_delflag=" + user_delflag + ", user_deldate=" + 

user_deldate + ", user_lastlogin="
				+ user_lastlogin + "]";
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_auth() {
		return user_auth;
	}

	public void setUser_auth(String user_auth) {
		this.user_auth = user_auth;
	}

	public String getUser_delflag() {
		return user_delflag;
	}

	public void setUser_delflag(String user_delflag) {
		this.user_delflag = user_delflag;
	}

	public String getUser_deldate() {
		return user_deldate;
	}

	public void setUser_deldate(String user_deldate) {
		this.user_deldate = user_deldate;
	}

	public String getUser_lastlogin() {
		return user_lastlogin;
	}

	public void setUser_lastlogin(String user_lastlogin) {
		this.user_lastlogin = user_lastlogin;
	}
	
}