package com.min.sc.salay.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.min.sc.salary.dtos.SalaryMonthPayDto;
import com.min.sc.salary.model.IService_Salary;
import com.min.sc.sch.util.ExcelProcess;

@Controller
public class SalaryCtrl {
	
	@Autowired
	public IService_Salary service;
	
	@Autowired
	public ExcelProcess process;
	
	@RequestMapping(value = "/goSalaryUpload.do", method = RequestMethod.GET)
	public String salaryUpload() {
		return "salary/salaryUpload";
	}
	
	@RequestMapping (value = "/salaryMonthPayUpLoad.do", method = RequestMethod.POST)
	public String boardExcelUp (MultipartFile file , Model model) {

		List<SalaryMonthPayDto> list = process.uploadExcelFile(file);
		
		service.InsertExcelData(list);
		
		model.addAttribute("list", list);
		
		return "jsonView";
	}

}
