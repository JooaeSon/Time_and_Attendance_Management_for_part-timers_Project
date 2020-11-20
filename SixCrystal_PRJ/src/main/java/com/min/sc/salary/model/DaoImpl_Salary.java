package com.min.sc.salary.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.sc.salary.dtos.SalaryMonthPayDto;

@Repository
public class DaoImpl_Salary implements IDao_Salary {

	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private SqlSessionTemplate sqlSessionFactoryBean;
	
	private final String NS="com.min.sc.salary.";
	
	@Override
	public boolean InsertExcelData(SalaryMonthPayDto dto) {
		log.info("DaoImpl_Salary InsertExcelData {}",dto);
		int cnt=sqlSessionFactoryBean.insert(NS+"InsertExcelData", dto);
		return (cnt>0)?true:false;
	}

}
