package com.min.sc.user.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.protocol.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.user.model.IService_User;

@Controller
public class UserCtrl_User_Ajax {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_User service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 아이디 중복검사 결과 Ajax처리
	 * @param String user_id
	 * @return boolean 중복된 결과가 존재하면 false : 사용가능하다면 true;
	 */
	@ResponseBody
	@RequestMapping(value="/idChk.do", method = RequestMethod.POST)
	public String idChk(String user_id) {
		log.info("UserCtrl_User_Ajax 아이디 중복 체크 아작스 처리: \t {}",user_id);
		String id = service.idChk(user_id);
		String isc = "true";
		if(id != null) {
			isc = "false";
		}
		return isc;
	}
	
	/**
	 * 로그인한 상태에서 비밀번호 수정시 비밀번호 일치 여부 Ajax처리
	 * 로그인한 상태에서 회원탈퇴 비밀번호 일치 여부 Ajax처리
	 * @param String user_password 입력받은 비밀번호
	 * @param String user_id 해당 유저의 아이디
	 * @return 비밀번호 일치시 true / 불일치시 false
	 */
	@ResponseBody
	@RequestMapping(value="/chkNowPw.do", method = RequestMethod.POST)
	public String chkNowPassword(String user_password, String user_id, HttpSession session) {
		log.info("UserCtrl_User_Ajax 비밀번호 일치여부 아작스 처리: \t {}",user_id);
		UserLoginDTO dto = service.login(user_id);
		String isc = "false";
		if(passwordEncoder.matches(user_password,dto.getUser_password())) { // matches(raw pw , encoded pw)
	         isc = "true";
	         session.setAttribute("nowPw", user_password); // 받아온 현재 비밀번호를 session에 저장
	    }
		return isc;
	}
	
	/**
	 * 아이디 찾기 정보일치 검사 Ajax처리
	 * @param user_name 사용자 이름
	 * @param user_phone 사용자의 휴대전화 
	 * @param user_email 사용자의 이메일
	 * @param model 아이디가 존재한다면 model에 해당 아이디 정보를 담아서 보냄
	 * @return 일치하는 정보 존재시 true / 불일치시 false
	 */
	@ResponseBody
	@RequestMapping(value="/findID.do", method = RequestMethod.POST)
	public Map<String, String> chkInfoForFindID(String user_name, String user_phone, String user_email, Model model) {
		log.info("UserCtrl_User_Ajax 아이디 찾기 아작스 처리: \t 사용자 이름 > {}",user_name);
		Map<String, String> map = new HashMap<String, String>(); // 아작스 결과로 return할 map
		Map<String, String> infomap = new HashMap<String, String>(); // 아이디 찾기에 사용할 map
		infomap.put("user_name", user_name);
		if(user_phone != null) {
			infomap.put("user_phone", user_phone.trim());
		}else if(user_email != null) {
			infomap.put("user_email", user_email.trim());
		}
		String id = service.findID(infomap);
		if(id != null) {
			String user_id = service.getIdForFindID(infomap);
			UserLoginDTO dto = service.getLoginUser(user_id);
			String flag = dto.getUser_delflag();
			if(flag.equals("T")) {
				map.put("isc","leave");
			}else {
				map.put("isc","true");
				map.put("id",id);
			}
		}else {
			map.put("isc","false");
		}
		return map;
	}
	
	/**
	 * 비밀번호 찾기 아이디 존재여부 Ajax 처리
	 * @param String user_id 입력받은 아이디
	 * @return String 해당 아이디 존재 시 "true" / 미존재 시 "false"
	 */
	@ResponseBody
	@RequestMapping(value="/chkIdForPw.do", method=RequestMethod.POST)
	public String chkIdForPw(String user_id){
		log.info("UserCtrl_User_Ajax 비밀번호 찾기 아이디 존재여부 아작스 처리: \t 아이디 > {}",user_id);
		String isc = "false";
		String matchId = service.idChk(user_id);
		UserLoginDTO dto = service.getLoginUser(user_id);
		String del = dto.getUser_delflag();
		if(user_id.trim().equals(matchId)) {
			isc = "true";
		}
		if(del.equals("T")) {
			isc = "leave";
		}
		return isc;
	}
	 
}
