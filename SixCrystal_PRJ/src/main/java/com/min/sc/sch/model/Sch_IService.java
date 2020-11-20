package com.min.sc.sch.model;

import java.util.List;
import java.util.Map;

import com.min.sc.sch.dtos.SchDTO;

public interface Sch_IService {
	/**
	 * 아이디 중복검사
	 * @param user_id 중복검사할 아이디
	 * @return String user_id : UserLoginDTO 에서 찾은 id(관리자 아이디도 포함하기 위함)
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
