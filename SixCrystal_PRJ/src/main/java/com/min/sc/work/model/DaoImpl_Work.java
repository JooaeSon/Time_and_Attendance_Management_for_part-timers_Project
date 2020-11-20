package com.min.sc.work.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.sc.work.dtos.WMDto;

@Repository
public class DaoImpl_Work implements IDao_Work{
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	private final String NS="com.min.sc.work.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public String workInfoSelect(Map<String, Object> map) {
		// TODO month와 사업장 코드를 입력하면 해당 리스트가 다 나온다.
		logger.info("DaoImpl: workInfoSelect");

		return sqlSession.selectOne(NS+"workInfoSelect", map);
	}

	@Override
	public Map<String, Object> workInfoSet(Map<String, Object> map) {
		logger.info("DaoImpl: workInfoSet");
		
		return sqlSession.selectOne(NS+"workInfoSet", map);
	}

	@Override
	public boolean workInfobasic_Insert(Map<String, Object> map) {
		logger.info("DaoImpl: workInfobasic_Insert");
		
		int cnt=sqlSession.insert(NS+"workInfobasic_Insert", map);
		return cnt>0? true:false;

	}

	@Override
	public String selectWorkcheck(Map<String, Object> map) {
		logger.info("DaoImpl: selectWorkcheck");
		
		return sqlSession.selectOne(NS+"selectWorkcheck", map);
	}

	@Override
	public boolean updateDaily(Map<String, Object> map) {
		logger.info("DaoImpl: updateDaily");
		
		int cnt=sqlSession.update(NS+"updateDaily", map);
		return cnt>0? true:false;
	}

	@Override
	public String selectPersonal(Map<String, Object> map) {
		logger.info("DaoImpl: selectPersonal");
		
		return sqlSession.selectOne(NS+"selectPersonal", map);
	}

	@Override
	public List<WMDto> selectLowRank(Map<String, Object> map) {
		logger.info("DaoImpl: selectLowRank");
		return sqlSession.selectList(NS+"selectLowRank",map);
	}

	@Override
	public String wifiInfoSelect(String work_code) {
		logger.info("DaoImpl: wifiInfoSelect");
		return sqlSession.selectOne(NS+"wifiInfoSelect", work_code);
	}

	@Override
	public boolean deleteWorkRecord(String deldate) {
		logger.info("DaoImpl: deleteWorkRecord");
		int cnt=sqlSession.insert(NS+"deleteWorkRecord", deldate);
		return cnt>0? true:false;
	}
}
