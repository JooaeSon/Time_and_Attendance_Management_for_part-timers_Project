package com.min.sc.user.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.model.IService_User;
import com.min.sc.ws.dtos.WorkSpaceDTO;

@Controller
public class UserCtrl_Admin_Ajax {
private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_User service;
	
	/**
	 * 회원정보 전체조회 창에서 model로 상세조회를 띄우는 Ajax 처리
	 * @param user_id 선택한 회원의 아이디
	 * @return Map<String, Object> dto : 해당 회원의 정보가 담긴 UserInfoDTO
	 */
	@ResponseBody
	@RequestMapping(value="/infoDetailForm.do", method = RequestMethod.POST)
	public Map<String, Object> infoDetail(String user_id){
		log.info("UserCtrl_Admin_Ajax 회원정보 상세조회 modal창 아작스 처리: \t 상세조회 > {}",user_id);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> input = new HashMap<String, String>();
		input.put("user_id",user_id);
		List<UserInfoDTO> list = service.getUserList(input);
		map.put("dto",list.get(0));
		return map;
	}
	
	/**
	 * 회원정보 전체 조회 창에서 전체/고용주/일반직원에 따라 테이블을 나누어서 보여주기 위한 Ajax처리
	 * @param sel 선택 값 : all / employer / employee
	 * @return String isc : basic / employer / employee
	 */
	@ResponseBody
	@RequestMapping(value="/listChange.do", method = RequestMethod.POST)
	public String listChange(String sel) {
		String isc = null;
		log.info("UserCtrl_Admin_Ajax 회원정보조회 유형선택 아작스 처리: \t 선택유형 > {}",sel);
		if(sel.trim().equals("all")) {
			isc = "basic";
		}else if(sel.trim().equals("employer")) {
			isc = "employer";
		}else {
			isc = "employee";
		}
		return isc;
	}
	
	/**
	 * 관리자가 사업장 정보 상세조회 Ajax 처리
	 * @param ws_code 해당사업장의 코드
	 * @return  Map<String, Object> dto : 해당 사업장의 정보가 담긴 WorkSpaceDTO
	 */
	@ResponseBody
	@RequestMapping(value="/wsDetailForm.do", method = RequestMethod.POST)
	public Map<String, Object> wsDetail(String ws_code){
		log.info("UserCtrl_Admin_Ajax 사업장 정보 상세조회 modal창 아작스 처리: \t 상세조회 > {}",ws_code);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> input = new HashMap<String, String>();
		input.put("ws_code", ws_code);
		List<WorkSpaceDTO> dto = service.wsDetailSelect(input);
		map.put("dto",dto.get(0));
		return map;
	}
}
