package com.min.sc.comm.model;

import java.util.List;
import java.util.Map;

import com.min.sc.comm.dtos.BoardFileDTO;
import com.min.sc.comm.dtos.HitDTO;

public interface IDao_Comm {
	
	/**
	 * 게시글에 해당되는 파일리스트를 가져온다
	 * @param map
	 * @return List<BoardFileDTO>
	 */
	public List<BoardFileDTO> boardFileSelect(Map<String, Object> map);
	
	
	/**
	 * 게시글에 해당되는 파일을 파일테이블에 입력한다
	 * @param dto
	 * @return 입력 성공 true / 실패 false
	 */
	public boolean boardFileInsert(BoardFileDTO dto);
	
	
	/**
	 * 게시글에 해당되는 조회수를 조회수 테이블에 입력한다.
	 * @param dto
	 * @return 입력 성공 true / 실패 false
	 */
	public boolean hitInsert(HitDTO dto);
	
	
	/**
	 * 게시글에 해당하는 조회수를 조회한다.
	 * @param map
	 * @return List<HitDTO>
	 */
	public List<HitDTO> hitSelect(Map<String, Object> map);
	
	
	/**
	 * 게시글해 해당하는 조회수의 아이디 목록을 업데이트한다.
	 * @param map
	 * @return 수정 성공 true/ 실패 false
	 */
	public boolean hitUpdate(Map<String, Object> map);


}
