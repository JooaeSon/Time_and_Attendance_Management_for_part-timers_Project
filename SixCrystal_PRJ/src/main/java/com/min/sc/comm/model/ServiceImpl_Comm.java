package com.min.sc.comm.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.sc.comm.dtos.BoardFileDTO;
import com.min.sc.comm.dtos.HitDTO;

@Service
public class ServiceImpl_Comm implements IService_Comm {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDao_Comm dao;
	

	@Override
	public List<BoardFileDTO> boardFileSelect(Map<String, Object> map) {
		logger.info("ServiceImpl_Comm boardFileSelect map :"+map);
		return dao.boardFileSelect(map);
	}

	@Override
	public boolean boardFileInsert(BoardFileDTO dto) {
		logger.info("ServiceImpl_Comm boardFileInsert BoardFileDTO :"+dto);
		return dao.boardFileInsert(dto);
	}

	@Override
	public boolean hitInsert(HitDTO dto) {
		logger.info("ServiceImpl_Comm hitInsert HitDTO :"+dto);
		return dao.hitInsert(dto);
	}

	@Override
	public List<HitDTO> hitSelect(Map<String, Object> map) {
		logger.info("ServiceImpl_Comm hitInsert map :"+map);
		return dao.hitSelect(map);
	}

	@Override
	public boolean hitUpdate(Map<String, Object> map) {
		logger.info("ServiceImpl_Comm hitUpdate map :"+map);
		return dao.hitUpdate(map);
	}

}
