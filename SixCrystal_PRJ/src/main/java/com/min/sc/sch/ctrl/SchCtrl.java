package com.min.sc.sch.ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.min.sc.sch.dtos.SchBasicDTO;
import com.min.sc.sch.dtos.SchDTO;
import com.min.sc.sch.model.IService_SchBasic;
import com.min.sc.sch.model.Sch_IService;
import com.min.sc.sch.util.ExcelProcess;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;
import com.min.sc.ws.model.IService_Ws;



@Controller
public class SchCtrl {

	@Autowired
	public Sch_IService service;
	
	@Autowired
	public IService_SchBasic service_basic;
	
	@Autowired
	public IService_Ws service_ws;
	
	@Autowired
	public ExcelProcess process;

	
	
	
	
	@RequestMapping(value = "/makeSchedule.do", method = RequestMethod.GET)
	public String makeSchedule() {
//		return "owner/makeSchedule";
		return "schedule/makeSchedule";
	}

	@RequestMapping (value = "/boardExdown.do", method = RequestMethod.POST)
	public String boardExcelDown( Model model,HttpSession session,HttpServletResponse resp ) throws ParseException, IOException{
		
		JsonParser parser = new JsonParser();
		
//		
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");
		
        String APPLY_START=(String) model.getAttribute("APPLY_START");
        String APPLY_END=(String) model.getAttribute("APPLY_END");
        String WORK_END=(String) model.getAttribute("WORK_END");
        String WORK_START=(String) model.getAttribute("WORK_START");
		
//직원꺼 찾자
		HashMap<String, Object> map_emp = new HashMap<String,Object>();
//				map.put("employee_rank", rank.equals("emp")?"G":"M");
		map_emp.put("employee_rank", "G");
		map_emp.put("ws_code", ws_code);
		
		SchBasicDTO dtoEmpBasic=service_basic.schBasicChkSelect(map_emp);
		System.out.println(dtoEmpBasic);
		//매니저꺼 찾자
		HashMap<String, Object> map_man = new HashMap<String,Object>();
//				map.put("employee_rank", rank.equals("emp")?"G":"M");
		map_man.put("employee_rank", "M");
		map_man.put("ws_code", ws_code);
		SchBasicDTO dtoManBasic=service_basic.schBasicChkSelect(map_man);
		String schedule_basic_emp_str="";
		String schedule_basic_man_str="";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
		if(dtoManBasic==null && dtoEmpBasic==null) {
			System.out.println("rank="+rank);
			PrintWriter p =  resp.getWriter();
			p.print("<script> alert('기초사항이 설정되지 않았습니다. 기초사항 설정 후 일정관리를 진행하여 주세요');</script>");
			p.flush();
            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
		}else if(dtoManBasic==null && dtoEmpBasic!=null){//직원의 기초사항만 등록되었을때 
			System.out.println("직원의 기초사항만 등록되었습니다");
			schedule_basic_emp_str=dtoEmpBasic.getSchbasic_json();
			JsonObject schedule_basic_emp_obj = (JsonObject) parser.parse(schedule_basic_emp_str);
			APPLY_START= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			System.out.println("APPLY_START : "+APPLY_START);
			APPLY_END= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			System.out.println("APPLY_END : "+APPLY_END);
			WORK_END= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			System.out.println("WORK_END : "+WORK_END);
			WORK_START= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
			System.out.println("WORK_START : "+WORK_START);
            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
            
            model.addAttribute("APPLY_START", APPLY_START);
            model.addAttribute("APPLY_END", APPLY_END);
    		model.addAttribute("WORK_START", WORK_START);
    		model.addAttribute("WORK_END", WORK_END);
    		model.addAttribute("id", session.getAttribute("id"));
    		model.addAttribute("rank", session.getAttribute("rank"));
            
            
            
		}else if(dtoEmpBasic==null && dtoManBasic!=null){
			System.out.println("매니저의 기초사항만 등록되었습니다");
			schedule_basic_man_str=dtoManBasic.getSchbasic_json();
			JsonObject schedule_basic_man_obj = (JsonObject) parser.parse(schedule_basic_man_str);
			APPLY_START= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			System.out.println("APPLY_START : "+APPLY_START);
			APPLY_END= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			System.out.println("APPLY_END : "+APPLY_END);
			WORK_END= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			System.out.println("WORK_END : "+WORK_END);
			WORK_START= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
			System.out.println("WORK_START : "+WORK_START);
            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
            
            model.addAttribute("APPLY_START", APPLY_START);
            model.addAttribute("APPLY_END", APPLY_END);
    		model.addAttribute("WORK_START", WORK_START);
    		model.addAttribute("WORK_END", WORK_END);
    		model.addAttribute("id", session.getAttribute("id"));
    		model.addAttribute("rank", session.getAttribute("rank"));
    		
		}else {
			System.out.println("매니저와 직원 모두의일정이 등록되었씁니다.");
			schedule_basic_emp_str=dtoEmpBasic.getSchbasic_json();
			schedule_basic_man_str=dtoManBasic.getSchbasic_json();
			JsonObject schedule_basic_emp_obj = (JsonObject) parser.parse(schedule_basic_emp_str);
			String APPLY_START_emp= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			String APPLY_END_emp= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			String WORK_END_emp= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			String WORK_START_emp= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
			JsonObject schedule_basic_man_obj = (JsonObject) parser.parse(schedule_basic_man_str);
			String APPLY_START_man= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			String APPLY_END_man= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			String WORK_END_man= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			String WORK_START_man= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
			String[] start_end_apply=findTime(APPLY_START_emp, APPLY_END_emp, APPLY_START_man, APPLY_END_man);
			String[] start_end_work=findTime(WORK_START_emp, WORK_END_emp, WORK_END_man, WORK_START_man);
			APPLY_START=start_end_apply[0];
			System.out.println("APPLY_START : "+APPLY_START);
			APPLY_END=start_end_apply[1];
			System.out.println("APPLY_END : "+APPLY_END);
			WORK_START=start_end_work[0];
			System.out.println("WORK_START : "+WORK_START);
			WORK_END=start_end_work[1];
			System.out.println("WORK_END : "+WORK_END);
//				PrintWriter p =  resp.getWriter();
//				p.print("<script> alert('주인님들어가십니다~');</script>");
//				p.flush();
            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
            
            model.addAttribute("APPLY_START", APPLY_START);
            model.addAttribute("APPLY_END", APPLY_END);
    		model.addAttribute("WORK_START", WORK_START);
    		model.addAttribute("WORK_END", WORK_END);
    		model.addAttribute("id", session.getAttribute("id"));
    		model.addAttribute("rank", session.getAttribute("rank"));
		}
		
		
		String year_month=WORK_START.substring(0,7);//자바스크립트로 가져오자
		System.out.println("year_month"+year_month);
		
		//2020-06에 해당하는 모든 스케줄 가져오고
		SchDTO dto = new SchDTO();
		dto.setSch_month(year_month);
		dto.setWs_code(ws_code);
		String schedule = service.selectSchInfo(dto);
		System.out.println("schedule : "+schedule);
		//그중에 work_start랑 work_end에 맞는거 가져오고
		
//		String accessflag =service.selectAccess(dto);
//		model.addAttribute("flag", accessflag);
		
		//그런다음에 그 JsonArray를 보내자!! 
		
//		JsonArray scheduleArray = (JsonArray) parser.parse(schedule);
		//그럼 이 데이터의 형태는 [{"":""},{"":""},{"":""},{"":""}] 요로케해서 되게찌? generateschedule이거 메서드로 해놔야겠다 ㅇㅇ 따로 뺴놔야겠따 ㅇㅇ 
		
		String start_month=WORK_START.substring(0, 7);
		String end_month=WORK_END.substring(0, 7);
		String dayStart=WORK_START.substring(8, 10);
		String dayEnd=WORK_END.substring(8, 10);
		
		  JsonArray array_common=new JsonArray();
	      JsonArray array_end=new JsonArray();
	      String whatDay_ele;
	      JsonArray send = new JsonArray();
	      if(start_month.equals(end_month)) {
	         System.out.println("같습니다~");
	         SchDTO dto_select = new SchDTO();
	         dto_select.setSch_month(start_month);
	         dto_select.setWs_code(ws_code);
	         array_common = nullChk(service.selectSchInfo(dto_select));
	         System.out.println(array_common);
//	         1) 첫날과 막날이 같은 달일경우 첫날과 막날 사이에 있는 스케쥴을 가져오자!!
	         for (int i = 0; i < array_common.size(); i++) {
	            whatDay_ele=array_common.get(i).getAsJsonObject().get("day").toString();
	            if((convertNumber(whatDay_ele).compareTo(dayStart)>=0)&&(convertNumber(whatDay_ele).compareTo(dayEnd)<=0)) {
	               for (int j = 0; j < array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
	                  send.add( array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
	               }
	            }
	         }
	      }
	         else {
	         System.out.println("다릅니다~");
	         SchDTO dto_select = new SchDTO();
	         dto_select.setSch_month(start_month);
	         dto_select.setWs_code(ws_code);
	         array_common = nullChk(service.selectSchInfo(dto_select));
	         System.out.println(array_common);
//	         
	         for (int i = 0; i < array_common.size(); i++) {
	            whatDay_ele=array_common.get(i).getAsJsonObject().get("day").toString();
	            if(convertNumber(whatDay_ele).compareTo(dayStart)>=0) {
	               for (int j = 0; j < array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
	                  send.add( array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
	               }
	            }
	         }
	         SchDTO dto_select_another = new SchDTO();
	         dto_select_another.setSch_month(end_month);
	         dto_select_another.setWs_code(ws_code);
	         array_end = nullChk(service.selectSchInfo(dto_select_another));
	         System.out.println(array_end);
	         for (int i = 0; i < array_end.size(); i++) {
	            whatDay_ele=array_end.get(i).getAsJsonObject().get("day").toString();
	            if(convertNumber(whatDay_ele).compareTo(dayEnd)<=0) {
	               for (int j = 0; j < array_end.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
	                  send.add(array_end.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
	               }
	            }
	         }
	      }

	      
	      System.out.println(send);
		Workbook workbook = process.makeExcelWorkbook(send);
	   
	    
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", "일정 목록");
	    
	    return "excelDownloadView";
	}
	private String convertNumber(String num) {
	      String result = num;
	      if(num.length()==4) {
	         result=""+num.charAt(1)+num.charAt(2);
	      }
	      return result;
	}
	
	private JsonArray nullChk(String generateTarget_str) {
		JsonArray generateTarget_ele;
		JsonParser parser = new JsonParser();
		if(generateTarget_str==null) {
			generateTarget_ele = new JsonArray();
		}else {
			generateTarget_ele = (JsonArray) parser.parse(generateTarget_str);
		}
		return generateTarget_ele;
	}
	
//	}
	
	@RequestMapping(value = "/exchangeData.do",method = RequestMethod.POST)
	public String exchangeData(String my_id,String my_scheid,String start,String emp_id,String emp_schedule,HttpSession session) {
		
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+my_id+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//내 아이디
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+my_scheid+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//내 스케쥴의 아이디
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+start+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//내 스케쥴의 시작시간
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+emp_id+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//바꾸는사람의 아이디
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+emp_schedule+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//바뀌는 스케쥴의 아이디+시작시간+종료시간
		
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");

//		>>>
		String my_start_yearmonth=start.substring(0, 7);
		System.out.println("my_start_yearmonth : "+my_start_yearmonth);
		String my_start_day=start.substring(8,10);
		System.out.println("my_start_day : "+my_start_day);
		String[] yourScheduleInfo=null;
		String your_schedule_id="";
		String your_start_month="";
		String your_start_day="";
		boolean throwFlag=false;
		
		System.out.println(emp_schedule);
			yourScheduleInfo = emp_schedule.split(" ");
			your_schedule_id=yourScheduleInfo[0];
			your_start_month=yourScheduleInfo[1].substring(0, 7);
			System.out.println("your_start_month : "+your_start_month);
			your_start_day=yourScheduleInfo[1].substring(8,10);
			System.out.println("your_start_day : "+your_start_day);
		
		JsonParser parser = new JsonParser();
		JsonElement mySchObj = new JsonObject();
		JsonElement yourSchObj = new JsonObject();
		if(my_start_yearmonth.equalsIgnoreCase(your_start_month)) {
			System.out.println("두 달이 같아!!");
			System.out.println("자 내것이 있는 어레이를 셀렉트하자 ");
			int my_day_idx=0;
			int my_schedule_idx=0;
			int your_day_idx=0;
			int your_schedule_idx=0;
			
			
			SchDTO mydto = new SchDTO();
			mydto.setSch_month(my_start_yearmonth);
			mydto.setWs_code(ws_code);
			String myScheduleArray_str=service.selectSchInfo(mydto);
			JsonArray myScheduleArray_arr = (JsonArray) parser.parse(myScheduleArray_str);
			
			System.out.println("변경 전 myScheduleArray_arr : "+myScheduleArray_arr);
			
			for (int i = 0; i < myScheduleArray_arr.getAsJsonArray().size(); i++) {
				if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(my_start_day))) {
					my_day_idx=i;
					System.out.println("날짜 같은거 찾았따!!");
					System.out.println("my_day_idx : "+i);
					for (int j = 0; j <myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
						if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(parser.parse("\""+my_scheid+"\""))) {
							my_schedule_idx=j;
							System.out.println("아이디 같은거 찾았다!! ");
							System.out.println("my_scheid : "+my_scheid);
							mySchObj=(JsonObject) myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
//							if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
//								myScheduleArray_arr.getAsJsonArray().remove(i);
//							}
							break;
						}
					}
					break;
				}
			}
			
			for (int i = 0; i < myScheduleArray_arr.getAsJsonArray().size(); i++) {
				if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(your_start_day))) {
					your_day_idx=i;
					System.out.println("날짜 같은거 찾았따!!");
					System.out.println("your_day_idx : "+i);
					for (int j = 0; j <myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
						if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(parser.parse("\""+your_schedule_id+"\""))) {
							your_schedule_idx=j;
							System.out.println("아이디 같은거 찾았다!! ");
							System.out.println("your_schedule_id : "+your_schedule_id);
							yourSchObj=myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
							break;
						}
					}
					break;
				}
			}
			
			//만약에 두 달이 같으면 하나의 어레이로 꿍쨕꿍쨕하면 되니까!! 
			System.out.println("변경 전 myScheduleArray_arr : "+myScheduleArray_arr);
			System.out.println("추출한 mySchObj : "+mySchObj);
			System.out.println("추출한 yourSchObj : "+yourSchObj);
			
			mySchObj.getAsJsonObject().remove("title");
			mySchObj.getAsJsonObject().add("title", parser.parse(emp_id));
			yourSchObj.getAsJsonObject().remove("title");
			yourSchObj.getAsJsonObject().add("title", parser.parse(my_id));
			myScheduleArray_arr.getAsJsonArray().get(your_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().add(yourSchObj);
			myScheduleArray_arr.getAsJsonArray().get(my_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().add(mySchObj);
			//마지막에 확인하면서 따옴표 어떻게 붙는지 확인하고나서 
			System.out.println("변경 후 myScheduleArray_arr : "+myScheduleArray_arr);
			//디비에 인서트하자!! 
//			SchDTO mydto = new SchDTO();
			
			mydto.setSch_month(my_start_yearmonth);
			mydto.setWs_code(ws_code);
			mydto.setSch_schedule(myScheduleArray_arr.toString());
			service.updateSchInfo(mydto);
			
			
		}else {
			System.out.println("두 달이 달라!!!!");
			System.out.println("자 내것이 있는 어레이를 셀렉트하자 ");
			int my_day_idx=0;
			int my_schedule_idx=0;
			int your_day_idx=0;
			int your_schedule_idx=0;
			
			
			SchDTO mydto = new SchDTO();
			mydto.setSch_month(my_start_yearmonth);
			mydto.setWs_code(ws_code);
			String myScheduleArray_str=service.selectSchInfo(mydto);
			JsonArray myScheduleArray_arr = (JsonArray) parser.parse(myScheduleArray_str);
			
			System.out.println("변경 전 myScheduleArray_arr : "+myScheduleArray_arr);
//			JsonElement mySchObj = new JsonObject();
			for (int i = 0; i < myScheduleArray_arr.getAsJsonArray().size(); i++) {
				if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(my_start_day))) {
					my_day_idx=i;
					System.out.println("날짜 같은거 찾았따!!");
					System.out.println("my_day_idx : "+i);
					for (int j = 0; j <myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
						if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(parser.parse("\""+my_scheid+"\""))) {
							my_schedule_idx=j;
							System.out.println("아이디 같은거 찾았다!! ");
							System.out.println("my_scheid : "+my_scheid);
							mySchObj=(JsonObject) myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
							break;
						}
					}
					break;
				}
			}
			
			
			//자 당신것이 있는 어레이를 셀렉트하자 
			SchDTO yourdto = new SchDTO();
			yourdto.setSch_month(your_start_month);//////////////////////////////////////
			yourdto.setWs_code(ws_code);
			String yourScheduleArray_str=service.selectSchInfo(yourdto);
			JsonArray yourScheduleArray_arr = (JsonArray) parser.parse(yourScheduleArray_str);
			
			System.out.println("변경 전 yourScheduleArray_arr : "+yourScheduleArray_arr);
