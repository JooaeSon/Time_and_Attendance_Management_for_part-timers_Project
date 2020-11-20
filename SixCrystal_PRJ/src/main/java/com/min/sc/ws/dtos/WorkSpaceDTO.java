package com.min.sc.ws.dtos;

import java.io.Serializable;

public class WorkSpaceDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -449429875668105357L;
	private String ws_code;
	private String user_id;
	private String ws_name;
	private String ws_loc;
	private String ws_num;
	private String ws_email;
	private String ws_ip;
	private String ws_ssid;
	private String ws_vol;
	private String ws_deld;
	private String ws_delflag;
	
	public WorkSpaceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WorkSpaceDTO(String ws_code, String user_id, String ws_name, String ws_loc, String ws_num, String ws_email,
			String ws_ip, String ws_ssid, String ws_vol, String ws_deld, String ws_delflag) {
		super();
		this.ws_code = ws_code;
		this.user_id = user_id;
		this.ws_name = ws_name;
		this.ws_loc = ws_loc;
		this.ws_num = ws_num;
		this.ws_email = ws_email;
		this.ws_ip = ws_ip;
		this.ws_ssid = ws_ssid;
		this.ws_vol = ws_vol;
		this.ws_deld = ws_deld;
		this.ws_delflag = ws_delflag;
	}

	@Override
	public String toString() {
		return "WorkSpaceDTO [ws_code=" + ws_code + ", user_id=" + user_id + ", ws_name=" + ws_name + ", ws_loc="
				+ ws_loc + ", ws_num=" + ws_num + ", ws_email=" + ws_email + ", ws_ip=" + ws_ip + ", ws_ssid=" + ws_ssid
				+ ", ws_vol=" + ws_vol + ", ws_deld=" + ws_deld + ", ws_delflag=" + ws_delflag + "]";
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

	public String getWs_name() {
		return ws_name;
	}

	public void setWs_name(String ws_name) {
		this.ws_name = ws_name;
	}

	public String getWs_loc() {
		return ws_loc;
	}

	public void setWs_loc(String ws_loc) {
		this.ws_loc = ws_loc;
	}

	public String getWs_num() {
		return ws_num;
	}

	public void setWs_num(String ws_num) {
		this.ws_num = ws_num;
	}

	public String getWs_email() {
		return ws_email;
	}

	public void setWs_email(String ws_email) {
		this.ws_email = ws_email;
	}

	public String getWs_ip() {
		return ws_ip;
	}

	public void setWs_ip(String ws_ip) {
		this.ws_ip = ws_ip;
	}

	public String getWs_ssid() {
		return ws_ssid;
	}

	public void setWs_ssid(String ws_ssid) {
		this.ws_ssid = ws_ssid;
	}

	public String getWs_vol() {
		return ws_vol;
	}

	public void setWs_vol(String ws_vol) {
		this.ws_vol = ws_vol;
	}

	public String getWs_deld() {
		return ws_deld;
	}

	public void setWs_deld(String ws_deld) {
		this.ws_deld = ws_deld;
	}

	public String getWs_delflag() {
		return ws_delflag;
	}

	public void setWs_delflag(String ws_delflag) {
		this.ws_delflag = ws_delflag;
	}

	public String getWs_num2() {
		String ws_num2="";
		if(ws_num.indexOf("2")==1) {
			ws_num2=ws_num.substring(2);
		} else {
			ws_num2=ws_num.substring(3);
		}
		return ws_num2;
	}

	public String getWs_reip() {
		String Ws_reip=ws_ip.replace(".", "");
		return Ws_reip;
	}
	
}