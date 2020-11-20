package com.min.sc.comm.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.sc.comm.dtos.BoardFileDTO;
import com.min.sc.comm.dtos.HitDTO;

@Repository
public class DaoImpl_Comm implements IDao_Comm {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate service;
	
	private final String NS ="com.min.sc.comm.";

	@Override
	public List<BoardFileDTO> boardFileSelect(Map<String, Object> map) {
		logger.info("DaoImpl_Comm boardFileSelect map :"+map);
		return service.selectList(NS+"boardFileSelect", map);
	}

	@Override
	public boolean boardFileInsert(BoardFileDTO dto) {
		logger.info("DaoImpl_Comm boardFileSelect BoardFileDTO :"+dto);
		int n = service.insert(NS+"boardFileInsert", dto);
		return n>0?true:false;
	}

	@Override
	public boolean hitInsert(HitDTO dto) {
		logger.info("DaoImpl_Comm hitInsert HitDTO :"+dto);
		int n = service.insert(NS+"hitInsert", dto);
		return n>0?true:false;
	}

	@Override
	public List<HitDTO> hitSelect(Map<String, Object> map) {
		logger.info("DaoImpl_Comm hitSelect map :"+map);
		return service.selectList(NS+"hitSelect", map);
	}

	@Override
	public boolean hitUpdate(Map<String, Object> map) {
		logger.info("DaoImpl_Comm hitUpdate map :"+map);
		int n = service.update(NS+"hitUpdate", map);
		return n>0?true:false;
	}

}
