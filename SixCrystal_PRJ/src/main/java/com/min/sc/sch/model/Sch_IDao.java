package com.min.sc.sch.model;

import java.util.List;
import java.util.Map;

import com.min.sc.sch.dtos.SchDTO;

public interface Sch_IDao {
	/**
	 * 회원가입 (로그인 테이블)
	 * @param UserLoginDTO 로그인 관련 DTO
	 * @return 성공 : true / 실패 : false
	 */
	public String selectSchInfo(SchDTO dto);
	public boolean insertSchInfo(SchDTO dto);
	public String updateSchInfo(SchDTO dto);
	public boolean updateAccess(SchDTO dto);
	public String selectAccess(SchDTO dto);
	public String deleteSchInfo(SchDTO dto);
	public List<SchDTO> selectSchAll();
	public boolean cornUpdate(Map<String, Object> map);
}
