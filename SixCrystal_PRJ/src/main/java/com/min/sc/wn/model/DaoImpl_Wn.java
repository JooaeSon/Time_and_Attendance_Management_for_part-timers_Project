package com.min.sc.wn.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.sc.comm.dtos.PagingDTO;
import com.min.sc.wn.dtos.WNBoardDTO;

@Repository
public class DaoImpl_Wn implements IDao_Wn {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate service;
	
	private final String NS="com.min.sc.wn.";

	@Override
	public List<WNBoardDTO> wnBoardSelect(String wn_seq) {
		logger.info("DaoImpl_Wn wnBoardSelect WN_SEQ :"+wn_seq);
		return service.selectList(NS+"wnBoardSelect",wn_seq);
	}

	@Override
	public boolean wnBoardInsert(WNBoardDTO dto) {
		logger.info("DaoImpl_Wn wnBoardInsert dto :"+dto);
		int n = service.insert(NS+"wnBoardInsert",dto);
		return n>0?true:false;
	}

	@Override
	public boolean wnBoardUpdate(WNBoardDTO dto) {
		logger.info("DaoImpl_Wn wnBoardUpdate dto :"+dto);
		int n = service.update(NS+"wnBoardUpdate",dto);
		return n>0?true:false;
	}

	@Override
	public boolean wnBoardDelete(int wn_seq) {
		logger.info("DaoImpl_Wn wnBoardDelete wn_seq :"+wn_seq);
		int n = service.update(NS+"wnBoardDelete",wn_seq);
		return n>0?true:false;
	}

	@Override
	public boolean wnBoardHitCount(int wn_seq) {
		logger.info("DaoImpl_Wn wnBoardHitCount wn_seq :"+wn_seq);
		int n = service.update(NS+"wnBoardHitCount",wn_seq);		
		return n>0?true:false;
	}

	@Override
	public List<WNBoardDTO> wnboardListPage(PagingDTO pdto) {
		logger.info("DaoImpl_Wn wnboardListPage PagingDTO :"+pdto);
		
		return service.selectList(NS+"wnboardListPage", pdto);
	}

	@Override
	public int wnboardListTotal() {
		logger.info("DaoImpl_Wn wnboardListTotal");
		return service.selectOne(NS+"wnboardListTotal");
	}


	
	
}
