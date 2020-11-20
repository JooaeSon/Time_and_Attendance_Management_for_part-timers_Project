package com.min.sc.wn.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.sc.comm.dtos.PagingDTO;
import com.min.sc.wn.dtos.WNBoardDTO;

@Service
public class ServiceImpl_Wn implements IService_Wn {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDao_Wn dao;
	
	@Override
	public List<WNBoardDTO> wnBoardSelect(String wn_seq) {
		logger.info("ServiceImpl_Wn wnBoardSelect WN_SEQ :"+wn_seq);
		return dao.wnBoardSelect(wn_seq);
	}

	@Override
	public boolean wnBoardInsert(WNBoardDTO dto) {
		logger.info("ServiceImpl_Wn wnBoardInsert dto :"+dto);
		return dao.wnBoardInsert(dto);
	}

	@Override
	public boolean wnBoardUpdate(WNBoardDTO dto) {
		logger.info("ServiceImpl_Wn wnBoardUpdate dto :"+dto);
		return dao.wnBoardUpdate(dto);
	}

	@Override
	public boolean wnBoardDelete(int wn_seq) {
		logger.info("ServiceImpl_Wn wnBoardDelete wn_seq :"+wn_seq);
		return dao.wnBoardDelete(wn_seq);
	}

	@Override
	public boolean wnBoardHitCount(int wn_seq) {
		logger.info("ServiceImpl_Wn wnBoardHitCount wn_seq :"+wn_seq);
		return dao.wnBoardHitCount(wn_seq);
	}

	@Override
	public List<WNBoardDTO> wnboardListPage(PagingDTO pdto) {
		logger.info("ServiceImpl_Wn wnboardListPage PagingDTO :"+pdto);
		return dao.wnboardListPage(pdto);
	}

	@Override
	public int wnboardListTotal() {
		logger.info("ServiceImpl_Wn wnboardListTotal");
		return dao.wnboardListTotal();
	}


	
	

}
