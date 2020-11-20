package com.min.sc.user.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.sc.emp.model.IService_Emp;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.user.model.IService_User;
import com.min.sc.ws.dtos.WorkSpaceDTO;
import com.min.sc.ws.model.IService_Ws;

@Controller
public class UserCtrl_Admin {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_User service;
	
	@RequestMapping(value = "/adminmain.do", method = RequestMethod.GET)
	public String adminMain() {
		return "admin/adminMain";
	}
	
	/**
	 * 전체 회원 정보 조회 화면으로 이동
	 * @param session 관리자의 정보를 확인하기 위한 HttpSession
	 * @param model 사용자 전체 리스트를 담기위한 Model 객체
	 * @return admin/allUserInfo.jsp
	 */
	@RequestMapping(value="/adminInfoAllMemberForm.do", method = RequestMethod.GET)
	public String infoAllMemberForm(HttpSession session, Model model) {
		UserLoginDTO dto = (UserLoginDTO) session.getAttribute("user");
		log.info("UserCtrl_Admin 전체 회원 정보 페이지로 이동 : \t {} > {}", dto.getUser_auth(),dto.getUser_id());
		List<UserInfoDTO> mAllLists = service.getUserList(null);
		model.addAttribute("mAllLists", mAllLists); // 전체 리스트
		
		Map<String, String> mapER = new HashMap<String, String>();
		mapER.put("user_type", "employer");
		List<UserInfoDTO> mListsER = service.getUserList(mapER);
		model.addAttribute("mListsER", mListsER); // 고용주 리스트
		
		Map<String, String> mapEE = new HashMap<String, String>();
		mapEE.put("user_type", "employee");
		List<UserInfoDTO> mListsEE = service.getUserList(mapEE);
		model.addAttribute("mListsEE", mListsEE); // 일반직원 리스트 
		return "admin/allUserInfo";
	}
	
	/**
	 * 전체 사업장 목록 조회화면으로 이동
	 * @param model 사업장 목록
	 * @return admin/allWsList.jsp
	 */
	@RequestMapping(value="/adminGetWsList.do", method = RequestMethod.GET)
	public String workSearch(Model model) {
		List<WorkSpaceDTO> lists = service.wsDetailSelect(null);
		log.info("UserCtrl_Admin 전체 사업장 목록 페이지로 이동 : \t {}", lists);
		model.addAttribute("lists", lists);
		return "admin/allWsList";
	}
	
}
