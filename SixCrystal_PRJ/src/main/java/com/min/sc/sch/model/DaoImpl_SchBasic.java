package com.min.sc.sch.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.sc.sch.dtos.SchBasicDTO;

@Repository
public class DaoImpl_SchBasic implements IDao_SchBasic {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String NS = "com.min.sc.schbasic.";
	@Autowired
	private SqlSessionTemplate service;
	
	
	@Override
	public boolean schBasicInsert(SchBasicDTO sbdto) {
		logger.info("DaoImpl_SchBasic schBasicInsert : dto="+sbdto);
		int n = service.insert(NS+"schBasicInsert",sbdto);
		return n>0?true:false;
	}

	@Override
	public boolean schBasicUpdate(int seq, String schbasic_json) {
		logger.info("schBasicUpdate schBasicUpdate : seq="+seq+"schbasic_json"+schbasic_json);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("schbasic_json", schbasic_json);
		int n = service.update(NS+"schBasicUpdate", map);
		return n>0?true:false;
	}

	@Override
	public SchBasicDTO schBasicSelect(String wscode, Date record) {
		logger.info("DaoImpl_SchBasic schBasicSelect : WS_CODE="+wscode+"SCHBASIC_RECORD"+record);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ws_code", wscode);
		map.put("schbasic_record", record);
		return service.selectOne(NS+"schBasicSelect", map);
	}

	@Override
	public List<SchBasicDTO> schBasicListSelect(Map<String,Object> map) {
		logger.info("DaoImpl_SchBasic DaoImpl_SchBasic : Map="+map);
		return service.selectList(NS+"schBasicListSelect", map);
	}

	@Override
	public SchBasicDTO schBasicChkSelect(Map<String,Object> map) {
		logger.info("DaoImpl_SchBasic schBasicChkSelect : Map="+map);
		return service.selectOne(NS+"schBasicChkSelect",map);
	}

	@Override
	public List<SchBasicDTO> schBasicSelAll() {
		logger.info("DaoImpl_SchBasic schBasicSelAll");
		
		return service.selectList(NS+"schBasicSelAll");
	}

	@Override
	public boolean schBasicDel(int schbasic_seq) {
		logger.info("DaoImpl_SchBasic schBasicDel seq:"+schbasic_seq);
		int n = service.delete(NS+"schBasicDel", schbasic_seq);
		return n>0?true:false;
	}

}
