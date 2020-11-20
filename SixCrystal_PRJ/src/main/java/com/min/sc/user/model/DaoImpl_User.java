package com.min.sc.user.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;

@Repository
public class DaoImpl_User implements IDao_User {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SqlSessionTemplate session;
	
	private final String NS = "com.min.sc.user.";
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean joinLogin(UserLoginDTO dto) {
		log.info("DaoImpl_User joinLogin 회원가입 (로그인정보) : {}",dto);
		String enPW = passwordEncoder.encode(dto.getUser_password());
		dto.setUser_password(enPW);
		log.info("DaoImpl_User joinLogin 회원가입 pw 암호화 (로그인정보) : {}",dto);
		return (session.insert(NS+"joinLogin", dto)>0)?true:false;
	}

	@Override
	public boolean joinInfoEmployer(UserInfoDTO dto) {
		log.info("DaoImpl_User joinLogin 고용주 회원가입 (개인정보) : {}",dto);
		return (session.insert(NS+"joinInfoEmployer", dto)>0)?true:false;
	}

	@Override
	public boolean joinInfoEmployee(UserInfoDTO dto) {
		log.info("DaoImpl_User joinLogin 일반직원 회원가입 (개인정보) : {}",dto);
		return (session.insert(NS+"joinInfoEmployee", dto)>0)?true:false;
	}


	@Override
	public String idChk(String user_id) {
		log.info("DaoImpl_User idChk 아이디 중복체크 : {}",user_id);
		return session.selectOne(NS+"idChk", user_id);
	}
	
	public UserInfoDTO duplChkEmail(String user_email) {
		log.info("DaoImpl_User duplChk 이메일 중복체크 : {}",user_email);
		return session.selectOne(NS+"duplChkEmail", user_email);
	}
	
	public UserInfoDTO duplChkPhone(String user_phone) {
		log.info("DaoImpl_User duplChk 휴대전화 중복체크 : {}",user_phone);
		return session.selectOne(NS+"duplChkPhone", user_phone);
	}

	@Override
	public UserLoginDTO login(String user_id) {
		log.info("DaoImpl_User login 로그인(시큐리티 사용) : {}",user_id);
		return session.selectOne(NS+"login", user_id);
	}

	@Override
	public boolean loginUpdate(String user_id) {
		log.info("DaoImpl_User login 로그인(마지막 로그인 날짜 업데이트) : {}",user_id);
		return (session.update(NS+"loginUpdate", user_id)>0)?true:false;
	}

	@Override
	public UserInfoDTO loginInfo(String user_id) {
		log.info("DaoImpl_User login 로그인(세션 정보 가져오기) : {}",user_id);
		return session.selectOne(NS+"loginInfo", user_id);
	}

	@Override
	public String findID(Map<String, String> map) {
		log.info("DaoImpl_User findID 아이디 찾기 : {}",map);
		return session.selectOne(NS+"findID", map);
	}
	
	@Override
	public String getIdForFindID(Map<String, String> map) {
		log.info("DaoImpl_User getIdForFindID 이메일/핸드폰으로 아이디 가져오기 : {}",map);
		return session.selectOne(NS+"getIdForFindID", map);
	}

	@Override
	public UserInfoDTO selectMyInfo(String user_id) {
		log.info("DaoImpl_User selectMyInfo 나의 개인정보 조회 : {}",user_id);
		return session.selectOne(NS+"selectMyInfo", user_id);
	}

	@Override
	public boolean modifyUserInfo(UserInfoDTO dto) {
		log.info("DaoImpl_User modifyUserInfo 일반정보 수정 : {}",dto);
		return (session.update(NS+"modifyUserInfo", dto)>0)?true:false;
	}

	@Override
	public String matchInfo(Map<String, String> map) {
		log.info("DaoImpl_User matchInfo 비밀번호 찾기(정보 일치 확인) : {}",map);
		return session.selectOne(NS+"matchInfo", map);
	}

	@Override
	public boolean modifyPw(Map<String, String> map) {
		log.info("DaoImpl_User modifyPw 비밀번호 수정 : {}",map);
		return (session.update(NS+"modifyPw", map)>0)?true:false;
	}

	@Override
	public boolean wdUser(Map<String, List<String>> map) {
		log.info("DaoImpl_User wdUser 회원탈퇴/강제탈퇴(회원 상태 변경) : {}",map);
		return (session.update(NS+"wdUser", map)>0)?true:false;
	}
	
	@Override
	public boolean removeCookies(Map<String, List<String>> map) {
		log.info("DaoImpl_User removeCookies 회원탈퇴시 자동로그인 쿠키값 삭제 : {}",map);
		return (session.delete(NS+"removeCookies", map)>0)?true:false;
	}

	@Override
	public List<String> chkDateForEmail() {
		log.info("DaoImpl_User chkDateForEmail 강제탈퇴 한달 전 이메일 보낼 대상 찾기 : {}", new Date());
		return session.selectList(NS+"chkDateForEmail");
	}

	@Override
	public List<UserInfoDTO> getUserList(Map<String, String> map) {
		log.info("DaoImpl_User getUserList 회원 전체조회/상세조회(user_id)/유형별 조회(user_type) : {}",map);
		return session.selectList(NS+"getUserList", map);
	}

	@Override
	public List<String> chkLastLogin() {
		log.info("DaoImpl_User chkLastLogin 마지막 로그인 날짜 체크 : {}", new Date());
		return session.selectList(NS+"chkLastLogin");
	}

	@Override
	public List<String> chkDelDate() {
		log.info("DaoImpl_User chkDelDate 탈퇴 날짜 체크 : {}", new Date());
		return session.selectList(NS+"chkDelDate");
	}

	@Override
	public boolean deleteUserInfo(Map<String, List<String>> map) {
		log.info("DaoImpl_User deleteUserInfo 회원 영구 삭제(개인 정보) : {}",map);
		return (session.delete(NS+"deleteUserInfo", map)>0)?true:false;
	}

	@Override
	public boolean deleteUserLogin(Map<String, List<String>> map) {
		log.info("DaoImpl_User deleteUserLogin 회원 영구 삭제(로그인 정보) : {}",map);
		return (session.delete(NS+"deleteUserLogin", map)>0)?true:false;
	}

	@Override
	public UserLoginDTO getLoginUser(String user_id) {
		log.info("DaoImpl_User getLoginUser 회원 로그인 정보 가져오기 : {}",user_id);
		return session.selectOne(NS+"getLoginUser", user_id);
	}

	@Override
	public List<WorkSpaceDTO> wsDetailSelect(Map<String, String> map){
		log.info("DaoImpl_User wsDetailSelect 사업장 상세정보 조회 : {}", map);
		return session.selectList(NS+"wsDetailSelect",map);
	}

}
