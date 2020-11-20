package com.min.sc.user.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;

@Service
public class ServiceImpl_User implements IService_User {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDao_User dao;
	
	@Override
	@Transactional
	public boolean join(UserLoginDTO lDto, UserInfoDTO iDto) {
		log.info("ServiceImpl_User join 회원가입 : \n 로그인정보 > {}",lDto);
		boolean iscL = dao.joinLogin(lDto);
		if(iDto.getUser_type().equals("employer")) {
			log.info("ServiceImpl_User 고용주 회원가입 : 개인정보 > {} ",iDto);
			boolean iscI = dao.joinInfoEmployer(iDto);
			return (iscL&&iscI)?true:false;
		}else {
			log.info("ServiceImpl_User 일반직원 회원가입 : 개인정보 > {} ",iDto);
			boolean iscI = dao.joinInfoEmployee(iDto);
			return (iscL&&iscI)?true:false;
		}
	}

	@Override
	public String idChk(String user_id) {
		log.info("ServiceImpl_User idChk 아이디 중복체크 : 중복체크값 > {} ",user_id);
		return dao.idChk(user_id);
	}
	
	@Override
	public UserInfoDTO duplChkEmail(String user_email) {
		log.info("ServiceImpl_User duplChk 이메일 중복체크 : {}",user_email);
		return dao.duplChkEmail(user_email);
	}
	
	@Override
	public UserInfoDTO duplChkPhone(String user_phone) {
		log.info("ServiceImpl_User duplChk 휴대전화 중복체크 : {}",user_phone);
		return dao.duplChkPhone(user_phone);
	}

	@Override
	public UserLoginDTO login(String user_id) {
		log.info("ServiceImpl_User login 로그인 : 사용자 아이디 > {} ",user_id);
		return dao.login(user_id);
	}

	@Override
	public boolean loginUpdate(String user_id) {
		log.info("ServiceImpl_User loginUpdate 마지막 로그인 날짜 업데이트 : 사용자 아이디 > {} ",user_id);
		return dao.loginUpdate(user_id);
	}

	@Override
	public UserInfoDTO loginInfo(String user_id) {
		log.info("ServiceImpl_User loginInfo 세션에 담을 정보 : 사용자 아이디 > {} ",user_id);
		return dao.loginInfo(user_id);
	}

	@Override
	public String findID(Map<String, String> map) {
		log.info("ServiceImpl_User findID 사용자 아이디 찾기 정보 > {} : {} ", map.get("user_name"), new Date());
		return dao.findID(map);
	}
	
	@Override
	public String getIdForFindID(Map<String, String> map) {
		log.info("ServiceImpl_User findID 사용자 아이디 찾기 탈퇴회원 판별을 위한 이메일/핸드폰으로 아이디가져오기 > {}",map);
		return dao.getIdForFindID(map);
	}

	@Override
	public UserInfoDTO selectMyInfo(String user_id) {
		log.info("ServiceImpl_User selectMyInfo 개인정보 조회 : 사용자 아이디 > {} ",user_id);
		return dao.selectMyInfo(user_id);
	}

	@Override
	public boolean modifyUserInfo(UserInfoDTO dto) {
		log.info("ServiceImpl_User modifyUserInfo 세션에 담을 정보 : 수정 정보 > {} ",dto);
		return dao.modifyUserInfo(dto);
	}

	@Override
	public boolean modifyPW(Map<String, String> map) {
		log.info("ServiceImpl_User modifyPW 비밀번호 수정 : 사용자 아이디 > {} / 비밀번호 > {} ",map.get("user_id"),map.get("user_password"));
		return dao.modifyPw(map);
	}

	@Override
	public boolean findPW(Map<String, String> infoMap) {
		log.info("ServiceImpl_User findPW 비밀번호 찾기 : 사용자 아이디 > {} ",infoMap.get("user_id"));
		String idFind = dao.matchInfo(infoMap);
		boolean isc = false;
		if(idFind != null) {
			isc = true;
		}
		return isc;
	}

	@Override
	public boolean wdUser(Map<String, List<String>> map) {
		log.info("ServiceImpl_User wdUser 사용자 탈퇴 : 사용자 아이디 > {} ",map.get("idLists"));
		return dao.wdUser(map);
	}
	
	@Override
	public boolean removeCookies(Map<String, List<String>> map) {
		log.info("ServiceImpl_User removeCookies 사용자 탈퇴시 자동로그인 쿠키값 삭제 > {} ",map.get("idLists"));
		return dao.removeCookies(map);
	}

	@Override
	public List<String> chkDateForEmail() {
		log.info("ServiceImpl_User chkDateForEmail 강제탈퇴 1달전 메일보낼 대상 가져오기 : 실행일 > {} ",new Date());
		return dao.chkDateForEmail();
	}

	@Override
	public List<UserInfoDTO> getUserList(Map<String, String> map) {
		log.info("ServiceImpl_User getUserList 전체회원조회/상세조회/유형별조회 : 입력값존재? > {} ",map);
		return dao.getUserList(map);
	}

	@Override
	public List<String> chkLastLogin() {
		log.info("ServiceImpl_User chkLastLogin 강제탈퇴 대상 가져오기 : 실행일 > {} ",new Date());
		return dao.chkLastLogin();
	}

	@Override
	public List<String> chkDelDate() {
		log.info("ServiceImpl_User chkDelDate 회원정보 영구삭제 대상 가져오기 : 실행일 > {} ",new Date());
		return dao.chkDelDate();
	}

	@Override
	@Transactional
	public boolean deleteUser(Map<String, List<String>> map) {
		log.info("ServiceImpl_User deleteUser 회원정보 영구삭제 : \t {}",map);
		boolean delInfo = dao.deleteUserInfo(map);
		boolean delLogin = dao.deleteUserLogin(map);
		return delInfo&&delLogin;
	}

	@Override
	public UserLoginDTO getLoginUser(String user_id) {
		log.info("ServiceImpl_User getLoginUser 사용자 로그인정보 가져오기 > {} ",user_id);
		return dao.getLoginUser(user_id);
	}

	@Override
	public List<WorkSpaceDTO> wsDetailSelect(Map<String, String> map) {
		log.info("ServiceImpl_User wsDetailSelect 사업장 상세정보 조회 > {} ",map);
		return dao.wsDetailSelect(map);
	}

}
