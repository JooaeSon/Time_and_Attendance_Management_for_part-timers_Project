package com.min.sc.user.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.model.IService_User;

@Controller
public class UserCtrl_User {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_User service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * 사용자 메인화면으로 이동하는 mapping
	 * @param session
	 * @return userMain
	 */
	@RequestMapping(value="/usermain.do", method=RequestMethod.GET)
	public String userMain(HttpSession session) {
		UserInfoDTO iDto = (UserInfoDTO) session.getAttribute("user");
		log.info("UserCtrl_User 회원 메인화면 이동 : \t 사용자 > {}",iDto.getUser_id());
		return "user/userMain";
	}
	
	/**
	 * 사용자 개인정보 조회 화면 이동
	 * @return 개인정보 조회 화면
	 */
	@RequestMapping(value="/myInfo.do", method=RequestMethod.GET)
	public String myInfo(HttpSession session) {
		UserInfoDTO iDto = (UserInfoDTO) session.getAttribute("user");
		log.info("UserCtrl_User 개인정보 조회 : \t 사용자 > {}",iDto.getUser_id());
		
		return "user/userInfo";
	}
	
	/**
	 * 개인정보 수정 화면으로 이동하는 mapping
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/modifyMyInfo.do", method=RequestMethod.GET)
	public String modifyForm(HttpSession session, Model model) {
		UserInfoDTO iDto = (UserInfoDTO) session.getAttribute("user");
		model.addAttribute("addr", iDto.getUser_address());
		log.info("UserCtrl_User 개인정보 수정화면으로 이동 : \t 사용자 > {}", iDto.getUser_id());
		return "user/userInfomodifyForm";
	}
	
	/**
	 * 개인정보 수정
	 * @param session
	 * @param id
	 * @param user_email
	 * @param user_phone
	 * @param user_name
	 * @param user_address
	 * @param user_birth
	 * @param user_eagree
	 * @param user_sagree
	 * @return
	 */
	@RequestMapping(value="/modifySuccess.do", method = RequestMethod.POST)
	public String modifyInfo(HttpSession session, UserInfoDTO dto, Model model) {
		// String id, String user_email, String user_phone, 
		// String user_name, String user_address, String user_birth, String user_eagree, String user_sagree,
		UserInfoDTO nowDto = (UserInfoDTO)session.getAttribute("user");
		log.info("UserCtrl_User 개인정보 수정 : \t 사용자 정보 > {}",nowDto);
		String url = null; // 이동할 주소
		boolean isc = service.modifyUserInfo(dto);
		if(isc){
			session.removeAttribute("user"); // 현재 존재하는 세션정보에 값만 바꾸자
			UserInfoDTO newDto = service.loginInfo(dto.getUser_id());
			session.setAttribute("user", newDto);
			url = "user/userInfo";
		}else{ // 수정이 안되었다면
			model.addAttribute("msg", "개인정보 수정에 실패하였습니다.");
			url = "error";
		}
		return url;
	}
	
	/**
	 * 비밀번호 수정 폼으로 이동
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/modifyPwForm.do", method=RequestMethod.GET)
	public String modifyPwForm(HttpSession session) {
		UserInfoDTO dto = (UserInfoDTO)session.getAttribute("user");
		log.info("UserCtrl_User 비밀번호 수정 Form 이동 : \t 사용자 > {}",dto.getUser_id());
		return "user/userPwModifyForm";
	}
	
	/**
	 * 새 비밀번호 입력 폼으로 이동
	 * @param session 사용자 확인을 위한 HttpSession
	 * @return 새 비밀번호 입력 창 userNewPwInsert.jsp
	 */
	@RequestMapping(value="/insertNewPwForm.do", method=RequestMethod.GET)
	public String newPwInsertForm(HttpSession session) {
		UserInfoDTO dto = (UserInfoDTO)session.getAttribute("user");
		log.info("UserCtrl_User 새비밀번호 입력 창 : \t 사용자 > {}",dto.getUser_id());
		return "user/userNewPwInsert";
	}
	
	/**
	 * 입력받은 비밀번호 암호화 + 비밀번호 수정 
	 * @param user_id 해당 사용자의 id
	 * @param user_password 입력받은 password
	 * @param model 비밀번호 수정 실패시 해당 에러 내용을 가지고 에러페이지로 이동
	 * @param session 현재 비밀번호 입력시에 담은 비밀번호 세션을 제거하기위해 사용
	 * @return 강제로그아웃 후 로그인창으로 이동 / 비밀번호 찾기 기능으로 들어왔다면 로그인창으로 그냥 이동
	 */
	@RequestMapping(value="/modifyPwSuccess.do", method=RequestMethod.POST, produces = "applicaton/text; charset=UTF-8;")
	public String modifyPw(String user_id, String user_password, Model model, HttpSession session){
		log.info("UserCtrl_User 비밀번호 수정 : \t 사용자 > {}",user_id);
		String url = null;
		String encodedPW = passwordEncoder.encode(user_password);
		String nowPw = (String) session.getAttribute("nowPw");
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id",user_id);
		map.put("user_password",encodedPW);
		boolean isc = service.modifyPW(map);
		if(isc&&(nowPw==null)) { // 비밀번호 찾기로 들어온 사용자이다
			log.info("UserCtrl_User 비밀번호 찾기에서 수정완료 : \t 사용자 > {}",user_id);
			url = "redirect:/loginForm.do";
		}else if(isc){ 
			log.info("UserCtrl_User 비밀번호 수정에서 수정완료 : \t 사용자 > {}",user_id);
			session.removeAttribute("nowPw");
			url = "redirect:/logout.do";
		}else { // 수정이 안되었다면
			model.addAttribute("msg", "비밀번호 수정에 실패하였습니다.");
			url = "error";
		}
		return url;
	}
	
