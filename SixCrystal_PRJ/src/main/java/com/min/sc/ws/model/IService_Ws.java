package com.min.sc.ws.model;

import java.util.List;

import com.min.sc.ws.dtos.WorkSpaceDTO;

public interface IService_Ws {
	
	/**
	 * 고용주 회원이 소유한 사업장 조회
	 * 
	 * @param user_id
	 * @return ws_code
	 */
	public String wsErCodeSelect(String user_id);
	
	/**
	 * 일반 회원이 소속된 사업장 조회
	 * 
	 * @param user_id
	 * @return ws_code
	 */
	public List<String> wsEeCodeSelect(String user_id);
	
	/**
	 * 사업장 정보 조회
	 * 
	 * @param ws_code
	 * @return String ws_name String ws_loc String ws_num String ws_email String
	 *         ws_ssid String ws_vol
	 */
	public WorkSpaceDTO wsInfoSelect(String ws_code);
	
	/**
	 * 현재 날짜를 6자리로 조회 ex) 200605
	 * 
	 * @return String nowDate
	 */
	public String wsDateSelect();

	// wsDateSelect

	/**
	 * 사업장 정보 입력
	 * 
	 * @param lists
	 * @return insert 성공여부(true: 성공, false: 실패)
	 */
	public boolean wsInfoInsert(WorkSpaceDTO WSDto);


	/**
	 * 사업장 정보 수정
	 * 
	 * @param ws_code
	 */
	public boolean wsInfoUpdate(WorkSpaceDTO WSDto);
	
	/**
	 * 처리중인 삭제 요청 조회
	 * 
	 * @param ws_code
	 * @return ws_delflag
	 */
	public String wsReqSelect(String ws_code);

	/**
	 * 사업장 삭제 요청
	 * 
	 * @param ws_code
	 * @return update 성공여부(true: 성공, false: 실패)
	 */
	public boolean wsDelReqUpdate(String ws_code);

	/**
	 * 관리자 삭제 요청 조회
	 * 
	 * @return "String ws_code String user_id"
	 */
	public List<WorkSpaceDTO> wsDelReqSelect();

	/**
	 * 사업장 삭제(삭제 요청 승인)
	 * 
	 * @param ws_code
	 * @return update 성공여부(true: 성공, false: 실패)
	 */
	public boolean wsDelReqYUpdate(String ws_code);

	/**
	 * 사업장 삭제 요청 거절
	 * 
	 * @param ws_code
	 * @return update 성공여부(true: 성공, false: 실패)
	 */
	public boolean wsDelReqNUpdate(String ws_code);
}
