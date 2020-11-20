package com.min.sc.user.model;

import java.util.List;
import java.util.Map;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;

public interface IService_User {
	
	/**
	 * 회원가입
	 * @param UserLoginDTO 로그인 관련 DTO
	 * @param UserInfoDTO 개인정보 관련 DTO
	 * @return 성공 : true / 실패 : false
	 */
	public boolean join(UserLoginDTO lDto, UserInfoDTO iDto);
	
	/**
	 * 유저 로그인 정보(USERLoginDTO) 가져오기
	 * @param user_id
	 * @return
	 */
	public UserLoginDTO getLoginUser(String user_id);
	
	/**
	 * 아이디 중복검사
	 * @param user_id 중복검사할 아이디
	 * @return String user_id : UserLoginDTO 에서 찾은 id(관리자 아이디도 포함하기 위함)
	 */
	public String idChk(String user_id);
	
	/**
	 * 이메일 중복검사
	 * @param String user_email 이메일 
	 * @return UserInfoDTO 
	 */
	public UserInfoDTO duplChkEmail(String user_email);
	
	/**
	 * 휴대폰 중복검사
	 * @param String user_phone 휴대전화번호
	 * @return UserInfoDTO 
	 */
	public UserInfoDTO duplChkPhone(String user_phone);
	
	/**
	 * 로그인 (시큐리티 사용)
	 * @param String user_id
	 * @return UserLoginDTO 비밀번호 / auth 담음
	 */
	public UserLoginDTO login(String user_id);
	/**
	 * 로그인 (마지막 로그인 날짜 업데이트)
	 * @param String user_id
	 * @return 성공 : true / 실패 : false
	 */
	public boolean loginUpdate(String user_id);
	/**
	 * 로그인 (세션에 담을 정보 가져오기)
	 * @param String user_id
	 * @return UserInfoDTO
	 */
	public UserInfoDTO loginInfo(String user_id);
	/**
	 * 아이디 찾기
	 * @param Map<String, String> user_name && (user_email || user_phone)
	 * @return String id (5글자 이후는 글자수 만큼 *로 표시)
	 */
	public String findID(Map<String, String> map);
	/**
	 * 아이디 찾기 시 탈퇴회원인지 확인하기 위해 아이디를 가져오는 기능
	 * @param Map<String, String> map (user_email || user_phone)
	 * @return String id (실제 아이디)
	 */
	public String getIdForFindID(Map<String, String> map);
	/**
	 * 개인 정보 조회
	 * @param String user_id
	 * @return UserInfoDTO
	 */
	public UserInfoDTO selectMyInfo(String user_id);
	
	/**
	 * 일반 정보 수정
	 * @param UserInfoDTO (user_email,user_phone,user_name,user_address,user_birth,user_eagree,user_sagree)
	 * @return 성공 : true / 실패 : false
	 */
	public boolean modifyUserInfo(UserInfoDTO dto);
	
	/**
	 * 비밀번호 수정 <br> 
	 * 로그인한 상태에서 비밀번호 수정시 (로그인 메소드로 현재비밀번호 확인 login -> 비밀번호 수정 modifyPw)
	 * @param Map<String, String> user_id && (user_email || user_phone)
	 * @return String user_name
	 */
	public boolean modifyPW(Map<String, String> map); 
	/**
	 * 비밀번호 찾기 (정보일치 확인 matchInfo)
	 * @param Map<String, String> infoMap : user_id && (user_email || user_phone)
	 * @return 성공 : true / 실패 : false
	 */
	public boolean findPW(Map<String, String> infoMap);
	/**
	 * 회원 탈퇴 / 강제 탈퇴(다중)
	 * @param Map<String, List<String>> map의 key : idLists
	 * @return 성공 : true / 실패 : false
	 */
	public boolean wdUser(Map<String, List<String>> map);
	/**
	 * 회원 탈퇴시 자동로그인 쿠키를 삭제
	 * @param Map<String, List<String>> map의 key : idLists
	 * @return 성공 : true / 실패 : false
	 */
	public boolean removeCookies(Map<String, List<String>> map);
	/**
	 * 회원 강제 탈퇴 알림 이메일을 보낼 대상 찾기
	 * @return String[user_id]
	 */
	public List<String> chkDateForEmail();
	/**
	 * 회원 전체조회 / 상세조회 / 유형별(고용주,일반직원) 조회
	 * @param Map<String, String> map (user_id 혹은 user_type)
	 * @return
	 */
	public List<UserInfoDTO> getUserList(Map<String, String> map);
	
	/**
	 * 사업장 상세목록 조회
	 * @param ws_code 사업장코드
	 * @return WorkSpaceDTO
	 */
	public List<WorkSpaceDTO> wsDetailSelect(Map<String, String> map);
	
	/**
	 * 회원 자동 탈퇴를 위한 마지막 로그인날짜 체크
	 * @return String[user_id]
	 */
	public List<String> chkLastLogin();
	/**
	 * 회원 정보 영구 삭제를 위한 탈퇴 날짜 체크
	 * @return String[user_id]
	 */
	public List<String> chkDelDate();
	/**
	 * 회원 삭제 (deleteUserInfo,deleteUserLogin)
	 * @param Map<String, List<String>> user_id 리스트 / map의 key : idLists 
	 * @return 성공 : true / 실패 : false
	 */
	public boolean deleteUser(Map<String, List<String>> map);

}
