package com.min.sc.user.dtos;

import java.io.Serializable;

import com.min.sc.user.model.IService_User;

public class UserInfoDTO implements Serializable{
	
	private static final long serialVersionUID = -6920318941730476206L;
	
	private String user_id; // join key
	private String user_type;
	private String user_email;
	private String user_phone;
	private String user_name;
	private String user_address;
	private String user_gender;
	private String user_birth;
	private String user_eagree;
	private String user_sagree;
	
	private UserLoginDTO logindto;
	
	public UserInfoDTO() {
		setlogindto(new UserLoginDTO());
	}
	
	public UserInfoDTO(String user_id, String user_type, String user_email, String user_phone, String user_name,
			String user_address, String user_gender, String user_birth, String user_eagree, String user_sagree) {
		super();
		this.user_id = user_id;
		this.user_type = user_type;
		this.user_email = user_email;
		this.user_phone = user_phone;
		this.user_name = user_name;
		this.user_address = user_address;
		this.user_gender = user_gender;
		this.user_birth = user_birth;
		this.user_eagree = user_eagree;
		this.user_sagree = user_sagree;
	}


	/**
	 * <b>Join 생성자</b>
	 * @param user_id
	 * @param user_type
	 * @param user_email
	 * @param user_phone
	 * @param user_name
	 * @param user_address
	 * @param user_gender
	 * @param user_birth
	 * @param user_eagree
	 * @param user_sagree
	 * @param logindto
	 */
	public UserInfoDTO(String user_id, String user_type, String user_email, String user_phone, String user_name,
			String user_address, String user_gender, String user_birth, String user_eagree, String user_sagree,
			UserLoginDTO logindto) {
		super();
		this.user_id = user_id;
		this.user_type = user_type;
		this.user_email = user_email;
		this.user_phone = user_phone;
		this.user_name = user_name;
		this.user_address = user_address;
		this.user_gender = user_gender;
		this.user_birth = user_birth;
		this.user_eagree = user_eagree;
		this.user_sagree = user_sagree;
		this.logindto = logindto;
	}

	@Override
	public String toString() {
		return "UserInfoDTO [user_id=" + user_id + ", user_type=" + user_type + ", user_email=" + user_email
				+ ", user_phone=" + user_phone + ", user_name=" + user_name + ", user_address=" + user_address
				+ ", user_gender=" + user_gender + ", user_birth=" + user_birth + ", user_eagree=" + user_eagree
				+ ", user_sagree=" + user_sagree +"]";
		// ", user_delflag=" + logindto.getUser_delflag() + ", user_deldate=" + logindto.getUser_deldate() +
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
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

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}

	public String getUser_birth() {
		return user_birth;
	}

	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}

	public String getUser_eagree() {
		return user_eagree;
	}

	public void setUser_eagree(String user_eagree) {
		this.user_eagree = user_eagree;
	}

	public String getUser_sagree() {
		return user_sagree;
	}

	public void setUser_sagree(String user_sagree) {
		this.user_sagree = user_sagree;
	}

	public UserLoginDTO getlogindto() {
		return logindto;
	}

	public void setlogindto(UserLoginDTO logindto) {
		this.logindto = logindto;
	}
	
}