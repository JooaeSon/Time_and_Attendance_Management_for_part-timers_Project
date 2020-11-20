package com.min.sc.salary.model;

import java.util.List;

import com.min.sc.salary.dtos.SalaryMonthPayDto;

public interface IDao_Salary {
	
	public boolean InsertExcelData(SalaryMonthPayDto dto);

}
