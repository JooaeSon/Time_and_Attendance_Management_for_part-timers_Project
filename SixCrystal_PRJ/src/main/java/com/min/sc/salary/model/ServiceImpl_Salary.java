package com.min.sc.salary.model;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.sc.salary.dtos.SalaryMonthPayDto;

@Service
public class ServiceImpl_Salary implements IService_Salary {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private IDao_Salary dao;
	
	@Override
	@Transactional
	public boolean InsertExcelData(List<SalaryMonthPayDto> list) {
		log.info("ServiceImpl_Salary InsertExcelData {}",list);
//		return dao.InsertExcelData(dto);
		boolean isc= false;
		
		for (SalaryMonthPayDto dto : list) {
			isc= dao.InsertExcelData(dto);
			if(isc==false) {
				System.out.println("실패 ㅠ");
				break;
			}
		}
		
		return isc;
	}

}
