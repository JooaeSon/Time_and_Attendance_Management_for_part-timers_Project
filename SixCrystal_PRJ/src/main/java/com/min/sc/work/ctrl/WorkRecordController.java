package com.min.sc.work.ctrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.min.sc.emp.dtos.EmpDTO;
import com.min.sc.emp.dtos.EmpUserDTO;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.work.dtos.WMDto;
import com.min.sc.work.dtos.WorkRecordDto;
import com.min.sc.work.model.IService_Work;
import com.min.sc.ws.dtos.WorkSpaceDTO;


@Controller
public class WorkRecordController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IService_Work work_service;
	
	//static private String user_id="gang";//나중에 이부분은 session객체에서 받아오기
	//현재 접속하고 있는 유저와 일치하는 애 가져오기
	
	Map<String, Object> schmap=new HashMap<String, Object>();
	
	
	@RequestMapping(value="/personalWork.do", method=RequestMethod.GET)
	public String personalPage(Model model, HttpSession session) { //개인조회 하기
		String WORK="";
		UserInfoDTO user=(UserInfoDTO) session.getAttribute("user");
		String ws_code=(String) session.getAttribute("ws_code");
		
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM");
		String dateTemp=sDate.format(new Date());//현재 년 월에 해당하는 기록을 조회
		
		schmap.put("sch_month", dateTemp);
		schmap.put("ws_code",ws_code);
		schmap.put("user_id", user.getUser_id());
			
		WORK="["+work_service.selectPersonal(schmap)+"]";
		System.out.println("WORK: "+WORK);
		if(!WORK.equals("[null]")) {
			System.out.println("***");
			List<WorkRecordDto> WorkPersonalList=JsonToString(WORK);
			
			model.addAttribute("WorkPersonalList", WorkPersonalList);//개인에 대한 세부 정보를 넘긴다.
		}
		
		return "work/personalWork";
	}
	
	
	
	@RequestMapping(value="/MultiWork.do", method=RequestMethod.GET)
	public String testPage(Model model, HttpSession session) { //고용주, 매니저 하위직원 조회
		String WORK="";
		
		List<WMDto> worklists=new ArrayList<WMDto>();
//		String test="[{\"13\":[{\"일정\":[{\"오전\":\"00:00\"},{\"오후\":\"00:01\"},{\"야간\":\"00:00\"},{\"초과\":\"\"}]}"
//				+","+"{\"출퇴\":[{\"출근\":\"202006132055\"},{\"퇴근\":\"202006132056\"}]}"
//						+","+ "{\"상태\":[{\"출근상태\":\"비정상\"},{\"퇴근상태\":\"비정상\"}]}]}]";
		
		UserInfoDTO user=(UserInfoDTO) session.getAttribute("user");
		String ws_code=(String) session.getAttribute("ws_code");
		String rank=(String) session.getAttribute("rank");
		
		String userType=user.getUser_type();//사용자가 고용주인지 비고용주인지 분류
		
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM");
		String dateTemp=sDate.format(new Date());//현재 년 월에 해당하는 기록을 조회
		System.out.println("dateTemp:"+dateTemp);
		if(userType.equals("employer")) {//고용주 일때
			schmap.put("sch_month", dateTemp);
			schmap.put("ws_code", ws_code);
			schmap.put("employee_rank", "admin");
			System.out.println("#######관리자로 들어왔다######");
		
		}else if(rank.equals("man")){//매니저일 때 (일반 직원 정보를 조회한다.)
			System.out.println("#######매니저로 들어왔다######");
			schmap.put("sch_month", dateTemp);
			schmap.put("ws_code", ws_code);
			schmap.put("employee_rank", "man");
			schmap.put("finder_rank", "emp");//매니저는 EMP를 검색한다. 지금은 임시로 man으로 해놓음
		}
		
		worklists=(List<WMDto>) work_service.selectLowRank(schmap);//한 행씩 뽑기-그달의 서로다른
		//사업장의 다른 사람들
		//worklists에 들어있는 것
		//user_id, employee_rank, work_check근태 기록(날짜, 출근시각, 출근상태, 퇴근시각, 퇴근상태)
		
		System.out.println("worklists:"+worklists);
		
		List<List<WorkRecordDto>> multiList=new ArrayList<List<WorkRecordDto>>();
		
		List<WorkRecordDto> rdlists=new ArrayList<WorkRecordDto>();	
		
		
		for(WMDto workDto : worklists) {//한사람당 꺼내기 
			
			System.out.println("***********************************");
			WORK="["+workDto.getWork_check()+"]";
			
			List<WorkRecordDto> happylists=JsonToString(WORK);
			
			System.out.println("happylists:"+happylists);
			//happylists:[WorkRecordDto [day=17, startDay=202006172153, endDay=202006172153, startState=지각, endState=조퇴, user_id=null, employee_rank=null],
			//WorkRecordDto [day=17, startDay=202006172159, endDay=202006172159, startState=지각, endState=조퇴, user_id=null, employee_rank=null],
			//WorkRecordDto [day=17, startDay=202006172210, endDay=202006172210, startState=지각, endState=조퇴, user_id=null, employee_rank=null]]
			int i=1;
			for(WorkRecordDto dto: happylists) {
				WorkRecordDto rd=new WorkRecordDto();
				rd.setSeq(i);
				i++;
				rd.setUser_id(workDto.getUser_id());
				rd.setEmployee_rank(workDto.getEmployee_rank());
				rd.setDay(dto.getDay());
				rd.setStartDay(dto.getStartDay());
				rd.setStartState(dto.getStartState());
				rd.setEndDay(dto.getEndDay());
				rd.setEndState(dto.getEndState());
				rdlists.add(rd);
		
				System.out.println("rdlists:"+rdlists);

			}

			
			multiList.add(rdlists);
			
		}
		//System.out.println("multilists: "+multiList);
		model.addAttribute("rdlists", rdlists);
	
		//model.addAttribute("multiList", multiList);

		
		return "work/MultiWork";
	}
	
	
	
	public List<WorkRecordDto> JsonToString(String WORK) {
			
		JsonParser parser = new JsonParser();
		JsonElement WORKjsonElement = (JsonElement) parser.parse(WORK); //json 파싱
		System.out.println(WORKjsonElement);
		System.out.println("WORK:"+WORK);
		
		List<WorkRecordDto> record=new ArrayList<WorkRecordDto>();
		
		for(int i=0; i<WORKjsonElement.getAsJsonArray().size(); i++) {
			
			JsonObject dayRecord=(JsonObject)WORKjsonElement.getAsJsonArray().get(i);
			System.out.println(i+": "+dayRecord);
			
			for(int j=1 ; j < 32 ; j++) {//한달의 매일매일 기록을 뽑기
				JsonArray c=(JsonArray)dayRecord.getAsJsonObject().get(String.valueOf(j));
				
				if(c!=null) {//일정이 있을 경우
						
						WorkRecordDto WorkrecordDto = new WorkRecordDto();
					
						JsonElement workCheck=c.getAsJsonArray().get(1).getAsJsonObject().get("출퇴");
						JsonElement workState=c.getAsJsonArray().get(2).getAsJsonObject().get("상태");
						
						System.out.println("workCheck"+workCheck);
						System.out.println("workState"+workState);
						
						String startDay=workCheck.getAsJsonArray().get(0).getAsJsonObject().get("출근").getAsString();
						String endDay=workCheck.getAsJsonArray().get(1).getAsJsonObject().get("퇴근").getAsString();
						
						String startState=workState.getAsJsonArray().get(0).getAsJsonObject().get("출근상태").getAsString();
						String endState=workState.getAsJsonArray().get(1).getAsJsonObject().get("퇴근상태").getAsString();
						
						
						WorkrecordDto.setDay(String.valueOf(j));
						WorkrecordDto.setStartDay(startDay);
						WorkrecordDto.setEndDay(endDay);
						WorkrecordDto.setStartState(startState);
						WorkrecordDto.setEndState(endState);
						
						record.add(WorkrecordDto);
						
				}//if
				
			}//for
		}//for
		System.out.println("record:"+record);
		return record;
			
	}//json 함수

}
