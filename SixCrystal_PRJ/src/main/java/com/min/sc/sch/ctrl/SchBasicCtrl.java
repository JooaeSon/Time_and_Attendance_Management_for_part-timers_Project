
package com.min.sc.sch.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.sc.sch.dtos.SchBasicDTO;
import com.min.sc.sch.model.IService_SchBasic;
import com.min.sc.user.dtos.UserInfoDTO;

@Controller
public class SchBasicCtrl {

	@Autowired
	private IService_SchBasic service;
	
	
//	@Autowired
//	private MakeJSON makejson;
//	
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/insertBasicForm.do", method = RequestMethod.GET)
	public String schBasicInsert(Model model, HttpSession session) throws ParseException {
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		String ws_code =  (String) session.getAttribute("ws_code");
		Map<String,Object> map = new HashMap<String,Object>();
		//회원유형 (고용주, 직원)에 따라 분기를 설정해야한다.
		
		map.put("ws_code", ws_code);
		if (!user.getUser_type().equalsIgnoreCase("employer")) {
			map.put("employee_rank","G");

		}
		
		
		List<SchBasicDTO> tlist = service.schBasicListSelect(map);
		List<Object> blist = new ArrayList<Object>();
		for (int i = 0; i < tlist.size(); i++) {
			SchBasicDTO dto = tlist.get(i);
			blist.add(dto.getEmployee_rank()+","+dto.getSchbasic_record());
		}
		model.addAttribute("tlist",tlist);
		model.addAttribute("blist", blist);
		return "schedule/insertBasicForm";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertSchBasic.do", method = RequestMethod.POST)
	public String insertSchBasic (SchBasicDTO sbdto, String schdate, String[] inday, String[] instart,
			String[] inend, String[] inper, String employee_rank,HttpSession session) throws ParseException {
		UserInfoDTO dto = (UserInfoDTO) session.getAttribute("user");
		String ws_code =  (String) session.getAttribute("ws_code");
		System.out.println(schdate);
		for (int i = 0; i < inper.length; i++) {
			System.out.println(inper[i]+inday[i]+instart[i]+inend[i]);
		}
		//{"SCHEDUEL":[{"NUM_OF_MEMBER":4,"START":"12:00","END":"20:00","DAY":"MON"}],
		//"BASIC":{"WORK_END":"2020-06-19","APPLY_START":"2020-06-08","WORK_START":"2020-06-15","APPLY_END":"2020-06-12"}}
		JSONObject mobj = new JSONObject();
		JSONParser jp = new JSONParser();
		mobj.put("BASIC", jp.parse(schdate));
		JSONArray scharr = new JSONArray();
		for (int i = 0; i < inper.length; i++) {
			String[] day = inday[i].split("/");
			
			for (int j = 0; j < day.length; j++) {
				JSONObject sobj = new JSONObject();
				sobj.put("DAY", day[j]);
				sobj.put("START",instart[i]);
				sobj.put("END",inend[i]);
				sobj.put("NUM_OF_MEMBER",inper[i]);
				scharr.add(sobj);
			}
		}
		mobj.put("SCHEDULE", scharr);
//		if (dto.getUser_type().equalsIgnoreCase("employer")) {
//			//고용주가 설정할경우
//			sbdto.setEmployee_rank(employee_rank);
//		}else {
//			//매니저가 설정할경우
//		}
			sbdto.setEmployee_rank(employee_rank);
			sbdto.setWs_code(ws_code);
		
		//고용주가 직급을 설정하여 보낼 수 있고
		//매니저는 일반직원의 기초사항만 설정할 수 있다.

		sbdto.setSchbasic_json(mobj.toJSONString());
		service.schBasicInsert(sbdto);
//		
		return "redirect:ws.do";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifySchBasic.do", method = RequestMethod.POST)
	public String modifySchBasic(SchBasicDTO mbdto,String schdate, String[] inday, String[] instart, String[] inend, String[] inper, int seq) throws ParseException {
		
		
		JSONObject mobj = new JSONObject();
		JSONParser jp = new JSONParser();
		mobj.put("BASIC", jp.parse(schdate));
		JSONArray scharr = new JSONArray();
		for (int i = 0; i < inper.length; i++) {
			String[] day = inday[i].split("/");
			
			for (int j = 0; j < day.length; j++) {
				JSONObject sobj = new JSONObject();
				sobj.put("DAY", day[j]);
				sobj.put("START",instart[i]);
				sobj.put("END",inend[i]);
				sobj.put("NUM_OF_MEMBER",inper[i]);
				scharr.add(sobj);
			}
		}
		mobj.put("SCHEDULE", scharr);
		


		service.schBasicUpdate(seq, mobj.toJSONString());
//		bdto.setSchbasic_json(schbasic_json);
		
		
		return "redirect:ws.do";
	}

}
