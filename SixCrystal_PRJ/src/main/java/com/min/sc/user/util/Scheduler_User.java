package com.min.sc.user.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSender;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.model.IService_User;

@Component
public class Scheduler_User {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_User service;
	
	@Autowired
	private JavaMailSender mailSender;
	
//	@Scheduled(cron = "0/5 * * * * *") 
//	public void run() {
//		log.info("Scheduler_User CRON Scheduler 테스트 : \t {}",new Date());
//	}
	
	/**
	 * 강제탈퇴 대상 회원에게 이메일 보내기 스케줄러
	 * 매월 매일 오전 8시에 실행 0 0 8 * * ? 
	 */
	@Scheduled(cron = "0 0 08 * * ? ")
	public void sendEmailForUser(){
		log.info("Scheduler_User 강제탈퇴 대상 회원에게 이메일 보내기 스케줄러 : \t {}",new Date());
		
		List<String> listE = service.chkDateForEmail();
		
		if(!listE.isEmpty()) {
			log.info("Scheduler_User 강제탈퇴 1개월전 회원 존재 : \t 대상 > {}", listE);
			for (String user_id : listE) {
				UserInfoDTO dto = service.selectMyInfo(user_id);
				
				String setFrom = "ormm3377@gmail.com";
				String toEmail = dto.getUser_email();
				String title = "SC 홈페이지 강제 탈퇴 예정 안내";
				String content = "<div style='font-size:15px; text-align:center; border:1px solid #ccc; padding:10px;'>"
						+ "SC 홈페이지에서 "+user_id+" 회원님의 강제 탈퇴 예정을 안내드립니다.<br>"
						+ "<b style='color:red; text-decoration:underline;'>1년 이상 미로그인 회원들의 경우 탈퇴 계정으로 분리되어 회원등록이 삭제됨</b>을 알려드립니다.<br>"
						+ "회원계정 및 정보 삭제를 원하지 않으신다면 <b style='text-decoration:underline;'> 해당 이메일 수신일로부터 한달 이내</b>로<br>"
						+ "<a href='http://localhost:8088/SixCrystal_PRJ/' style='text-decoration:none;'>사이트</a> 로그인을 통하여 <b>계정 활성화</b>를 부탁드립니다.<br> 감사합니다.</div>";

				log.info("Scheduler_User 이메일 전송 시도 : \t {} : {} : {}", toEmail, title, content);
				// user_email 받는사람 주소, title 메일 제목, content 메일 내용

				MimeMessage message = mailSender.createMimeMessage();

				try {
					MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
					messageHelper.setFrom(setFrom);
					messageHelper.setTo(toEmail);
					messageHelper.setSubject(title);
					messageHelper.setText(content, true);

					mailSender.send(message);
					log.info("Scheduler_User 이메일 전송 완료 : \t 수신자 > {} : {} ",user_id, toEmail);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			} // for문 끝
			log.info("Scheduler_User 강제탈퇴 1개월전 회원에게 이메일 보내기 완료 : \t  {}", new Date());
		}else {
			log.info("Scheduler_User 강제탈퇴 1개월전 회원이 없습니다. : \t {}",new Date());
		}
	}
	
	/**
	 * 마지막 접속으로부터 1년이 지난 회원 강제 탈퇴 스케줄러
	 * 매월 매일 오전 9시에 실행
	 */
	@Scheduled(cron = "0 0 9 * * ? ")
	public void kickOutUser() {
		log.info("Scheduler_User 마지막 접속으로부터 1년이 지난 회원 강제 탈퇴 스케줄러 : \t {}",new Date());
		
		List<String> listK = service.chkLastLogin();
		
		if(!listK.isEmpty()) {
			log.info("Scheduler_User 강제탈퇴 대상 회원 존재 : \t 대상 > {}",listK);
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("idLists", listK);
			boolean iscT = service.wdUser(map);
			boolean iscD = service.removeCookies(map);
			if(iscT) {
				log.info("Scheduler_User 강제탈퇴 대상 회원 탈퇴처리 완료 : \t {}",new Date());
				if(iscD) {
					log.info("Scheduler_User 강제탈퇴 대상 회원 자동로그인 쿠키 삭제 완료 : \t {}",new Date());
				}else {
					log.info("Scheduler_User 강제탈퇴 대상 회원 자동로그인 쿠키 삭제 실패 : \t {}",new Date());
				}
				sendMailAlert(listK);
			}else {
				log.info("Scheduler_User 강제탈퇴 대상 회원 탈퇴처리 실패 : \t {}",new Date());
			}
		}else {
			log.info("Scheduler_User 강제탈퇴 대상 회원이 없습니다. : \t {}",new Date());
		}
	}
	
	/**
	 * 탈퇴 이후 6개월이 지난 회원의 정보 영구 삭제 스케줄러
	 * 매월 1일 오후 1시에 실행
	 */
	@Scheduled(cron = "0 0 13 1 * ? ")
	public void deleteUser() {
		log.info("Scheduler_User 탈퇴 후 6개월이 지난 회원의 정보 영구 삭제 스케줄러 : \t {}",new Date());
		
		List<String> listD = service.chkDelDate();
		
		if(!listD.isEmpty()) {
			log.info("Scheduler_User 정보 영구 삭제 대상 회원 존재 : \t 대상 > {}",listD);
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("idLists", listD);
			boolean isc = service.deleteUser(map);
			if(isc) {
				log.info("Scheduler_User 정보 영구 삭제 대상 회원 정보삭제 처리 완료 : \t {}",new Date());
			}else {
				log.info("Scheduler_User 정보 영구 삭제 대상 회원 정보삭제 처리 실패 : \t {}",new Date());
			}
		}else {
			log.info("Scheduler_User 정보 영구 삭제 대상 회원이 없습니다. : \t {}",new Date());
		}
	}
	
	/**
	 * 탈퇴처리된 회원들에게 메일을 보내기
	 * @param list 탈퇴 처리된 회원의 아이디 목록
	 * @return boolean 성공시 ture / 실패시 false
	 */
	public boolean sendMailAlert(List<String> list) {
		boolean isc = false;
		log.info("Scheduler_User 탈퇴 처리된 회원에게 메일 보내기 : \t대상 > {}",list);
		
		for (String user_id : list) {
			UserInfoDTO dto = service.selectMyInfo(user_id);
			String setFrom = "ormm3377@gmail.com";
			String toEmail = dto.getUser_email();
			String title = "SC 홈페이지 강제 탈퇴 안내";
			String content = "<div style='font-size:15px; text-align:center; border:1px solid #ccc; padding:10px;'>"
					+ "<a href='http://localhost:8088/SixCrystal_PRJ/' style='text-decoration:none;'>SC 홈페이지</a>에서 "+user_id+" 회원님의 강제 탈퇴를 안내드립니다.<br>"
					+ "회원님의 계정은 1년 이상 미로그인한 계정으로,<br>"
					+ "<b style='color:red; text-decoration:underline;'>본사의 운영 방침에 따라 탈퇴 계정으로 분류되어 회원등록이 삭제됨</b>을 알려드립니다.<br>"
					+ "약관에 안내드린 바와 같이 서비스 재이용을 원하신다면 재가입을 해주셔야 합니다.<br>"
					+ "문의사항은 <p style='text-decoration:underline;>ormm3377@gmail.com</p>로 보내주시면 빠른 시일 내로 답변해 드리겠습니다."
					+ "<br><br> 감사합니다.</div>";

			log.info("Scheduler_User 탈퇴 처리된 계정에 이메일 전송 시도 : \t {} : {} : {}", toEmail, title, content);

			MimeMessage message = mailSender.createMimeMessage();

			try {
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom(setFrom);
				messageHelper.setTo(toEmail);
				messageHelper.setSubject(title);
				messageHelper.setText(content, true);

				mailSender.send(message);
				log.info("Scheduler_User 탈퇴 처리된 계정에 이메일 전송 완료 : \t 수신자 > {} : {} ",user_id, toEmail);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			isc = true;
		}
		return isc;
	}

	
}
