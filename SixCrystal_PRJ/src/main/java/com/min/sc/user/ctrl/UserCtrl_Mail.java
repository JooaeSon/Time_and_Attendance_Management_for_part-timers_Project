package com.min.sc.user.ctrl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.model.IService_User;

@Controller
public class UserCtrl_Mail {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IService_User service;

	@Autowired
	private JavaMailSender mailSender;

	private String key; // 생성되는 인증키를 담음
	private String setFrom = "ormm3377@gmail.com"; // 발신자
	private String title = "SC 이메일 인증 코드 발송"; // 메일제목

	/**
	 * 랜덤한 대문자+숫자 조합의 12자리 인증키를 만들어내는 메소드
	 * 
	 * @return String 랜덤한 대문자+숫자 조합의 12자리 인증키
	 */
	private String makeKey() {
		String key = null;
		Random rnd = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 12; i++) {
			if (rnd.nextBoolean()) {
				buffer.append((char) (int) (rnd.nextInt(26) + 65)); // 랜덤한 대문자
			} else {
				buffer.append((rnd.nextInt(10)));
			}
		}
		key = buffer.toString();
		log.info("UserCtrl_Mail 이메일 인증 키 생성 : \t {}", key);
		return key;
	}

	/**
	 * 이메일 인증 form 으로 이동
	 * 
	 * @return
	 */
	@RequestMapping(value = "/emailChkForm.do", method = RequestMethod.GET)
	public String mail() {
		log.info("UserCtrl_Mail 이메일 인증 Form : \t {}", new Date());
		return "login/emailChk";
	}
	
	/**
	 * 메일 전송 Ajax 처리
	 * @param String user_email 화면에서 사용자에게 입력받은 이메일
	 * @return Map<String, String> map 
	 * <br> : available > 중복되지 않은 사용가능한 메일일 시 "true" / 사용 불가한 메일일 시 "false"
	 * <br> : isc > 메일 전송에 성공했을 시 "true" / 메일 전송 실패 시 "false" 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/sendEmail.do", method = RequestMethod.POST)
	public Map<String, String> mailSend(String user_email) throws IOException {
		log.info("UserCtrl_Mail 메일 전송 시도 : \t {}", user_email);

		Map<String, String> map = new HashMap<String, String>();

		UserInfoDTO dto = service.duplChkEmail(user_email);
		if (dto != null) {
			map.put("available", "false");
		} else {
			map.put("available", "true");

			this.key = makeKey();
			String content = "<div style='font-size:15px;'>SC 홈페이지 이메일 인증 코드는 [ " + key + " ] 입니다. 정확히 입력해 주세요.</div>";

			log.info("UserCtrl_Mail 이메일 전송 결과 : \t {} : {} : {}", user_email, title, content);
			// toEmail 받는사람 주소, title 메일 제목, content 메일 내용

			MimeMessage message = mailSender.createMimeMessage();

			try {
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom(setFrom);
				messageHelper.setTo(user_email);
				messageHelper.setSubject(title);
				messageHelper.setText(content, true);

				mailSender.send(message);

				map.put("isc", "true");
			} catch (MessagingException e) {
				map.put("isc", "false");
				e.printStackTrace();
			}
		}

		return map;
	}
	
	/**
	 * 비밀번호 찾기 기능을 위한 메일 전송 Ajax 처리
	 * @param String user_email 화면에서 사용자에게 입력받은 이메일
	 * @param String user_id 화면에서 사용자가 입력한 본인의 아이디
	 * @return Map<String, String> map 
	 * <br> : match > 해당 사용자의 이메일과 일치 시 "true" / 불일치 시 "false"
	 * <br> : isc > 메일 전송에 성공했을 시 "true" / 메일 전송 실패 시 "false" 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/sendEmailForPw.do", method = RequestMethod.POST)
	public Map<String, String> mailSendForPw(String email, String user_id)
			throws IOException {
		Map<String, String> map = new HashMap<String, String>();

		UserInfoDTO dto = service.loginInfo(user_id);
		if (email.trim().equals(dto.getUser_email())) {
			map.put("match", "true");

			this.key = makeKey();
			String content = "<div style='font-size:15px;'>SC 홈페이지 이메일 인증 코드는 [ " + key + " ] 입니다. 정확히 입력해 주세요.</div>";

			log.info("UserCtrl_Mail 비밀번호 찾기를 위한 이메일 전송 결과 : \t {} : {} : {}", email, title, content);

			MimeMessage message = mailSender.createMimeMessage();

			try {
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom(setFrom);
				messageHelper.setTo(email);
				messageHelper.setSubject(title);
				messageHelper.setText(content, true);

				mailSender.send(message);
				map.put("isc", "true");
			} catch (MessagingException e) {
				map.put("isc", "false");
				e.printStackTrace();
			}
		} else { // 정보가 다른데 메일을 왜보내니?
			log.info("UserCtrl_Mail 비밀번호 찾기를 시도했으나 이메일 정보 다름 : \n 사용자 : {} / 입력 이메일 : {} / 본인 이메일 : {}", user_id, email,
					dto.getUser_email());
			map.put("match", "false");
		}

		return map;
	}
	
	/**
	 * 이메일로 전송받은 인증키가 일치하는지 판단하는 Ajax처리
	 * @param emKey 화면에서 사용자에게 입력받은 인증키
	 * @return String 인증키 일치시 "true" / 불일치 시 "false"
	 */
	@ResponseBody
	@RequestMapping(value = "/emailCheck.do", method = RequestMethod.POST)
	public String emailCheck(String emKey) {
		String isc = "false";
		if (emKey.trim().equals(key)) {
			isc = "true";
		}
		return isc;
	}
	
}
