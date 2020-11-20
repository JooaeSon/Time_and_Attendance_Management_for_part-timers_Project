package com.min.sc.user.ctrl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.model.IService_User;
import com.min.sc.user.sms.Coolsms;

@Controller
public class UserCtrl_SMS {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_User service;
	
	private String apiKey = "NCS5LSP0ITSKA7HZ";
	private String apiSecret = "72K2C4XRAUZFTCQEUUCXZB7BM4QELLZ2";
	
	// 생성된 인증번호를 담을 String
	private String code = null;
	
	/**
	 * 임의의 인증번호 6자리 숫자를 랜덤으로 생성해 <br> 
	 * String으로 반환하는 메소드
	 * @return String 인증번호 6자리
	 */
	private String makeCode() {
		int rand = (int) (Math.random() * 899999) + 100000;
		String key = Integer.toString(rand);
		return key;
	}
	
	/**
	 * sms 인증 화면 띄우기
	 */
	@RequestMapping(value="/phoneChkForm.do", method = RequestMethod.GET)
	public String smsPage() {
		log.info("Welcome sms Page : \t {}",new Date());
		return "login/smsChk";
	}
	
	/**
	 * Ajax 처리로 입력한 번호로 인증번호 6자리를 전송하는 메소드
	 * @param String 입력받은 핸드폰 번호
	 * @return String 문자 전송 결과 : 성공-true/실패-false
	 */
	@ResponseBody
	@RequestMapping(value="/sendSms.do", method = RequestMethod.POST)
	public Map<String, String> smsSend(String user_phone) {
		log.info("UserCtrl_SMS 메세지 전송 시도 : \t {}",user_phone);
		
		Map<String, String> map = new HashMap<String, String>();
		
		UserInfoDTO dto = service.duplChkPhone(user_phone);
		if(dto != null) {
			map.put("available","false");
		}else {
			map.put("available","true");
			
			Coolsms cool = new Coolsms(apiKey, apiSecret);
			
			this.code = makeCode();
			
			// 전송할 문자에 대한 정보를 HashMap으로 담음
			HashMap<String, String> set = new HashMap<String, String>();
			// 수신번호.  - 없이 붙여서 입력 ( ex> 01012340000 ) 본인이 발신번호로 등록한 번호여야한다.
	        set.put("from", "01048990859"); 
	        // 발신번호, jsp에서 전송한 발신번호를 받아 map에 저장한다.
	        set.put("to", user_phone.trim()); 
	        // 문자내용, jsp에서 전송한 문자내용을 받아 map에 저장한다.
	        set.put("text", "SC 홈페이지 SMS 인증 번호는 ["+code+"]입니다. 정확히 입력해 주세요."); 
	        // 문자 타입
	        set.put("type", "sms"); 
			
	        System.out.println("문자 정보 확인해볼까?"+set); 
	        
	        // 메세지를 전송하고 전송 결과를 JSONObject로 반환하는 send 메소드
	        JSONObject result = cool.send(set);
			
	        if ((boolean)result.get("status") == true) {
	            // 메시지 보내기 성공 및 전송결과 출력
	        	log.info("UserCtrl_SMS 메세지 전송 성공 : \t {}",set);
	            System.out.println("결과 코드 : "+result.get("result_code")+"/ 결과 메세지 : "+result.get("result_message")); 
				map.put("isc","true"); 
	          } else {
	            // 메시지 보내기 실패
	        	log.info("UserCtrl_SMS 메세지 전송 실패 : \t {}",set);
	            System.out.println("REST API 에러코드 : "+result.get("code")+" / 에러 메세지 : "+result.get("message")); // REST API 에러코드
	            map.put("isc","false"); 
	          }
		}
		
		return map;
	}
	
	/**
	 * 비밀번호 찾기 정보 인증을 위한 인증번호 메세지 전송 Ajax 처리
	 * @param user_id 해당 사용자의 id
	 * @param phone 입력받은 핸드폰번호
	 * @return  Map<String, String> 
	 * <br>: map.match > 해당 사용자의 핸드폰번호가 일치시 "true" / 미일치시 "false" 
	 * <br>: map.isc > 해당 사용자에게 문자가 정상적으로 전송시 "true" / 실패시 "false" 
	 */
	@ResponseBody
	@RequestMapping(value="/sendSmsForPw.do",method=RequestMethod.POST)
	public Map<String, String> sendSmsForPw(String user_id, String phone){
		log.info("UserCtrl_SMS 비밀번호 찾기 정보 인증을 위한 메세지 전송 : \t {} / {}",user_id,phone);
		
		Map<String, String> map = new HashMap<String, String>();
		
		UserInfoDTO dto = service.loginInfo(user_id);
		if(phone.trim().equals(dto.getUser_phone())) { // 해당 회원의 핸드폰번호 정보가 일치
			map.put("match", "true");
			
			Coolsms cool = new Coolsms(apiKey, apiSecret);
			
			this.code = makeCode();
			
			HashMap<String, String> set = new HashMap<String, String>();
	        set.put("from", "01048990859"); 
	        set.put("to", phone.trim()); 
	        set.put("text", "SC 홈페이지 SMS 인증 번호는 ["+code+"]입니다. 정확히 입력해 주세요."); 
	        set.put("type", "sms"); 
			
	        System.out.println("문자 정보 확인해볼까?"+set); 
	        
	        // 메세지를 전송하고 전송 결과를 JSONObject로 반환하는 send 메소드
	        JSONObject result = cool.send(set);
			
	        if ((boolean)result.get("status") == true) {
	        	log.info("UserCtrl_SMS 메세지 전송 성공 : \t {}",set);
	            System.out.println("결과 코드 : "+result.get("result_code")+"/ 결과 메세지 : "+result.get("result_message")); 
				map.put("isc", "true");
	          } else {
	        	log.info("UserCtrl_SMS 메세지 전송 실패 : \t {}",set);
	            System.out.println("REST API 에러코드 : "+result.get("code")+" / 에러 메세지 : "+result.get("message")); // REST API 에러코드
	            map.put("isc", "false");
	          }
	        
		}else { // 등록된 핸드폰이 아닌데 문자를 왜보내주니?
			map.put("match", "false");
		}
	
		return map;
	}
	
	/**
	 * Ajax처리로 입력받은 인증번호를 비교하여 결과를 반환하는 메소드
	 * @param String 입력받은 인증번호
	 * @return String 인증 성공 여부 : 성공-ok/실패-no
	 */
	@ResponseBody
	@RequestMapping(value="/smsCheck.do", method = RequestMethod.POST)
	public String smsCheck(String sms) {
		String chkCode = this.code;
		log.info("UserCtrl_SMS 문자인증 smsCheck.do \n");
		if(sms.equals(chkCode)) { 
			log.info("UserCtrl_SMS 문자 인증성공 - \t 인증번호 : {} / 입력값 : {}",chkCode,sms);
			return "ok"; 
		}else { 
			log.info("UserCtrl_SMS 문자 인증실패 - \t 인증번호 : {} / 입력값 : {}",chkCode,sms);
			return "no"; 
		}
	}

}
