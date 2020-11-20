package com.min.sc.work.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.sc.work.dtos.WMDto;


@Service
public class ServiceImpl_Work implements IService_Work {
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDao_Work dao;
	
	@Override
	public String workInfoSelect(Map<String, Object> map) {
		logger.info("ServiceImple: workInfoSelect");
		return dao.workInfoSelect(map);
	}

	@Override
	public Map<String, Object> workInfoSet(Map<String, Object> map) {
		logger.info("ServiceImple: workInfoSet");
		return dao.workInfoSet(map);
	}

	@Override
	public boolean workInfobasic_Insert(Map<String, Object> map) {
		logger.info("ServiceImple: workInfobasic_Insert");
		return dao.workInfobasic_Insert(map);
	}

	@Override
	public String selectWorkcheck(Map<String, Object> map) {
		logger.info("ServiceImple: selectWorkcheck");
		return dao.selectWorkcheck(map);
	}

	@Override
	public boolean updateDaily(Map<String, Object> map) {
		logger.info("ServiceImple: updateDaily");
		return dao.updateDaily(map);
	}


	@Override
	public String selectPersonal(Map<String, Object> map) {
		logger.info("ServiceImple: selectPersonal");
		return dao.selectPersonal(map);
	}

	@Override
	public List<WMDto> selectLowRank(Map<String, Object> map) {
		logger.info("ServiceImple: selectLowRank");
		return dao.selectLowRank(map);
	}

	@Override
	public String wifiInfoSelect(String work_code) {
		logger.info("ServiceImple: wifiInfoSelect");
		return dao.wifiInfoSelect(work_code);
	}

	@Override
	public boolean deleteWorkRecord(String deldate) {
		logger.info("ServiceImple: deleteWorkRecord");
		return dao.deleteWorkRecord(deldate);
	}

}
