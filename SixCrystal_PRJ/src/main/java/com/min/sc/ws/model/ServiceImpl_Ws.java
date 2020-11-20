package com.min.sc.ws.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.sc.ws.dtos.WorkSpaceDTO;

@Service
public class ServiceImpl_Ws implements IService_Ws {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDao_Ws dao;

	@Override
	public String wsDateSelect() {
		log.info("ServiceImpl_Ws wsDateSelect : {}\"");
		return dao.wsDateSelect();
	}

	@Override
	public boolean wsInfoInsert(WorkSpaceDTO WSDto) {
		log.info("ServiceImpl_Ws wsInfoInsert : {}\"", WSDto);
		return dao.wsInfoInsert(WSDto);
	}

	@Override
	public WorkSpaceDTO wsInfoSelect(String ws_code) {
		log.info("ServiceImpl_Ws wsDateSelect : {}\"", ws_code);
		return dao.wsInfoSelect(ws_code);
	}

	@Override
	public boolean wsInfoUpdate(WorkSpaceDTO WSDto) {
		log.info("ServiceImpl_Ws wsDateSelect : {}\"", WSDto);
		return dao.wsInfoUpdate(WSDto);
	}

	@Override
	public boolean wsDelReqUpdate(String ws_code) {
		log.info("ServiceImpl_Ws wsDateSelect : {}\"", ws_code);
		return dao.wsDelReqUpdate(ws_code);
	}

	@Override
	public List<WorkSpaceDTO> wsDelReqSelect() {
		log.info("ServiceImpl_Ws wsDateSelect : {}\"");
		return dao.wsDelReqSelect();
	}

	@Override
	public boolean wsDelReqYUpdate(String ws_code) {
		log.info("ServiceImpl_Ws wsDateSelect : {}\"", ws_code);
		return dao.wsDelReqYUpdate(ws_code);
	}

	@Override
	public boolean wsDelReqNUpdate(String ws_code) {
		log.info("ServiceImpl_Ws wsDateSelect : {}\"", ws_code);
		return dao.wsDelReqNUpdate(ws_code);
	}

	@Override
	public String wsErCodeSelect(String user_id) {
		log.info("ServiceImpl_Ws wsErCodeSelect : {}\"", user_id);
		return dao.wsErCodeSelect(user_id);
	}

	@Override
	public List<String> wsEeCodeSelect(String user_id) {
		log.info("ServiceImpl_Ws wsEeCodeSelect : {}\"", user_id);
		return dao.wsEeCodeSelect(user_id);
	}

	@Override
	public String wsReqSelect(String ws_code) {
		log.info("ServiceImpl_Ws wsReqSelect : {}\"", ws_code);
		return dao.wsReqSelect(ws_code);
	}

}
