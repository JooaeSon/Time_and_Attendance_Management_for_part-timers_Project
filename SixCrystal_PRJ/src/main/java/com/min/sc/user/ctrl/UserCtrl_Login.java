package com.min.sc.user.ctrl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.user.model.IService_User;

@Controller
public class UserCtrl_Login {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_User service;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping(value = "/loginForm.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(@RequestParam(value = "error", required = false) String error,
							@RequestParam(value = "logout", required = false) String logout, Model model,
							Authentication user, HttpServletRequest request) {
		if(user != null) {
		// Authentication -> UserDetails 를 받아오는 과정
		UserDetails userD = (UserDetails) user.getPrincipal();
//		userD.getUsername(); // pk
		model.addAttribute("userD",userD.toString());
		}
		if (error != null) {
			String errormsg = (String) request.getAttribute("errormsg");
			model.addAttribute("error",errormsg);
			System.out.println("로그아웃 에러 : "+errormsg);
		}
		if (logout != null) {
			model.addAttribute("msg", "로그아웃 성공");
			System.out.println("logout 로그아웃 성공");
		}
		return "login/loginForm";
	}
	
	/**
	 * 로그인 성공 후 Auth에 따라 메인 페이지로 분기하는 mapping
	 * 해당 아이디가 사용 가능한 상태인지 판별, 로그인 날짜 업데이트, 세션에 정보담기
	 * @param user 사용자 정보(아이디,비밀번호,권한)를 담은 security의 객체
	 * @param model
	 * @param session 세션에 담음 (아이디/타입(고용주/일반직원)/이메일/핸드폰/이름/주소/성별/생일/이메일수신동의/sms수신동의)
	 * @return String 각 상태에 따라 이동할 주소
	 */
	@RequestMapping(value = "/loginResult.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String main(Authentication user, Model model, HttpSession session) {
		log.info("UserCtrl_Login main페이지 분기");
		String url = "";
		if(user != null) {
			UserDetails userD = (UserDetails) user.getPrincipal();
			log.info("UserCtrl_Login main페이지 유저확인(Authentication) : \t{}",userD.toString());
			
			String auth = userD.getAuthorities().toString();
			log.info("UserCtrl_Login main페이지 유저권한 확인 : \t{}",auth);
			if(auth.equals("[ROLE_Admin]")) {
				log.info("UserCtrl_Login main페이지 관리자확인 : \t{}",session.getAttribute("user"));
				url="admin/adminMain";
			}else {
				log.info("UserCtrl_Login main페이지 회원확인(DTO) : \t{}",session.getAttribute("user"));
				UserInfoDTO dto = (UserInfoDTO) session.getAttribute("user");
				if(dto.getUser_type().equals("employer")) {
					url="redirect:/ws.do";
				}else {
					url="user/userMain";
				}
			}
		}else {
			url="login/loginForm";
		}
		return url;
	}
	
	/**
	 * 회원유형 선택 후 약관동의 페이지로 이동하는 mapping
	 * @param employee 선택유형 - 일반직원
	 * @param employer 선택유형 - 고용주
	 * @param model 해당 타입 화면으로 보내기
	 * @return login/agreeForSignUp.jsp
	 */
	@RequestMapping(value="/agreeForm.do", method = RequestMethod.GET)
	public String agreeForm(@RequestParam(value = "employee", required = false) String employee, 
			@RequestParam(value = "employer", required = false) String employer, Model model) {
		log.info("UserCtrl_Login 약관동의 페이지로 이동");
		if (employee != null) {
			model.addAttribute("user_type", "employee");
		}
		if (employer != null) {
			model.addAttribute("user_type", "employer");
		}
		return "login/agreeForSignUp";
	}
	
	/**
	 * 회원가입 정보입력 화면으로 이동
	 * @param user_type 선택한 회원 유형
	 * @param model
	 * @return login/signUpForm.jsp
	 */
	@RequestMapping(value = "/singUpgo.do", method = RequestMethod.POST)
	public String SignUpgo(String user_type, Model model) {
		log.info("UserCtrl_Login 회원가입 페이지로 이동");
		if (user_type.trim().equals("employee")) {
			model.addAttribute("user_type", "employee");
		}
		if (user_type.trim().equals("employer")) {
			model.addAttribute("user_type", "employer");
		}
		return "login/signUpForm";
	}
	
	/**
	 * 주소 입력 팝업창 띄우기
	 * @author hyo
	 * @return String jusoPopup.jsp
	 */
	@RequestMapping(value="/jusoPopup.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String jusoPopup() {
		log.info("UserCtrl_Login 주소검색창 : \t {}",new Date());
		return "login/jusoPopup";
	}
	