//			JsonElement yourSchObj = new JsonObject();
			for (int i = 0; i < yourScheduleArray_arr.getAsJsonArray().size(); i++) {
				if(yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(your_start_day))) {
					your_day_idx=i;
					System.out.println("날짜 같은거 찾았따!!");
					System.out.println("your_day_idx : "+i);
					for (int j = 0; j <yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
						if(yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(parser.parse("\""+your_schedule_id+"\""))) {
							your_schedule_idx=j;
							System.out.println("아이디 같은거 찾았다!! ");
							System.out.println("your_schedule_id : "+your_schedule_id);
							yourSchObj=yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
//							if(yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
//								yourScheduleArray_arr.getAsJsonArray().remove(i);
//							}
							break;
						}
					}
					break;
				}
			}
			System.out.println("변경 전 myScheduleArray_arr : "+myScheduleArray_arr);
			System.out.println("변경 전 yourScheduleArray_arr : "+yourScheduleArray_arr);
			System.out.println("mySchObj : "+mySchObj);
			System.out.println("yourSchObj : "+yourSchObj);
			
			//타이틀을 바꿔야함,,, 
			mySchObj.getAsJsonObject().remove("title");
			mySchObj.getAsJsonObject().add("title", parser.parse(emp_id));
			yourSchObj.getAsJsonObject().remove("title");
			yourSchObj.getAsJsonObject().add("title", parser.parse(my_id));
			myScheduleArray_arr.getAsJsonArray().get(your_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().add(yourSchObj);
			yourScheduleArray_arr.getAsJsonArray().get(my_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().add(mySchObj);
//			
			
			//마지막에 확인하면서 따옴표 어떻게 붙는지 확인하고나서 
			System.out.println("변경 후 myScheduleArray_arr : "+myScheduleArray_arr);
			System.out.println("변경 후 yourScheduleArray_arr : "+yourScheduleArray_arr);
			//디비에 인서트하자!! 
			mydto.setSch_month(my_start_yearmonth);
			mydto.setWs_code(ws_code);
			mydto.setSch_schedule(myScheduleArray_arr.toString());
			service.updateSchInfo(mydto);
			mydto.setSch_month(your_start_month);
			mydto.setWs_code(ws_code);
			mydto.setSch_schedule(yourScheduleArray_arr.toString());
			service.updateSchInfo(mydto);
			
		}
		
		return "redirect:goCalendar.do";
	}
	
	@RequestMapping(value = "/throwData.do",method = RequestMethod.POST)
	public String throwData(String my_id,String my_scheid,String start,String emp_id,String emp_schedule,HttpSession session) {
		
		
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+my_id+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//내 아이디
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+my_scheid+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//내 스케쥴의 아이디
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+start+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//내 스케쥴의 시작시간
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+emp_id+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//바꾸는사람의 아이디
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒"+emp_schedule+"▒▒▒▒▒▒▒▒▒▒▒▒▒▒");//바뀌는 스케쥴의 아이디+시작시간+종료시간
		
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");

		String my_start_yearmonth=start.substring(0, 7);
		System.out.println("my_start_yearmonth : "+my_start_yearmonth);
		String my_start_day=start.substring(8,10);
		System.out.println("my_start_day : "+my_start_day);
		String[] yourScheduleInfo=null;
		String your_schedule_id="";
		String your_start_month="";
		String your_start_day="";
		boolean throwFlag=false;
		
		System.out.println(emp_schedule);
			yourScheduleInfo = emp_schedule.split(" ");
			your_schedule_id=yourScheduleInfo[0];
			your_start_month=yourScheduleInfo[1].substring(0, 7);
			System.out.println("your_start_month : "+your_start_month);
			your_start_day=yourScheduleInfo[1].substring(8,10);
			System.out.println("your_start_day : "+your_start_day);
		
		JsonParser parser = new JsonParser();
		JsonElement mySchObj = new JsonObject();
		JsonElement yourSchObj = new JsonObject();
		if(my_start_yearmonth.equalsIgnoreCase(your_start_month)) {
			System.out.println("두 달이 같아!!");
			System.out.println("자 내것이 있는 어레이를 셀렉트하자 ");
			int my_day_idx=0;
			int my_schedule_idx=0;
			int your_day_idx=0;
			int your_schedule_idx=0;
			
			
			SchDTO mydto = new SchDTO();
			mydto.setSch_month(my_start_yearmonth);
			mydto.setWs_code(ws_code);
			String myScheduleArray_str=service.selectSchInfo(mydto);
			JsonArray myScheduleArray_arr = (JsonArray) parser.parse(myScheduleArray_str);
			
			System.out.println("변경 전 myScheduleArray_arr : "+myScheduleArray_arr);
			
			for (int i = 0; i < myScheduleArray_arr.getAsJsonArray().size(); i++) {
				if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(my_start_day))) {
					my_day_idx=i;
					System.out.println("날짜 같은거 찾았따!!");
					System.out.println("my_day_idx : "+i);
					for (int j = 0; j <myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
						if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(parser.parse("\""+my_scheid+"\""))) {
							my_schedule_idx=j;
							System.out.println("아이디 같은거 찾았다!! ");
							System.out.println("my_scheid : "+my_scheid);
							mySchObj=(JsonObject) myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
//							if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
//								myScheduleArray_arr.getAsJsonArray().remove(i);
//							}
							break;
						}
					}
					break;
				}
			}
			
			for (int i = 0; i < myScheduleArray_arr.getAsJsonArray().size(); i++) {
				if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(your_start_day))) {
					your_day_idx=i;
					System.out.println("날짜 같은거 찾았따!!");
					System.out.println("your_day_idx : "+i);
					for (int j = 0; j <myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
						if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(parser.parse("\""+your_schedule_id+"\""))) {
							your_schedule_idx=j;
							System.out.println("아이디 같은거 찾았다!! ");
							System.out.println("your_schedule_id : "+your_schedule_id);
							yourSchObj=myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
//							if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
//								myScheduleArray_arr.getAsJsonArray().remove(i);
//							}
							break;
						}
					}
					break;
				}
			}
			
			//만약에 두 달이 같으면 하나의 어레이로 꿍쨕꿍쨕하면 되니까!! 
			System.out.println("변경 전 myScheduleArray_arr : "+myScheduleArray_arr);
			System.out.println("추출한 mySchObj : "+mySchObj);
			System.out.println("추출한 yourSchObj : "+yourSchObj);
			//던지기는 타이틀을 바꿔야겠네
			mySchObj.getAsJsonObject().remove("title");
			mySchObj.getAsJsonObject().add("title",parser.parse(emp_id));
			myScheduleArray_arr.getAsJsonArray().get(my_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().add(mySchObj);
			myScheduleArray_arr.getAsJsonArray().get(your_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().add(yourSchObj);//원상복귀
//			myScheduleArray_arr.getAsJsonArray().get(my_day_idx).getAsJsonObject()
			
			System.out.println("변경 후 myScheduleArray_arr : "+myScheduleArray_arr);
			//디비에 인서트하자!! 
//			SchDTO mydto = new SchDTO();
			
			mydto.setSch_month(my_start_yearmonth);
			mydto.setWs_code(ws_code);
			mydto.setSch_schedule(myScheduleArray_arr.toString());
			service.updateSchInfo(mydto);
			
			
		}else {
			System.out.println("두 달이 달라!!!!");
			System.out.println("자 내것이 있는 어레이를 셀렉트하자 ");
			int my_day_idx=0;
			int my_schedule_idx=0;
			int your_day_idx=0;
			int your_schedule_idx=0;
			
			
			SchDTO mydto = new SchDTO();
			mydto.setSch_month(my_start_yearmonth);
			mydto.setWs_code(ws_code);
			String myScheduleArray_str=service.selectSchInfo(mydto);
			JsonArray myScheduleArray_arr = (JsonArray) parser.parse(myScheduleArray_str);
			
			System.out.println("변경 전 myScheduleArray_arr : "+myScheduleArray_arr);
//			JsonElement mySchObj = new JsonObject();
			for (int i = 0; i < myScheduleArray_arr.getAsJsonArray().size(); i++) {
				if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(my_start_day))) {
					my_day_idx=i;
					System.out.println("날짜 같은거 찾았따!!");
					System.out.println("my_day_idx : "+i);
					for (int j = 0; j <myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
						if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(parser.parse("\""+my_scheid+"\""))) {
							my_schedule_idx=j;
							System.out.println("아이디 같은거 찾았다!! ");
							System.out.println("my_scheid : "+my_scheid);
							mySchObj=(JsonObject) myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
//							if(myScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
//								myScheduleArray_arr.getAsJsonArray().remove(i);
//							}
							break;
						}
					}
					break;
				}
			}
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//자 당신것이 있는 어레이를 셀렉트하자 
			SchDTO yourdto = new SchDTO();
			yourdto.setSch_month(your_start_month);//////////////////////////////////////
			yourdto.setWs_code(ws_code);
			String yourScheduleArray_str=service.selectSchInfo(yourdto);
			JsonArray yourScheduleArray_arr = (JsonArray) parser.parse(yourScheduleArray_str);
			
			System.out.println("변경 전 yourScheduleArray_arr : "+yourScheduleArray_arr);
