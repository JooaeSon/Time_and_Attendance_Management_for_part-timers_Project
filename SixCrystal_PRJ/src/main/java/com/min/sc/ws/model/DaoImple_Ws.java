package com.min.sc.ws.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.sc.ws.dtos.WorkSpaceDTO;

@Repository
public class DaoImple_Ws implements IDao_Ws {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate session;
	
	private final String NS="com.min.sc.ws.";

	@Override
	public String wsDateSelect() {
		log.info("DaoImple_Ws wsDateSelect : {}\"");
		return session.selectOne(NS+"wsDateSelect");
	}

	@Override
	public boolean wsInfoInsert(WorkSpaceDTO WSDto) {
		log.info("DaoImple_Ws wsInfoInsert : {}\"", WSDto);
		return (session.insert(NS+"wsInfoInsert", WSDto))>0?true:false;
	}

	@Override
	public WorkSpaceDTO wsInfoSelect(String ws_code) {
		log.info("DaoImple_Ws wsInfoSelect : {}\"", ws_code);
		return session.selectOne(NS+"wsInfoSelect", ws_code);
	}

	@Override
	public boolean wsInfoUpdate(WorkSpaceDTO WSDto) {
		log.info("DaoImple_Ws wsInfoUpdate : {}\"", WSDto);
		return (session.update(NS+"wsInfoUpdate", WSDto))>0?true:false;
	}

	@Override
	public boolean wsDelReqUpdate(String ws_code) {
		log.info("DaoImple_Ws wsDelReqUpdate : {}\"", ws_code);
		return (session.update(NS+"wsDelReqUpdate", ws_code))>0?true:false;
	}

	@Override
	public List<WorkSpaceDTO> wsDelReqSelect() {
		log.info("DaoImple_Ws wsDelReqSelect : {}\"");
		return session.selectList(NS+"wsDelReqSelect");
	}

	@Override
	public boolean wsDelReqYUpdate(String ws_code) {
		log.info("DaoImple_Ws wsDelReqYUpdate : {}\"", ws_code);
		return (session.update(NS+"wsDelReqYUpdate", ws_code))>0?true:false;
	}

	@Override
	public boolean wsDelReqNUpdate(String ws_code) {
		log.info("DaoImple_Ws wsDelReqNUpdate : {}\"", ws_code);
		return (session.update(NS+"wsDelReqNUpdate", ws_code))>0?true:false;
	}

	@Override
	public String wsErCodeSelect(String user_id) {
		log.info("DaoImple_Ws wsErCodeSelect : {}\"", user_id);
		return session.selectOne(NS+"wsErCodeSelect", user_id);
	}

	@Override
	public List<String> wsEeCodeSelect(String user_id) {
		log.info("DaoImple_Ws wsEeCodeSelect : {}\"", user_id);
		return session.selectList(NS+"wsEeCodeSelect", user_id);
	}

	@Override
	public String wsReqSelect(String ws_code) {
		log.info("DaoImple_Ws wsReqSelect : {}\"", ws_code);
		return session.selectOne(NS+"wsReqSelect", ws_code);
	}

}