	/**
	 * 회원가입 성공후 로그인창으로 이동
	 * @param user_name 이름
	 * @param user_id 아이디
	 * @param user_password 비밀번호
	 * @param user_type 유형(고용주/일반직원)
	 * @param user_email 이메일
	 * @param user_phone 핸드폰
	 * @param user_address 주소
	 * @param user_gender 성별
	 * @param user_birth 생일
	 * @param user_eagree 이메일 수신 동의
	 * @param user_sagree sms 수신 동의
	 * @param model
	 * @return login/loginForm.jsp
	 */
	@RequestMapping(value = "/signUpSuccess.do", method = RequestMethod.POST)
	public String maingo(String user_name, String user_id, String user_password, String user_type, 
			String user_email, String user_phone, String user_address, String user_gender, 
			String user_birth, String user_eagree, String user_sagree, Model model) {
		log.info("UserCtrl_Login singUpSc.do 회원가입 시도");
		String url = null;
		System.out.println(user_name+":"+user_id+":"+user_password+":"+user_type+":"+user_email+":"+user_phone+":"+user_address+":"+user_gender+":"+user_birth+":"+user_eagree+":"+user_sagree);
		UserLoginDTO lDto = new UserLoginDTO(user_id, user_password, "ROLE_User", "U", "", "");
		UserInfoDTO iDto = new UserInfoDTO(user_id, user_type, user_email, user_phone, user_name, user_address, user_gender, user_birth, user_eagree, user_sagree, lDto);
		log.info("UserCtrl_Login 회원가입 정보 : \t login:{} & info:{}",lDto,iDto);
		System.out.println("UserCtrl 회원가입 정보 : " +lDto+iDto);
		boolean isc =service.join(lDto, iDto);
		if(isc){ // 회원가입 성공
			log.info("UserCtrl_Login 회원 가입 완료 : \t 사용자 > {}",user_id);
			String title = "SC 홈페이지 회원 가입 완료";
			String content = "<div style='font-size:15px; text-align:center; border:1px solid #ccc; padding:10px;'>"
					+ "<b><a href='http://localhost:8088/SixCrystal_PRJ/' style='text-decoration:none;'>SC 홈페이지</a>에서 "+user_id+" 회원님의 회원 가입을 축하드립니다!</b><br>"
					+ "회원님은 SC 홈페이지의 서비스를 이용하실 수 있습니다.<br>"
					+ "문의사항은 <span style='text-decoration:underline;>ormm3377@gmail.com</span>으로 보내주시면 빠른 시일 내로 답변해 드리겠습니다."
					+ "<br><br> 감사합니다.</div>";
			boolean iscM = sendEmailSignUpAlert(user_email, title, content);
			if(iscM) {
				log.info("UserCtrl_Login 회원 가입 축하 메일 발송 성공: \t 사용자 > {}",user_id);
			}else {
				log.info("UserCtrl_Login 회원 가입 축하 메일 발송 실패 : \t 사용자 > {}",user_id);
			}
			url = "login/loginForm";
		}else { // 회원가입이 되지 않았다면
			model.addAttribute("msg", "회원 가입에 실패하였습니다.");
			url = "error";
		}
		return url;
	}

	// 권한이 없는 곳으로 접근했을 때
	@RequestMapping(value = "/access_denied_page.do", method = RequestMethod.GET)
	public String authError(Model model, HttpSession session) {
		UserInfoDTO iDto = (UserInfoDTO)session.getAttribute("user");
		log.info("UserCtrl_Login 권한이 미승인된 접근 : \t 접근자 {}",iDto.getUser_id());
		return "login/AuthError";
	}
	
	// 탈퇴한 회원일 때
	@RequestMapping(value="/invalidUser.do", method = RequestMethod.GET)
	public String invalidUser(Model model) {
		log.info("UserCtrl_Login 탈퇴한 회원이 로그인");
		model.addAttribute("msg", "탈퇴한 계정입니다.");
		return "login/invalidUser";
	}
	
	// 아이디 찾기로 이동 /idFindForm.do
	@RequestMapping(value="/idFindForm.do", method=RequestMethod.GET)
	public String idFindForm() {
		log.info("UserCtrl_Login 아이디 찾기 Form 이동 : \t {}", new Date());
		return "login/findID";
	}
	
	// 비밀번호 찾기로 이동
	@RequestMapping(value="/pwFindForm.do", method=RequestMethod.GET)
	public String pwFindForm() {
		log.info("UserCtrl_Login 비밀번호 찾기 Form 이동 : \t {}", new Date());
		return "login/findPW";
	}
	
	// 아이디 중복검사창 띄우기
	@RequestMapping(value="/idChkForm.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String idChkForm() {
		log.info("UserCtrl_Login 아이디 중복 체크 화면 : \t {}",new Date());
		return "login/idChk";
	}
	
	/**
	 * 회원가입을를 한 회원에게 이메일 보내기
	 * @param user_id 사용자 id
	 * @param title
	 * @param content
	 * @return
	 */
	private boolean sendEmailSignUpAlert(String user_email, String title, String content) {
		boolean isc = false;
		
		log.info("UserCtrl_Login 이메일 전송 시도 : \t {} : {}", user_email, title);
		
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom("ormm3377@gmail.com");
			messageHelper.setTo(user_email);
			messageHelper.setSubject(title);
			messageHelper.setText(content, true);

			mailSender.send(message);
			isc = true;
			
			log.info("UserCtrl_Login 이메일 전송 성공 : \t {} : {} : {}", user_email, title, content);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return isc;
	}

}
