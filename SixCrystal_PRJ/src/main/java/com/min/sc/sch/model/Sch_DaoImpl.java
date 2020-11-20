package com.min.sc.sch.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.sc.sch.dtos.SchDTO;

@Repository
public class Sch_DaoImpl implements Sch_IDao {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private SqlSessionTemplate sqlSessionFactoryBean;
	
	private final String NS="com.min.sc.sch.sqls.";

	@Override
	public String selectSchInfo(SchDTO dto) {
		log.info("Sch_DaoImpl selectSchInfo {}",dto);
		return sqlSessionFactoryBean.selectOne(NS+"selectSchInfo", dto);
	}

	@Override
	public boolean insertSchInfo(SchDTO dto) {
		log.info("Sch_DaoImpl insertSchInfo {}",dto);
		int cnt=sqlSessionFactoryBean.insert(NS+"insertSchInfo", dto);
		return (cnt>0)?true:false;
	}

	@Override
	public String updateSchInfo(SchDTO dto) {
		log.info("Sch_DaoImpl updateSchInfo {}",dto);
		int cnt=sqlSessionFactoryBean.update(NS+"updateSchInfo", dto);
		return (cnt>0)?"true":"false";
	}


	@Override
	public boolean updateAccess(SchDTO dto) {
		log.info("Sch_DaoImpl updateAccess {}",dto);
		int cnt=sqlSessionFactoryBean.update(NS+"updateAccess", dto);
		return (cnt>0)?true:false;
	}

	@Override
	public String selectAccess(SchDTO dto) {
		log.info("Sch_DaoImpl selectSchInfo {}",dto);
		return sqlSessionFactoryBean.selectOne(NS+"selectAccess", dto);
	}

	@Override
	public String deleteSchInfo(SchDTO dto) {
		log.info("Sch_DaoImpl deleteSchInfo {}",dto);
		int cnt=sqlSessionFactoryBean.update(NS+"deleteSchInfo", dto);
		return (cnt>0)?"true":"false";
	}
	
	@Override
	public List<SchDTO> selectSchAll() {
		log.info("Sch_DaoImpl selectSchAll");
		return sqlSessionFactoryBean.selectList(NS+"selectSchAll");
	}

	@Override
	public boolean cornUpdate(Map<String, Object> map) {
		log.info("Sch_DaoImpl cornUpdate {}",map);
		int cnt=sqlSessionFactoryBean.delete(NS+"cornUpdate", map);
		return cnt>0?true:false;
	}


	

}
