package com.min.sc.work.ctrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.work.dtos.WMDto;
import com.min.sc.work.dtos.WorkRecordDto;
import com.min.sc.work.model.IService_Work;

@Controller
public class WorkRcrdModifyCtrl {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IService_Work work_service;
	
	public static Map<String, Object> schmap=new HashMap<String, Object>();
	public static Map<String, Object> DayMap = new HashMap<String, Object>();
	
		//수정완료하기
		@RequestMapping(value="/modifyfinish.do", method=RequestMethod.POST)
		public String modifyfinish(WorkRecordDto wrdto, HttpSession session) throws ParseException {
			logger.info("Welcome modifyfinish.do : \t {}");
			System.out.println("수정 완료하기: "+wrdto);
			//일단 수정을 완료하려면 받은 seq에서 시작시간을 update 한다.		
			String WORK="";
			String WorkStream="";//나중에 수정해줄 때 쓸 것
			
			List<WMDto> worklists=new ArrayList<WMDto>();
			
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
					
			
					System.out.println("rdlists:"+rdlists);
					
					String seqTemp=rd.getSeq()+"";//현재 for문 돌고있는 seq
					String modifySeq=wrdto.getSeq()+""; //내가 변경하고자하는 seq
					
					if(modifySeq.equals(seqTemp)) {//만약 같다면 변경
						rd.setStartDay(wrdto.getStartDay());
						//rd.setEndDay(endDay);
					}
					rdlists.add(rd);
					
				}
	
			}
			
			System.out.println("rdlists##########:"+rdlists);
		//여기서부터가 시작
			stringToJson(rdlists);
			
			return "redirect:/MultiWork.do";
		}
		
		//String ->json으로
		public String stringToJson(List<WorkRecordDto> rdlists) throws ParseException {
			//{"21":[{"일정":[{"오전":"00:00"},{"오후":"00:01"},{"야간":"00:00"},{"초과":""}]},{"출퇴":[{"출근":"202006211647"},{"퇴근":"202006211648"}]},
			//{"상태":[{"출근상태":"지각"},{"퇴근상태":"조퇴"}]}]},
			
			//{"21":[{"일정":[{"오전":"00:00"},{"오후":"00:00"},{"야간":"00:00"},{"초과":""}]},
			//{"출퇴":[{"출근":"202006211651"},{"퇴근":"202006211651"}]},{"상태":[{"출근상태":"지각"},{"퇴근상태":"조퇴"}]}]}
			

			String SCschedule= work_service.workInfoSelect(schmap);//해당 월, 사업장 스케줄
			System.out.println("SCschedule"+SCschedule);//타입은 String 타입이다.
			
			if(SCschedule !=null) {
				//string을 json객체로 바꾸어주는 방법
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(SCschedule); //근태 string 타입을 obj에 넣어준다.
				JSONArray jsonarray = (JSONArray) obj;
		
				System.out.println("**jsonarray**"+jsonarray); //요소는 각각 schedule하고 day로 표기 
				
				
				
				int size=jsonarray.size();
		
				for(int i=0;i<size;i++){
					//MAP 만들어서 그 날의 ::스케줄 이렇게 묶어줘서 언제 어디서든지 꺼내주기 하기
		
					JSONObject dayinfo = (JSONObject)jsonarray.get(i);
		
					System.out.println("----- "+i+"번째 인덱스 값 -----");
					System.out.println("오늘 정보: "+dayinfo);
		
					String today=dayinfo.get("day")+"";
					String schedule=dayinfo.get("schedule")+"";
		
					System.out.println(i+"번째 인덱스 today: "+today);
					System.out.println(i+"번째 인덱스 schedule: "+schedule);
		
					DayMap.put(today,schedule); //이렇게 날짜별로 스케줄을 집어넣는 작업 끝!
		
					//map을 인덱스 별로 나옴. 그러니까 그날 하루 사람들이 오고간 정보
					//schedule하나에 많은 정보들이 들어있음(user_id, start, end)				
				}
			
			}
			
			//rdlists##########:[WorkRecordDto [seq=1, day=21, startDay=202006211644, endDay=202006211648, startState=지각, endState=조퇴, user_id=emptest01, employee_rank=emp], 
			//WorkRecordDto [seq=2, day=21, startDay=202006211651, endDay=202006211651, startState=지각, endState=조퇴, user_id=emptest01, employee_rank=emp]]
			
			JsonParser parser = new JsonParser();
			
			for(WorkRecordDto dto: rdlists) {
				String day=dto.getDay();
				String daySch=(String) DayMap.get(day);
				JsonElement jsonElement = (JsonElement) parser.parse(daySch);
				System.out.println("그날일정 json으로/수정을위한 jsonElement:"+jsonElement);//해당일의 일정 json으로 가져오기
				
				
				int user_count=0;
//				if(dto.getUser_id().equals(User_id))
//				for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) { //배열안에 user_id개수 세기
//					JsonObject user_chk = (JsonObject) jsonElement.getAsJsonArray().get(i);
//					String User_id=user_chk.get("title").getAsString();
//					if(dto.getUser_id().equals(User_id)) {
//						user_count++; //이 for문을 다 도는 순간 내가 오늘하루 몇개의 스케줄을 가지고 있는지 판명난다.
//					}
//				}
//				
//				for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
//					JsonObject dayby = (JsonObject) jsonElement.getAsJsonArray().get(i);
//					System.out.println(i+"번째 dayby jsonElement:"+dayby);
//					String User_id=dayby.get("title").getAsString();
//					String start=dayby.get("start").getAsString().replaceAll(":","").replaceAll("-", "").replaceAll("T", "");
//					String end=dayby.get("end").getAsString().replaceAll(":","").replaceAll("-", "").replaceAll("T", "");
//					String calendarId=dayby.get("calendarId").getAsString();
//
//					System.out.println("start:"+start);
//					System.out.println("end:"+end);
//					System.out.println("realTime:"+realTime);
//					System.out.println("calendarId:"+calendarId);
//
//					if(dto.getUser_id().equals(User_id)) {//만약 스케줄에도 day에 해당하는 일정이있고 이름도 있다면?
//						//user_id가 한명이  두번이상 있다면 매번 할때마다 realtime을 갱신해야된다.
//						System.out.println("오늘 출근 체크가 되었습니다./이름도 존재하고 스케줄도 존재");
//						
//						if(user_count==1) { //한명이 한개 스캐줄 가지고 있을 경우
//							//초기 차이값	
//							Tstart=start;
//							Tend=end;
//							Tuser_id=User_id;
//							Tcalendar_id=calendarId;
//						}
//
//
//						if(user_count>=2) { //한명이 두개 이상 일정을 가지고 있을 경우 제일 가까운 시간으로 처리(출근할때)
//
//							if((Long.parseLong(realTime)<=Long.parseLong(end))&&
//									(Long.parseLong(start)<=Long.parseLong(realTime))) {//realTime이 사이 시간이라면
//								Tstart=start;
//								Tend=end;
//								Tuser_id=User_id;
//								Tcalendar_id=calendarId;
//							}	
//
//							else if((Long.parseLong(start)>Long.parseLong(realTime))) {//아직 끝나기 전 시간이라면
//								if(min>Math.abs(Long.parseLong(realTime)-Long.parseLong(start))) {
//									min=Math.abs(Long.parseLong(realTime)-Long.parseLong(start));//실제 시간과 스케줄 출근시간 차이
//									Tstart=start;
//									Tend=end;
//									Tuser_id=User_id;
//									Tcalendar_id=calendarId;
//								}
//							}else {//realTime>end 이미 다 끝난 시점에서는 기억하는 시간이 의미가 없다. 출근체크가 안되기 때문->결석처리
//								Tstart="000000000000";
//								Tend="000000000000";
//								Tuser_id=User_id;
//								Tcalendar_id=calendarId;
//							}	
//						}
//
//					}
//				}
			}
			
			
			
			return "";
		}
		
		
		
		//수정 폼 ajax
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/modifyForm.do", method=RequestMethod.POST,
		produces = "application/text; charset=UTF-8;") //마임 타입
		@ResponseBody
		public String modifyForm(String seq, HttpSession session) {
			logger.info("Welcome modifyForm,{}");
			System.out.println("welcom to modifyForm");
			JSONObject json = new JSONObject();
			String WORK="";
			
			List<WMDto> worklists=new ArrayList<WMDto>();
//			String test="[{\"13\":[{\"일정\":[{\"오전\":\"00:00\"},{\"오후\":\"00:01\"},{\"야간\":\"00:00\"},{\"초과\":\"\"}]}"
//					+","+"{\"출퇴\":[{\"출근\":\"202006132055\"},{\"퇴근\":\"202006132056\"}]}"
//							+","+ "{\"상태\":[{\"출근상태\":\"비정상\"},{\"퇴근상태\":\"비정상\"}]}]}]";
			
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
					
					String seqTemp=rd.getSeq()+"";
					
					if(seq.equals(seqTemp)) {
						json.put("seq", rd.getSeq());
						json.put("startDay", rd.getStartDay());
						json.put("endDay" ,rd.getEndDay());
						break;
					}
					
				}
				
				
				multiList.add(rdlists);
				
			}
	
		logger.info("Welcome modifyForm 결과,{}", json.toString());
		
			
			return json.toString();
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
