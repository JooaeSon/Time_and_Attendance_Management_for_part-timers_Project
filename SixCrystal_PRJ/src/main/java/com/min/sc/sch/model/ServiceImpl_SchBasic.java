package com.min.sc.sch.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.sc.sch.dtos.SchBasicDTO;


@Service
public class ServiceImpl_SchBasic implements IService_SchBasic {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private IDao_SchBasic dao;
	
	@Override
	public boolean schBasicInsert(SchBasicDTO sbdto) {
		logger.info("ServiceImpl_SchBasic schBasicInsert : dto="+sbdto);
		return dao.schBasicInsert(sbdto);
	}

	@Override
	public boolean schBasicUpdate(int seq, String schbasic_json) {
		logger.info("ServiceImpl_SchBasic schBasicUpdate : seq="+seq+"schbasic_json"+schbasic_json);
		return dao.schBasicUpdate(seq, schbasic_json);
	}

	@Override
	public SchBasicDTO schBasicSelect(String wscode, Date record) {
		logger.info("ServiceImpl_SchBasic schBasicSelect : WS_CODE="+wscode+"SCHBASIC_RECORD"+record);
		return dao.schBasicSelect(wscode, record);
	}

	@Override
	public List<SchBasicDTO> schBasicListSelect(Map<String,Object> map) {
		logger.info("ServiceImpl_SchBasic schBasicListSelect : Map="+map);
		return dao.schBasicListSelect(map);
	}

	@Override
	public SchBasicDTO schBasicChkSelect(Map<String,Object> map) {
		logger.info("ServiceImpl_SchBasic schBasicChkSelect : Map="+map);
		return dao.schBasicChkSelect(map);
	}

	@Override
	public List<SchBasicDTO> schBasicSelAll() {
		logger.info("ServiceImpl_SchBasic schBasicSelAll");
		return dao.schBasicSelAll();
	}

	@Override
	public boolean schBasicDel(int schbasic_seq) {
		logger.info("ServiceImpl_SchBasic schBasicDel seq:"+schbasic_seq );
		return dao.schBasicDel(schbasic_seq);
	}

}
