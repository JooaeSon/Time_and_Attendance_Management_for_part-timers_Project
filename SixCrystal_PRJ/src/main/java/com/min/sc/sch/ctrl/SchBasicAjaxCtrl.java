package com.min.sc.sch.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;


import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.min.sc.sch.dtos.SchBasicDTO;
import com.min.sc.sch.model.IService_SchBasic;

@RestController
public class SchBasicAjaxCtrl {

	@Autowired
	private IService_SchBasic service;
	
	@RequestMapping(value = "/selectBasic.do", method = RequestMethod.POST)
	public String selectBasic(String record, Model model, HttpSession session) throws ParseException, java.text.ParseException {
		SimpleDateFormat rformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ws_code =  (String) session.getAttribute("ws_code");
//		Map<String, Object> map = new HashMap<String, Object>();
		String[] selrecord = record.split(",");
		Date drecord = rformat.parse(selrecord[1].trim());
		System.out.println("========================"+ws_code+"================="+selrecord);
		SchBasicDTO dto = service.schBasicSelect(ws_code,drecord);
//		map.put("bseq", dto.getSchbasic_seq());
//		map.put("json", dto.getSchbasic_json());
		JSONObject jobj= new JSONObject();
		JSONParser jp = new JSONParser();
		System.out.println(dto.getSchbasic_json());
//		jobj.put(key, value);
		jobj.put("json", jp.parse(dto.getSchbasic_json()));
		jobj.put("bseq", dto.getSchbasic_seq());
		System.out.println(dto.getSchbasic_json());
		System.out.println(jobj.toString());
		
				
	
		return jobj.toString();
	}
	
}
