package com.min.sc.wn.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.min.sc.comm.dtos.BoardFileDTO;
import com.min.sc.comm.dtos.HitDTO;
import com.min.sc.comm.dtos.PagingDTO;
import com.min.sc.wn.dtos.WNBoardDTO;



public interface IService_Wn {

	/**
	 * 전체 게시글을 가져온다
	 * @param wn_seq
	 * @return List<WNBoardDTO> dto
	 */
	public List<WNBoardDTO> wnBoardSelect(String wn_seq);	
	
	
	/**
	 * 게시글을 입력한다.
	 * @param dto
	 * @return 입력 성공 true / 입력 실패 false
	 */
	public boolean wnBoardInsert(WNBoardDTO dto);
	
	
	/**
	 * 게시글을 수정한다.
	 * @param dto
	 * @return 수정 성공 true / 수정 실패 false
	 */
	public boolean wnBoardUpdate(WNBoardDTO dto);
	
	/**
	 * 게시글을 삭제한다.
	 * @param wn_seq
	 * @return 삭제 성공 true / 삭제 실패 false
	 */
	public boolean wnBoardDelete(int wn_seq);
	
	
	/**
	 * 게시글 조회수를 증가시킨다.
	 * @param wn_seq
	 * @return 수정 성공 true / 수정 실패 false
	 */
	public boolean wnBoardHitCount(int wn_seq);
	
	
	/**
	 * 
	 * @param pdto
	 * @return List<WNBoardDTO> 해당 범위에있는 게시글만을 가져온다.
	 */
	public List<WNBoardDTO> wnboardListPage(PagingDTO pdto);
	
	/**
	 * 게시글을 총 갯수를 가지고 온다
	 * @return int total
	 */
	public int wnboardListTotal();
	
	
	
	
}
