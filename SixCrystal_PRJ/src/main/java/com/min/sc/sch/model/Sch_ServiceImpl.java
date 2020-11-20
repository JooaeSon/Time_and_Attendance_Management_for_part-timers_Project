package com.min.sc.sch.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.sc.sch.dtos.SchDTO;

@Service
public class Sch_ServiceImpl implements Sch_IService{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Sch_IDao dao;

	@Override
	public String selectSchInfo(SchDTO dto) {
		log.info("Sch_ServiceImpl selectSchInfo {}",dto);
		return dao.selectSchInfo(dto);
	}

	@Override
	public boolean insertSchInfo(SchDTO dto) {
		log.info("Sch_ServiceImpl insertSchInfo {}",dto);
		return dao.insertSchInfo(dto);
	}

	@Override
	public String updateSchInfo(SchDTO dto) {
		log.info("Sch_ServiceImpl updateSchInfo {}",dto);
		return dao.updateSchInfo(dto);
	}

	@Override
	public boolean updateAccess(SchDTO dto) {
		log.info("Sch_ServiceImpl updateAccess {}",dto);
		return dao.updateAccess(dto);
	}

	@Override
	public String selectAccess(SchDTO dto) {
		log.info("Sch_ServiceImpl selectAccess {}",dto);
		return dao.selectAccess(dto);
	}
	
	@Override
	public String deleteSchInfo(SchDTO dto) {
		log.info("Sch_ServiceImpl deleteSchInfo {}",dto);
		return dao.deleteSchInfo(dto);
	}
	@Override
	public List<SchDTO> selectSchAll() {
		log.info("Sch_ServiceImpl selectSchAll");
		return dao.selectSchAll();
	}

	@Override
	public boolean cornUpdate(Map<String, Object> map) {
		log.info("Sch_ServiceImpl cornUpdate {}",map);
		return dao.cornUpdate(map);
	}





}