	/**
	 * 회원탈퇴 폼으로 이동
	 * @param session 사용자를 알기위한 HttpSession
	 * @return user/leaveUsForm.jsp
	 */
	@RequestMapping(value="/leaveUs.do", method = RequestMethod.GET)
	public String leaveUs(HttpSession session) {
		UserInfoDTO dto = (UserInfoDTO)session.getAttribute("user");
		log.info("UserCtrl_User 사용자가 회원탈퇴폼으로 이동 : \t {} 사용자 > {} ",new Date(), dto.getUser_id());
		return "user/leaveUsForm";
	}
	
	/**
	 * 탈퇴를 신청한 회원의 user_delfalg를 U->T로 변경
	 * @param user_id 사용자 id
	 * @return 로그아웃
	 */
	@RequestMapping(value="/leaveUsSuccess.do", method = RequestMethod.POST)
	public String leaveUsSuccess(HttpSession session, Model model) {
		UserInfoDTO dto = (UserInfoDTO) session.getAttribute("user");
		log.info("UserCtrl_User 사용자가 회원탈퇴 시도 : \t {} 사용자 > {} ",new Date(), dto.getUser_id());
		String url = null;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> lists = new ArrayList<String>();
		lists.add(dto.getUser_id());
		map.put("idLists", lists);
		boolean iscT = service.wdUser(map);
		boolean iscD = service.removeCookies(map);
		if(iscT){ 
			log.info("UserCtrl_User 회원 탈퇴 완료 : \t 사용자 > {}",dto.getUser_id());
			if(iscD) {
				log.info("UserCtrl_User 탈퇴회원 자동로그인 쿠키 삭제 완료 : \t {}",new Date());
			}else {
				log.info("UserCtrl_User 탈퇴회원 자동로그인 쿠키 삭제 실패 : \t {}",new Date());
			}
			String title = "SC 홈페이지 회원 탈퇴 완료";
			String content = "<div style='font-size:15px; text-align:center; border:1px solid #ccc; padding:10px;'>"
					+ "<b><a href='http://localhost:8088/SixCrystal_PRJ/' style='text-decoration:none;'>SC 홈페이지</a>에서 "+dto.getUser_id()+" 회원님의 회원 탈퇴를 안내드립니다.</b><br>"
					+ "회원님의 회원 탈퇴 요청에 의해, 회원님의 계정은<br>"
					+ "<b style='text-decoration:underline;'>본사의 운영 방침에 따라 탈퇴 계정으로 분류되어 회원등록이 삭제됨</b>을 알려드립니다.<br>"
					+ "약관에 안내드린 바와 같이 서비스 재이용을 원하신다면 재가입을 해주셔야 합니다.<br>"
					+ "문의사항은 <span style='text-decoration:underline;>ormm3377@gmail.com</span>으로 보내주시면 빠른 시일 내로 답변해 드리겠습니다."
					+ "<br><br> 감사합니다.</div>";
			boolean iscM = sendEmailLeaveAlert(dto.getUser_email(), title, content);
			if(iscM) {
				log.info("UserCtrl_User 회원 탈퇴 알림 메일 전송 성공 : \t {}",dto.getUser_id());
			}else {
				log.info("UserCtrl_User 회원 탈퇴 알림 메일 전송 실패 : \t {}",dto.getUser_id());
			}
			url = "redirect:/logout.do";
		}else { // 탈퇴가 되지 않았다면
			model.addAttribute("msg", "회원 탈퇴에 실패하였습니다.");
			url = "error";
		}
		return url;
	}
	
	/**
	 * 회원가입 / 회원탈퇴를 한 회원에게 이메일 보내기
	 * @param user_id 사용자 id
	 * @param title
	 * @param content
	 * @return
	 */
	private boolean sendEmailLeaveAlert(String user_email, String title, String content) {
		boolean isc = false;
		
		log.info("UserCtrl_User 이메일 전송 시도 : \t {} : {}", user_email, title);
		
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom("ormm3377@gmail.com");
			messageHelper.setTo(user_email);
			messageHelper.setSubject(title);
			messageHelper.setText(content, true);

			mailSender.send(message);
			isc = true;
			
			log.info("UserCtrl_User 이메일 전송 성공 : \t {} : {} : {}", user_email, title, content);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return isc;
	}
}