//			JsonElement yourSchObj = new JsonObject();
			for (int i = 0; i < yourScheduleArray_arr.getAsJsonArray().size(); i++) {
				if(yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(your_start_day))) {
					your_day_idx=i;
					System.out.println("날짜 같은거 찾았따!!");
					System.out.println("your_day_idx : "+i);
					for (int j = 0; j <yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
						if(yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(parser.parse("\""+your_schedule_id+"\""))) {
							your_schedule_idx=j;
							System.out.println("아이디 같은거 찾았다!! ");
							System.out.println("your_schedule_id : "+your_schedule_id);
							yourSchObj=yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
//							if(yourScheduleArray_arr.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
//								yourScheduleArray_arr.getAsJsonArray().remove(i);
//							}
							break;
						}
					}
					break;
				}
			}
			System.out.println("변경 전 myScheduleArray_arr : "+myScheduleArray_arr);
			System.out.println("변경 전 yourScheduleArray_arr : "+yourScheduleArray_arr);
			System.out.println("mySchObj : "+mySchObj);
			System.out.println("yourSchObj : "+yourSchObj);

			//던지기는 타이틀을 바꿔야겠네
			mySchObj.getAsJsonObject().remove("title");
			mySchObj.getAsJsonObject().add("title",parser.parse(emp_id));
			yourScheduleArray_arr.getAsJsonArray().get(my_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().add(mySchObj);
			yourScheduleArray_arr.getAsJsonArray().get(your_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().add(yourSchObj);//원상복귀
			
			//마지막에 확인하면서 따옴표 어떻게 붙는지 확인하고나서 
			System.out.println("변경 후 myScheduleArray_arr : "+myScheduleArray_arr);
			System.out.println("변경 후 yourScheduleArray_arr : "+myScheduleArray_arr);
			//디비에 인서트하자!! 
			mydto.setSch_month(my_start_yearmonth);
			mydto.setWs_code(ws_code);
			mydto.setSch_schedule(myScheduleArray_arr.toString());
			service.updateSchInfo(mydto);
			mydto.setSch_month(your_start_month);
			mydto.setWs_code(ws_code);
			mydto.setSch_schedule(yourScheduleArray_arr.toString());
			service.updateSchInfo(mydto);
			
		}
		
		return "redirect:goCalendar.do";
	}
	
	@RequestMapping(value="/goCalendar.do",method = RequestMethod.GET)
	public String EmployeeOrManager(Model model,HttpSession session,HttpServletResponse resp) throws IOException, ParseException {

		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");

		boolean okay=false;
		String destination="schedule/emp_calendar";
		JsonParser parser = new JsonParser();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(session.getAttribute("rank").equals("emp")) {
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("employee_rank", rank.equals("emp")?"G":"M");
			map.put("ws_code", ws_code);
			SchBasicDTO dtoEmpBasic=service_basic.schBasicChkSelect(map);
			System.out.println(dtoEmpBasic);
			 resp.setCharacterEncoding("utf-8");
		       resp.setContentType("text/html; charset=UTF-8");
		       
			if(dtoEmpBasic==null) {
				okay=true;
	            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
	            model.addAttribute("WSDto", WSDto);
				destination="ws/select";
				PrintWriter p =  resp.getWriter();
				p.print("<script> alert('해당 직급의 일정기초사항이 설정되지 않았습니다. 사업장공지사항을 확인해주세요');</script>");
				p.flush();
				return "ws/select";
				
			}
			
			String schedule_basic_emp_str=dtoEmpBasic.getSchbasic_json();
			
//			오늘날짜와 비교하여 신청날짜가 아니라면 페이지로 들어갈 수 없도록!! (alert창 띄우기!!)//신청날짜라면 신청페이지로 이동
			JsonObject schedule_basic_obj = (JsonObject) parser.parse(schedule_basic_emp_str);
			String APPLY_START= schedule_basic_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			String APPLY_END= schedule_basic_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			String WORK_END= schedule_basic_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			String WORK_START= schedule_basic_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
//			Date today_date =format.parse(format.format(new Date()));
//			Date start_date =format.parse(APPLY_START);
//			Date end_date =format.parse(APPLY_END);
			String year_month=WORK_START.substring(0,7);//자바스크립트로 가져오자
			SchDTO dto = new SchDTO();
			dto.setSch_month(year_month);
			dto.setWs_code(ws_code);
			System.out.println(timechk(APPLY_START,APPLY_END));
			String accessflag =service.selectAccess(dto);
			System.out.println(accessflag);
			model.addAttribute("flag", accessflag);
		      resp.setCharacterEncoding("utf-8");
		       resp.setContentType("text/html; charset=UTF-8");
			if(timechk(APPLY_START,APPLY_END)) {
				System.out.println("조건문true");
				System.out.println("accessflag : "+accessflag);
				PrintWriter p =  resp.getWriter();
				if(accessflag==null||accessflag.equalsIgnoreCase("D")) {
					System.out.println("accessflag : "+accessflag);
					p.print("<script> alert('신청 날짜 입니다. 일정을 등록해 주세요');</script>");
					p.flush();
					okay=true;
				}else if(accessflag.equalsIgnoreCase("Y")) {
					System.out.println("accessflag : "+accessflag);
					p.print("<script> alert('자신의 일정을 상대방과 교환할 수 있습니다.');</script>");
					p.flush();
					okay=true;
				}else {
					System.out.println("accessflag : "+accessflag);
					p.print("<script> alert('자신의 일정을 확인해 주세요');</script>");
					p.flush();
					okay=true;
				}

				model.addAttribute("WORK_START", WORK_START);
				model.addAttribute("WORK_END", WORK_END);
				model.addAttribute("id", session.getAttribute("id"));
				model.addAttribute("rank", session.getAttribute("rank"));
			}else {
				System.out.println("조건문false");
				PrintWriter p =  resp.getWriter();
				p.print("<script> alert('신청 날짜가 아닙니다');</script>");
				p.flush();
				okay=false;
				
			}
			
		}else if(session.getAttribute("rank").equals("man")){
			destination="schedule/manager_calendar";// 지금 이거 메소드로 따로 뺄 수 있을듯!! 
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("employee_rank", rank.equals("emp")?"G":"M");
			map.put("ws_code", ws_code);
			SchBasicDTO dtoManagerBasic=service_basic.schBasicChkSelect(map);
			
			 resp.setCharacterEncoding("utf-8");
		       resp.setContentType("text/html; charset=UTF-8");
			if(dtoManagerBasic==null) {
				okay=true;
	            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
	            model.addAttribute("WSDto", WSDto);
				
				PrintWriter p =  resp.getWriter();
				p.print("<script> alert('해당 직급의 일정기초사항이 설정되지 않았습니다. 사업장공지사항을 확인해주세요');</script>");
				p.flush();
				return "ws/select";
				
			}
				
			String schedule_basic_man_str=dtoManagerBasic.getSchbasic_json();
			
			JsonObject schedule_basic_obj = (JsonObject) parser.parse(schedule_basic_man_str);
			String APPLY_START= schedule_basic_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			String APPLY_END= schedule_basic_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			String WORK_END= schedule_basic_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			String WORK_START= schedule_basic_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
//			Date today_date =format.parse(format.format(new Date()));
//			Date start_date =format.parse(APPLY_START);
//			Date end_date =format.parse(APPLY_END);
			String year_month=WORK_START.substring(0,7);//자바스크립트로 가져오자
			SchDTO dto = new SchDTO();
			dto.setSch_month(year_month);
			dto.setWs_code(ws_code);
			System.out.println(timechk(APPLY_START,APPLY_END));
			System.out.println(timechk(APPLY_START,APPLY_END));
		      resp.setCharacterEncoding("utf-8");
		       resp.setContentType("text/html; charset=UTF-8");
			if(timechk(APPLY_START,APPLY_END)) {
				System.out.println("조건문true");
				
				String accessflag =service.selectAccess(dto);
				System.out.println("accessflag : "+accessflag);
				model.addAttribute("flag", accessflag);
				
				PrintWriter p =  resp.getWriter();
//				p.print("<script> alert('신청 날짜 입니다. 일정을 등록해 주세요');</script>");
//				p.flush();
//				okay=true;
				
				
				if(accessflag==null|| accessflag.equalsIgnoreCase("D")) {
					System.out.println("accessflag : "+accessflag);
					p.print("<script> alert('신청 날짜 입니다. 일정을 등록해 주세요');</script>");
					p.flush();
					okay=true;
				}else if(accessflag.equalsIgnoreCase("Y")) {
					System.out.println("accessflag : "+accessflag);
					p.print("<script> alert('자신의 일정을 상대방과 교환할 수 있습니다.');</script>");
					p.flush();
					okay=true;
				}else {
					System.out.println("accessflag : "+accessflag);
					p.print("<script> alert('자신의 일정을 확인해 주세요');</script>");
					p.flush();
					okay=true;
				}
				
				
				
				model.addAttribute("WORK_START", WORK_START);
				model.addAttribute("WORK_END", WORK_END);
				model.addAttribute("id", session.getAttribute("id"));
				model.addAttribute("rank", session.getAttribute("rank"));
//				- 직원이 신청페이지에 들어갈 때 기초설정데이터에 있는 근무일자 처음과 끝이 모델로 넘어가서
//				app.js에서 근무일자에 따라서 해당페이지 내에서만 이동할 수 있도록 설정하는거!!
			}else {
				System.out.println("조건문false");
				PrintWriter p =  resp.getWriter();
				p.print("<script> alert('신청 날짜가 아닙니다');</script>");
				p.flush();
				okay=false;
			}
		}else {
			
			
			System.out.println("조건문false");
			PrintWriter p =  resp.getWriter();
			p.print("<script> alert('정상적인 접근이 아닙니다.');</script>");
			p.flush();
			okay=false;
			
		}

		return (okay)?destination:"login/loginForm";
	}
	
	
	private boolean timechk(String start,String end) throws ParseException {
		boolean result = false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today_date =format.parse(format.format(new Date()));
		Date start_date =format.parse(start);
		Date end_date =format.parse(end);
		if(today_date.getTime()-start_date.getTime()>=0 && today_date.getTime()-end_date.getTime()<=0) {
			result= true;
		}
		return result;
	}
	
	private String[] findTime(String start_emp,String start_man,String end_emp,String end_man) throws ParseException {
		String[] start_end=new String[2];
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start_emp_date =format.parse(start_emp);
		Date start_man_date =format.parse(start_man);
		Date end_emp_date =format.parse(end_emp);
		Date end_man_date =format.parse(end_man);
		if(start_emp_date.getTime()-start_man_date.getTime()>=0) {
			start_end[0]=start_man;
			start_end[1]=start_emp;
		}else {
			start_end[0]=start_emp;
			start_end[1]=start_man;
		}
		if(end_emp_date.getTime()-end_man_date.getTime()>=0) {
			start_end[0]=end_man;
			start_end[1]=end_emp;
		}else {
			start_end[0]=end_emp;
			start_end[1]=end_man;
		}
		
		
		return start_end;
	}
	
	
	@RequestMapping(value = "/updateReviseable.do",method = RequestMethod.GET)
	public String chkReviseable(Model model,String chk,String start_month ,String end_month,HttpSession session) {
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");
		
		System.out.println("===========updateReviseable============");
		System.out.println(chk); 
		System.out.println(start_month); 
		System.out.println(end_month); 
		SchDTO dto = new SchDTO();
		dto.setSch_month(start_month);//아 이거 가져와야겠다 화면에서,,ㅎ
		dto.setWs_code(ws_code);//오예 세션에서 가져오고
		dto.setSch_reviseable(chk);
		service.updateAccess(dto);                       
		model.addAttribute("access",chk);
		System.out.println("=================================");
		return "redirect:goCalendarConfirm.do";
	}
	
	

	
	@RequestMapping(value="/goCalendarConfirm.do",method = RequestMethod.GET)
	public String EmployeeOrManagerV(Model model,HttpSession session,HttpServletResponse resp) throws IOException, ParseException {
//		session.setAttribute("rank", "own");//지금 직원이라고 생각합시다~~ 
		
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");
		System.out.println(rank);
		System.out.println(user.getUser_type());
		String destination="";
		boolean okay=false;
		
		//직원꺼 찾자
		HashMap<String, Object> map_emp = new HashMap<String,Object>();
//		map.put("employee_rank", rank.equals("emp")?"G":"M");
		map_emp.put("employee_rank", "G");//디폴트로 일단 직원의 일정으로 가져오는걸로,,!!
		map_emp.put("ws_code", ws_code);
		SchBasicDTO dtoEmpBasic=service_basic.schBasicChkSelect(map_emp);
		System.out.println("dtoEmpBasic : "+dtoEmpBasic);
		//매니저꺼 찾자
		HashMap<String, Object> map_man = new HashMap<String,Object>();
//		map.put("employee_rank", rank.equals("emp")?"G":"M");
		map_man.put("employee_rank", "M".trim());//디폴트로 일단 직원의 일정으로 가져오는걸로,,!!
		map_man.put("ws_code", ws_code);
		SchBasicDTO dtoManBasic=service_basic.schBasicChkSelect(map_man);
		System.out.println("dtoManBasic : "+dtoManBasic);
		String schedule_basic_emp_str="";
		String schedule_basic_man_str="";
		JsonParser parser = new JsonParser();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        String APPLY_START="";
        String APPLY_END="";
        String WORK_END="";
        String WORK_START="";
        
        
        
        
		if(dtoManBasic==null && dtoEmpBasic==null) {
			System.out.println("rank="+rank);
			PrintWriter p =  resp.getWriter();
			p.print("<script> alert('기초사항이 설정되지 않았습니다. 기초사항 설정 후 일정관리를 진행하여 주세요');</script>");
			p.flush();
			okay=true;
            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
			destination="ws/select";
		}else if(dtoManBasic==null && dtoEmpBasic!=null){//직원의 기초사항만 등록되었을때 
			
			if(rank==null) {
				destination="schedule/owner_confirm";
			}else {
				destination="schedule/manager_confirm";
			}
//			destination="schedule/owner_confirm";
			schedule_basic_emp_str=dtoEmpBasic.getSchbasic_json();
			JsonObject schedule_basic_emp_obj = (JsonObject) parser.parse(schedule_basic_emp_str);
			APPLY_START= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			APPLY_END= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			WORK_END= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			WORK_START= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
			PrintWriter p =  resp.getWriter();
			p.print("<script> alert('매니저의 기초사항이 설정되지 않았습니다.설정해주세요');</script>");
			p.print("<script> alert('주인님들어가십니다~');</script>");
			p.flush();
			okay=true;
            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
            
            model.addAttribute("APPLY_START", APPLY_START);
            model.addAttribute("APPLY_END", APPLY_END);
    		model.addAttribute("WORK_START", WORK_START);
    		model.addAttribute("WORK_END", WORK_END);
    		model.addAttribute("id", session.getAttribute("id"));
    		model.addAttribute("rank", session.getAttribute("rank"));
		}else if(dtoEmpBasic==null && dtoManBasic!=null){
			if(rank==null) {
				destination="schedule/owner_confirm";
			}else {
				destination="schedule/manager_confirm";
			}
			schedule_basic_man_str=dtoManBasic.getSchbasic_json();
			JsonObject schedule_basic_man_obj = (JsonObject) parser.parse(schedule_basic_man_str);
			APPLY_START= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			APPLY_END= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			WORK_END= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			WORK_START= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
			PrintWriter p =  resp.getWriter();
			p.print("<script> alert('직원의 기초사항이 설정되지 않았습니다.설정해주세요');</script>");
			p.print("<script> alert('주인님들어가십니다~');</script>");
			p.flush();
			okay=true;
            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
            
            model.addAttribute("APPLY_START", APPLY_START);
            model.addAttribute("APPLY_END", APPLY_END);
    		model.addAttribute("WORK_START", WORK_START);
    		model.addAttribute("WORK_END", WORK_END);
    		model.addAttribute("id", session.getAttribute("id"));
    		model.addAttribute("rank", session.getAttribute("rank"));
    		
		}else {
			if(rank==null) {
				destination="schedule/owner_confirm";
			}else {
				System.out.println("매니저컨펌으로 감!! ");
				destination="schedule/manager_confirm";
			}
			schedule_basic_emp_str=dtoEmpBasic.getSchbasic_json();
			schedule_basic_man_str=dtoManBasic.getSchbasic_json();
			JsonObject schedule_basic_emp_obj = (JsonObject) parser.parse(schedule_basic_emp_str);
			String APPLY_START_emp= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			String APPLY_END_emp= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			String WORK_END_emp= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			String WORK_START_emp= schedule_basic_emp_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
			JsonObject schedule_basic_man_obj = (JsonObject) parser.parse(schedule_basic_man_str);
			String APPLY_START_man= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("APPLY_START").toString().replace("\"", "");
			String APPLY_END_man= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("APPLY_END").toString().replace("\"", "");
			String WORK_END_man= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("WORK_END").toString().replace("\"", "");
			String WORK_START_man= schedule_basic_man_obj.get("BASIC").getAsJsonObject().get("WORK_START").toString().replace("\"", "");
			String[] start_end_apply=findTime(APPLY_START_emp, APPLY_END_emp, APPLY_START_man, APPLY_END_man);
			String[] start_end_work=findTime(WORK_START_emp, WORK_END_emp, WORK_END_man, WORK_START_man);
			APPLY_START=start_end_apply[0];
			APPLY_END=start_end_apply[1];
			WORK_START=start_end_work[0];
			WORK_END=start_end_work[1];
			PrintWriter p =  resp.getWriter();
			p.print("<script> alert('주인님들어가십니다~');</script>");
			p.flush();
			okay=true;
            WorkSpaceDTO WSDto=service_ws.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
            
            model.addAttribute("APPLY_START", APPLY_START);
            model.addAttribute("APPLY_END", APPLY_END);
    		model.addAttribute("WORK_START", WORK_START);
    		model.addAttribute("WORK_END", WORK_END);
    		model.addAttribute("id", session.getAttribute("id"));
    		model.addAttribute("rank", session.getAttribute("rank"));
		}
		
		
		
		return (okay)?destination:"login/loginForm";
	}
}
