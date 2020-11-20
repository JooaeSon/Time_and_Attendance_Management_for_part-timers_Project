package com.min.sc.work.ctrl;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.work.model.IService_Work;
import com.min.sc.ws.dtos.WorkSpaceDTO;

@Controller
public class WorkTimeCtrl {

	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IService_Work work_service; 

	@Autowired
	ServletContext context;
	
	public static Map<String, Object> DayMap = new HashMap<String, Object>();//날짜:

	public static Map<String, Object> schmap=new HashMap<String, Object>();

	//로직에 필요한 string형 json
	public static String commuteJSON="";
	public static String leaveWorkJSON="";

	//전체 json에 들어갈 것들
	public static String startChk_str="";//출근체크
	public static String startState="";//출근상태
	public static String endChk_str="";//퇴근체크
	public static String endStateChk_str="";//퇴근 상태

	String Tstart="";
	String Tend="";
	String Tuser_id="";
	String Tcalendar_id="";

	//remote-ip:모바일이든 다른 디바이스에서 접근하는 ip주소
	//commonIp:앞의 일치하는 번호까지만 잘라서 사업장db의 등록된 wifi주소를 비교한다.
	public boolean ChkIp(ServletRequest req, String workspacesession) {
		//아이피 주소 가져오기
		//InetAddress local;
		String remoteAddr = StringUtils.defaultString(req.getRemoteAddr(), "-");
		String userIP=remoteAddr.substring(0,9);
		System.out.println(userIP);
		//172.30.1.41
		String commonIP=work_service.wifiInfoSelect(workspacesession);
		String commonIPPP=commonIP.substring(0, 9);
		System.out.println("userIP"+userIP);
		System.out.println("commonIPPP:"+commonIPPP);
		/////////////////////////////////////////
		if(userIP.equalsIgnoreCase(commonIPPP)) {
			logger.info("아이피가 일치합니다.");
			return true;
		}else {
			logger.info("아이피가 일치하지 않습니다.");
			return false;
		}


	}
	
	
	
	//session객체에서 user_id비교하기. 
	public boolean chkName(String User_id) {
		
		String name=(String) schmap.get("user_id"); //세션에서 가져온 아이디
		if(name.equals(User_id)) { //JSON에서 가져온 아이디(직원아이디) ->이 가져온 아이디는 근태 테이블 user_id에다 집어넣는다.
			logger.info("오늘의 스케줄에 존재합니다.");
			return true;
		}else {
			logger.info("오늘의 스케줄에 존재하지 않습니다.");
			return false;
		}

	}

	//출근 버튼을 눌렀을 때 그날의 출근시간과 비교(일단 여기까지 왔따는 것은 출근은 됬다는 뜻. 정상/비정상(지각) 판단)
	public String commuteChk(String YYYYMMDDtime) throws org.json.simple.parser.ParseException, ParseException {

		//찍은날이 오늘인지 확인한다. 오늘이 맞다면 map키로 해당날짜를 불러오기
		//실제 찍은날만 조회할 수 있다.
		String DD=YYYYMMDDtime.substring(8,10);//실제 날
		//System.out.println("YYYYMMDDtime"+YYYYMMDDtime);
		
		String daySchedule=(String) DayMap.get(DD);//애는 map임
		//여기에서 user_id가 맞다면 start와 end를 구한다.
		System.out.println("daySchedule"+daySchedule);
		
		String pastchk=work_service.selectWorkcheck(schmap);//그 달의 사업장의 근태 정보가 있는지
		System.out.println("테이블 생성 가능한지:"+pastchk);
		
		
		
		if(daySchedule!=null) {//일정이 있다
			
		//daySchedule.get("");
		Gson gson=new Gson();
		JsonObject startChkObj=new JsonObject();
		String startChk_str="";
		JsonObject startStateChkObj=new JsonObject();
		String startState_str="";


		int user_count=0;
		long min=1000000000;
		String Tstart=""; //기억되는 출근시간
		String Tend=""; //기억되는 퇴근시간
		String Tuser_id="";//기억되는 user_id
		String realTime=YYYYMMDDtime.substring(0,16).replaceAll(":","").replaceAll("-", "").replaceAll("T", "");
		String Tcalendar_id="";

		//startChk_str+"x"+startState_str+"x"+Tstart+"x"+Tend+"x"+Tuser_id

		
			//gSon
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = (JsonElement) parser.parse(daySchedule);
			System.out.println("jsonElement:"+jsonElement);

			//List<String> Userlists=new ArrayList<String>();



			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) { //배열안에 user_id개수 세기
				JsonObject user_chk = (JsonObject) jsonElement.getAsJsonArray().get(i);
				String User_id=user_chk.get("title").getAsString();
				if(chkName(User_id)) {
					user_count++; //이 for문을 다 도는 순간 내가 오늘하루 몇개의 스케줄을 가지고 있는지 판명난다.
				}
			}
			
			if(user_count>=1) {
				
				if(DD.equals("1") || pastchk==null) { //매월 1일이거나 그달의 정보 자체가 없을 경우
					//------------테이블에 담아줄 기본 정보------------------//
					Map<String, Object> WORKinfo_Basic=work_service.workInfoSet(schmap);//근무일정코드, 사업장 코드, 월
					WORKinfo_Basic.put("USER_ID", schmap.get("user_id"));
					System.out.println("WORKinfo_Basic"+WORKinfo_Basic);
					//테이블에 기본 설정 담아주기
					boolean isc=work_service.workInfobasic_Insert(WORKinfo_Basic);//기본일정을 근태 테이블에 넣어줄지 말지 성공여부
					System.out.println("테이블 기본정보 만들기 성공:"+isc);
				}
				
				
			}

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				JsonObject dayby = (JsonObject) jsonElement.getAsJsonArray().get(i);
				System.out.println(i+"번째 dayby jsonElement:"+dayby);
				String User_id=dayby.get("title").getAsString();
				String start=dayby.get("start").getAsString().replaceAll(":","").replaceAll("-", "").replaceAll("T", "");
				String end=dayby.get("end").getAsString().replaceAll(":","").replaceAll("-", "").replaceAll("T", "");
				String calendarId=dayby.get("calendarId").getAsString();

				System.out.println("start:"+start);
				System.out.println("end:"+end);
				System.out.println("realTime:"+realTime);
				System.out.println("calendarId:"+calendarId);

				if(chkName(User_id)) {//만약 스케줄에도 day에 해당하는 일정이있고 이름도 있다면?
					//user_id가 한명이  두번이상 있다면 매번 할때마다 realtime을 갱신해야된다.
					System.out.println("오늘 출근 체크가 되었습니다./이름도 존재하고 스케줄도 존재");
					
					if(user_count==1) { //한명이 한개 스캐줄 가지고 있을 경우
						//초기 차이값	
						Tstart=start;
						Tend=end;
						Tuser_id=User_id;
						Tcalendar_id=calendarId;
					}


					if(user_count>=2) { //한명이 두개 이상 일정을 가지고 있을 경우 제일 가까운 시간으로 처리(출근할때)

						if((Long.parseLong(realTime)<=Long.parseLong(end))&&
								(Long.parseLong(start)<=Long.parseLong(realTime))) {//realTime이 사이 시간이라면
							Tstart=start;
							Tend=end;
							Tuser_id=User_id;
							Tcalendar_id=calendarId;
						}	

						else if((Long.parseLong(start)>Long.parseLong(realTime))) {//아직 끝나기 전 시간이라면
							if(min>Math.abs(Long.parseLong(realTime)-Long.parseLong(start))) {
								min=Math.abs(Long.parseLong(realTime)-Long.parseLong(start));//실제 시간과 스케줄 출근시간 차이
								Tstart=start;
								Tend=end;
								Tuser_id=User_id;
								Tcalendar_id=calendarId;
							}
						}else {//realTime>end 이미 다 끝난 시점에서는 기억하는 시간이 의미가 없다. 출근체크가 안되기 때문->결석처리
							Tstart="000000000000";
							Tend="000000000000";
							Tuser_id=User_id;
							Tcalendar_id=calendarId;
						}	
					}

				}
			}//for문 끝남->그날 스케줄 기록탐색 다 끝난거임

			System.out.println("당신이 출근할 시간Tstart:"+Tstart);
			System.out.println("당신이 퇴근할 시간Tend:"+Tend);
			System.out.println("당신이 현재 접속되어 있는 id:"+Tuser_id);
			System.out.println("당신이 현재 접속되어있는 Tcalendar_id:"+Tcalendar_id);

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				JsonObject dayby = (JsonObject) jsonElement.getAsJsonArray().get(i);
				String User_id=dayby.get("title").getAsString();	

				if(chkName(User_id)) {
					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
					Date start_time=dateFormat.parse(Tstart); //가장 가까운 시간을 넣어준다.
					Date end_time=dateFormat.parse(Tend);
					Date real_time=dateFormat.parse(realTime);
					System.out.println(start_time+":"+end_time+":"+"real_time#####"+real_time);

					//start시간 전일 때 -> 정시로 출근체크된다. 퇴근 체크는 되지 않는다.
					//(시간이 지날 수록 숫자가 더 크다.)
					if(real_time.getTime()<=start_time.getTime()) {
						System.out.println("출근시간 전 입니다.");
						//정시로 출근시간이 찍히는 출근이 가능한 시간/ 퇴근 체크 안됨.
						startChkObj.addProperty("출근", Tstart);
						startChk_str=gson.toJson(startChkObj);
						System.out.println("startChk_str"+startChk_str);

						///시간 찍기
						//정상 출근인지 확인하기
						startStateChkObj.addProperty("출근상태", "정상");
						startState_str=gson.toJson(startStateChkObj);

					}
					//start시간 후 이고 end시간 전일 때 ->지각 처리 된다. 이때 퇴근체크하면 조퇴 처리 된다.
					else if((start_time.getTime()<real_time.getTime())
							&&(real_time.getTime()<end_time.getTime())) {
						System.out.println("근무 중 시간 입니다. -지각");

						startChkObj.addProperty("출근", realTime); //정상체크는 안되고 지각일경우 실제시간부터
						//분 단위로 계산한다.
						startChk_str=gson.toJson(startChkObj);

						startStateChkObj.addProperty("출근상태", "지각");
						startState_str=gson.toJson(startStateChkObj);

					}
					//end 시간 후 일때 -> 출근 체크 안됨, 출근체크가 되어 있으면 퇴근 체크 가능
					else if(real_time.getTime()>=end_time.getTime()){
						System.out.println("퇴근시간 후 입니다. 출근할 수 없습니다.");
						//					startChkObj.addProperty("출근", "");
						//					startChk_str=gson.toJson(startChkObj);
						//					
						//					startStateChkObj.addProperty("출근상태", "");
						//					startState_str=gson.toJson(startStateChkObj);

					}

				}
			}//for문



			//일정표와 비교한 결과값이 들어가게 된다. 만약 내 이름이 일정표에 존재하지 않고 일하는 날이 아니라면 "xxx"만 리턴 되게 된다.(초기값이 ""+"x"+""이기 때문)
			return startChk_str+"x"+startState_str+"x"+Tstart+"x"+Tend+"x"+Tuser_id+"x"+Tcalendar_id; //기억되는 출근시간(Tstart)과 기억되는 퇴근시간(Tend)을 같이 보내준다.

		}else {
			return "xxxxx";
		}
	}



	//퇴근 버튼을 눌렀을 떄 그날의 퇴근 시간 비교(일단 여기까지는 퇴근 완료. 판단을 통하여 정상/비정상(조퇴)판단)
	public String leaveWork(String YYYYMMDDtime, String startState, String Tstart, String Tend,
			String Tuser_id, String Tcalendar_id) throws ParseException {
		//찍은날이 오늘인지 확인한다. 오늘이 맞다면 map키로 해당날짜를 불러오기

		//gSon
		JsonParser parser = new JsonParser();
		JsonElement startStatejson;

		Gson gson=new Gson();
		JsonObject endChkObj=new JsonObject();
		String endChk_str="";
		JsonObject endStateChkObj=new JsonObject();
		String endStateChk_str="";

		System.out.println("startState::leaveWork::"+startState);

		//startState
		//				if(startState!=null) {
		startStatejson=(JsonElement)parser.parse(startState);
		//아예 출근부터 하지 않았다면 startState가 null일 수밖에 없다. 파싱 자체가 안될 것임!**********



		System.out.println("startStatejson: "+(startStatejson.getAsJsonObject().get("출근상태")));

		if(!(startStatejson.getAsJsonObject().get("출근상태").getAsString()).equals("")) { //출근 상태가 null이 아닐 때: 출근이 먼저 선행되어야 된다는 이야기
			//출근 상태가 있을 때만


			String User_id=Tuser_id;
			String realTime=YYYYMMDDtime.substring(0,16).replaceAll(":","").replaceAll("-", "").replaceAll("T", "");

			System.out.println("Tstart:"+Tstart);
			System.out.println("Tend:"+Tend);
			System.out.println("realTime:"+realTime);

			if(chkName(User_id)) {//만약 스케줄에도 day에 해당하는 일정이있고 이름도 있다면?
				System.out.println("오늘 퇴근 체크가 되었습니다./이름도 존재하고 스케줄도 존재");

				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
				Date start_time=dateFormat.parse(Tstart);
				Date end_time=dateFormat.parse(Tend);
				Date real_time=dateFormat.parse(realTime);
				System.out.println(start_time+":"+end_time+":"+"real_time#####"+real_time);


				//						//하루에 같은 유저가 두개의 스케줄을 가질 경우를 대비하여 lists.add를 사용한다.
				//						Userlists.add(i, User_id);
				//						Startlists.add(i, start);

				//start시간 전일 때 -> 정시로 출근체크된다. 퇴근 체크는 되지 않는다.
				//(시간이 지날 수록 숫자가 더 크다.)
				if(real_time.getTime()<=start_time.getTime()) {
					System.out.println("출근시간 전 입니다. 퇴근 안됨");
					//정시로 출근시간이 찍히는 출근이 가능한 시간/ 퇴근 체크 안됨.
					//								endChkObj.addProperty("퇴근", ""); 
					//								endChk_str=gson.toJson(endChkObj);
					//								endStateChkObj.addProperty("퇴근상태", ""); 
					//								endStateChk_str=gson.toJson(endStateChkObj);

				}
				//start시간 후 이고 end시간 전일 때 ->지각 처리 된다. 이때 퇴근체크하면 조퇴 처리 된다.
				else if((start_time.getTime()<real_time.getTime())
						&&(real_time.getTime()<end_time.getTime())) {
					System.out.println("근무 중 시간 입니다. -조퇴");

					endChkObj.addProperty("퇴근", realTime); //정상체크는 안되고 일찍 퇴근하니깐 조퇴 처리한다.
					//분 단위로 계산한다.
					endChk_str=gson.toJson(endChkObj);
					endStateChkObj.addProperty("퇴근상태", "조퇴"); //조퇴를 뜻함
					endStateChk_str=gson.toJson(endStateChkObj);
				}
				//end 시간 후 일때 -> 출근 체크 안됨, 출근체크가 되어 있으면 퇴근 체크 가능
				else if(real_time.getTime()>=end_time.getTime()){
					System.out.println("퇴근시간 후 입니다. 퇴근가능 합니다.");
					endChkObj.addProperty("퇴근", Tend);
					endChk_str=gson.toJson(endChkObj);	
					endStateChkObj.addProperty("퇴근상태", "정상");
					endStateChk_str=gson.toJson(endStateChkObj);
				}
			}

		}

		//				}
		return endChk_str+"x"+endStateChk_str; 

	}


	//여기서부터 아작스 컨트롤러
	@RequestMapping(value="/dayCheck.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> commute(Model model, ServletRequest req, String YYYYMMDDtime,
			String name, HttpSession session) throws org.json.simple.parser.ParseException, ParseException {
		//ServletContext application
		Map<String, Object> map=new HashMap<String, Object>();
		
		logger.info("welcom to schmap:\t {}", new Date());
		System.out.println("map시작전");
		
		UserInfoDTO user=(UserInfoDTO) session.getAttribute("user");
		System.out.println("세션 객체 확인(현재 로그인 되어 있는 정보):"+user);
		
		String work_code= session.getAttribute("ws_code")+"";
		System.out.println("세션 객체 확인(현재 들어가 있는 사업장 정보):"+work_code);
		
		String user_idsession=user.getUser_id();//현재 로그인 되어 있는 정보
		String workspacesession= work_code;
		String daytemp=YYYYMMDDtime.substring(0,7);
		
		schmap.put("sch_month", daytemp);
		schmap.put("ws_code", workspacesession);
		schmap.put("user_id", user_idsession);//여기 나중에 세션 객체 넣기!!!!!!!!!!!!!
		System.out.println("map.put까지는 완료");
		
		context.setAttribute("user_id", user_idsession);
		context.setAttribute("ws_code", workspacesession);
		
		String SCschedule= work_service.workInfoSelect(schmap);
		System.out.println("SCschedule"+SCschedule);//타입은 String 타입이다.
		//SClist는 한달 동안 하나의 사업장에서의 기록이다. 행이 하나 dto를 for문 돌려서 여러개 빼줄 필요가 없다.
		//model.addAttribute("list", SCschedule); //test.jsp에 스케줄 정보를 뿌려주기 위해 model 객체에 넣기

		//JSONObject json=new JSONObject();
		
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
		}else {
			map.put("message", "오늘의 일정에 일정이 존재하지 않습니다.");//출근정시 시작 전에 퇴근을 찍었을 경우
			return map;
		}
		//////////////////////////////////////////////////

		

		String DD=YYYYMMDDtime.substring(8,10);
		if(ChkIp(req, workspacesession)) { //둘다 true일 경우만 실행
			//ChkIp(req) && chkName() ->애는 나중에 하겠음. 일단 보류	
			//출근일 경우
			if(name.equals("출근")) {
				logger.info("출근하기");
				//시간 가져오기
				commuteJSON=commuteChk(YYYYMMDDtime); //출근을 체크할 때 먼저 user_id/스케줄 있는지 판단->그리고 모든 판단의 결과값 json으로
				//return startChk_str+"x"+startState_str+"x"+Tstart+"x"+Tend+"x"+Tuser_id;
				//애는 현재 내가 출근 체크를 넣었을 때 제대로 되었는지의 json값을 반환함.
				System.out.println("commuteJSON: "+commuteJSON);//출근정보

				if(!commuteJSON.equals("xxxxx")) {//일정이 있따면
					String array[] = commuteJSON.split("x");

					startChk_str=array[0]; //출근체크된 시간
					startState=array[1]; //출근상태
					Tstart=array[2]; //기억되는 출근 시간
					Tend=array[3]; //기억되는 퇴근 시간
					Tuser_id=array[4]; //기억되는 user_id
					Tcalendar_id=array[5];

					System.out.println("startChk_str:"+startChk_str);
					System.out.println("startState:"+startState);
					System.out.println("Tstart:"+Tstart);
					System.out.println("Tend:"+Tend);
					System.out.println("Tuser_id:"+Tuser_id);
					System.out.println("Tcalendar_id:"+Tcalendar_id);
					//나중에 time값과 일정관리json에서 가져온 애와 근무 시작시간/근무종료시간을 출퇴근 시간과 비교해야한다.
					//비교후 일치하면 근무일정코드 json을 db에 저장 ->확정되어 찍힌 시간이기 때문


					System.out.println("년/월/일/시/분: "+YYYYMMDDtime);



					if(!commuteJSON.substring(0,2).equals("xx")) {//출근은 일단 했다는 것
						Date from = new Date();
						SimpleDateFormat fm = new SimpleDateFormat("a hh:mm:ss");
						String startDATE = fm.format(from);
						System.out.println("startDATE:"+startDATE);
						context.setAttribute("startDATE", startDATE);
						map.put("commute", context.getAttribute("startDATE")); //출근시간 넣기

						//출근 정보 넣어주기 , 나중에 퇴근하면 퇴근 정보 넣어주기
						//그래서 출근 정보만 있으면 출근 처리 해주고 출퇴근 모두 안해줬다면 결석 처리 해주기..이 조건에 해당하면 해당 기록 db에 넣어주기
						System.out.println("출근만 했을 때 확인 시작");
						//String startEndChk_Str="{"+"\""+"출퇴"+"\""+":"+"["+startChk_str+","+endChk_str+"]"+"}";
						//String state_Str="{"+"\""+"상태"+"\""+":"+"["+startState+","+endStateChk_str+"]"+"}";

						//출근 만 할 때 넘겨줄 애
						String ONLYSTARTWORK="{"+"\""+DD+"\""+":"+"[{\"일정\":[{\"오전\":\"\"},{\"오후\":\"\"},{\"야간\":\"\"},{\"초과\":\"\"}]}"
								+","+"{"+"\""+"출퇴"+"\""+":"+"["+startChk_str+","+"{"+"\""+"퇴근"+"\""+":"+"\"\""+"}"+"]"+"}"+","+"{"+"\""+"상태"+"\""+":"+"["+startState+","+"{"+"\""+"퇴근상태"+"\""+":"+"\"\""+"}"+"]"+"}"+"]"+"}";
						
						
							context.setAttribute("ONLYSTARTWORK", ONLYSTARTWORK);
							System.out.println("ONLYSTARTWORK근태JSON:"+ONLYSTARTWORK);
							//----------퇴근에서 써줄 정보 담아주기--------------------
							context.setAttribute("YYYYMMDDtime", YYYYMMDDtime);
							context.setAttribute("startState", startState);
							context.setAttribute("Tstart", Tstart);
							context.setAttribute("Tend", Tend);
							context.setAttribute("Tuser_id", Tuser_id);
							context.setAttribute("Tcalendar_id", Tcalendar_id);
							//------------------------------------------------
	
							//servletContext.setAttribute("ONLYSTARTWORK", ONLYSTARTWORK);
							//application.setAttribute("ONLYSTARTWORK", ONLYSTARTWORK);
						
					}else {//일정은 있지만 출근이 가능한 시간이 아닐떄
						//startChk_str:""
						//startState:""    ->결근(출석이 결근이면 퇴근 처리는 당연히 안됨)-> 이거 json표시 해주기{"출근":""}
						map.put("message", "출근시간이 아닙니다.");//퇴근정시 이후에 출근을 찍었을 경우
						map.put("blank","");
					}

				}else { //아예 일정 자체가 존재하지 않을 때
					map.put("message", "오늘은 일정이 존재하지 않습니다.");
					map.put("blank","");
				}


			}
			//퇴근일 경우
			else {
				logger.info("퇴근하기");
				System.out.println("commuteJSON: "+commuteJSON);
				System.out.println("Tstart:"+Tstart+"  Tend:"+Tend+"  Tuser_id:"+Tuser_id+"    Tcalendar_id:"+Tcalendar_id);
				leaveWorkJSON=leaveWork(YYYYMMDDtime, startState, Tstart, Tend, Tuser_id, Tcalendar_id);
				//endChk_str+"x"+endStateChk_str

				System.out.println("년/월/일/시/분: "+YYYYMMDDtime);

				if(!leaveWorkJSON.equals("x")) {
					//시간 가져오기
					String array2[] = leaveWorkJSON.split("x");
					endChk_str=array2[0];
					endStateChk_str=array2[1];

					System.out.println("endChk_str:"+endChk_str);
					System.out.println("endStateChk_str:"+endStateChk_str);
					String sch_str=calWork();//일정계산 함수
					System.out.println("--------모든 일정이 끝난 후 근태 기록 JSON출력->table에 넣어줄것--------");
					System.out.println("sch_str:"+sch_str);

					String startEndChk_Str="{"+"\""+"출퇴"+"\""+":"+"["+startChk_str+","+endChk_str+"]"+"}";
					String state_Str="{"+"\""+"상태"+"\""+":"+"["+startState+","+endStateChk_str+"]"+"}";

					String WORK="{"+"\""+DD+"\""+":"+"["+sch_str+","+startEndChk_Str+","+state_Str+"]"+"}";

					System.out.println("WORK근태JSON:"+WORK);

					context.setAttribute("ONLYENDWORK", WORK);
					System.out.println("ONLYENDWORK가 끝나는 근태 =WORK"+WORK);


					Date from = new Date();

					SimpleDateFormat fm = new SimpleDateFormat("a hh:mm:ss");

					String endDATE = fm.format(from);
					System.out.println("endDATE:"+endDATE);
					context.setAttribute("endDATE", endDATE);

					map.put("leaveWork", context.getAttribute("endDATE")); //퇴근시간
					tableInfo_Input(WORK, Tuser_id, Tcalendar_id); //완벽히 출근과 퇴근이 완료 되었을 때 테이블이 생성

				}else {
					map.put("message", "퇴근시간이 아닙니다.");//출근정시 시작 전에 퇴근을 찍었을 경우
					map.put("blank","");
				}

			}

			return map;

		}else {
			logger.info("ip가 일치하지 않습니다.다시 확인해주세요");
			map.clear();
			map.put("message", "사업장 Wi-fi가 아닙니다. 다시 확인해주세요");
			return map;
		}


	}

	//////////////테이블 정보////////////////////////
	public void tableInfo_Input(String WORK, String Tuser_id, String Tcalendar_id) {//work json에서 아이디와 직급(calendarId가 1이면 직원/2이면 매니저) , 근태  map으로 넣어주기
		//	JsonParser parser = new JsonParser();
		//startChk_str->json
		//	JsonElement WORKJOSON=(JsonElement)parser.parse(WORK);
		//	System.out.println("WORKJOSON: "+WORKJOSON);
		//	String =WORKJOSON.getAsString();

		Map<String, Object> workmap=new HashMap<String, Object>();

		//work는 그냥 담으면 안되고 그 전날 것까지 갱신해서 담아야한다. string으로 연결 연결..
		//테이블에서 가져온다음 insert하기
		String pastchk=work_service.selectWorkcheck(schmap);
		String WORK_STR="";
		if(pastchk !=null) { //과거의 기록이 존재한다면
			WORK_STR=pastchk+","+WORK;
		}else {//과거의 기록이 없다면(첫 기록일 때)
			WORK_STR=WORK;
		}

		System.out.println("WORK_STR:"+WORK_STR);

		workmap.put("work_check", WORK_STR);
		//schmap//string으로 반환

		workmap.put("user_id", Tuser_id);

		String rank="";

		if(Tcalendar_id.equals("1")) {//1직원
			rank="emp";
		}else {//2매니저
			rank="man";
		}

		workmap.put("employee_rank", rank);

		workmap.put("ws_code", schmap.get("ws_code"));
		workmap.put("sch_month",schmap.get("sch_month"));

		boolean success=work_service.updateDaily(workmap);
		System.out.println("오늘의 일정 성공 여부 success:"+success);

	}	




	///////////////////////<<일정계산 로직>>////////////////////////////////

	//근무 시간을 분류하는 곳//{"일정":
	//    {{"오전":""},{"오후":""},{"야간":""},{"초과":""}}
	// }
	public String calWork() throws ParseException { //일정 파트별 계산 후 "일정":json을 만든다. 
		//startChk_str="";//출근체크
		//startState="";//출근상태
		//endChk_str="";//퇴근체크
		//endStateChk_str="";//퇴근 상태

		JsonParser parser = new JsonParser();
		//startChk_str->json
		JsonElement startChkJSON=(JsonElement)parser.parse(startChk_str);
		System.out.println("startChkJSON"+startChkJSON);
		String startPoint=startChkJSON.getAsJsonObject().get("출근").getAsString();////startChk_str->json
		//202006111437
		System.out.println("startPoint:"+startPoint);

		//endChk_str->json
		JsonElement endChkJSON=(JsonElement)parser.parse(endChk_str);
		System.out.println("endChkJSON"+endChkJSON);
		String endPoint=endChkJSON.getAsJsonObject().get("퇴근").getAsString();
		System.out.println("endPoint:"+endPoint);

		//그날의 아침 6시
		String morningPoint=startPoint.substring(0,8)+"0600";
		//그날의 낮 12시
		String afternoonPoint=startPoint.substring(0,8)+"1200";
		//그날의 밤 22시
		String nightPoint=startPoint.substring(0,8)+"2200";

		System.out.println("nightPoint:"+nightPoint);
		//그 다음날의 6시
		//String nextMorningPoint=startPoint.substring(0,6)+(Integer.parseInt(startPoint.substring(6,8))+1)+"0600";

		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");


		Date startchkDate=dateFormat.parse(startPoint);
		Date endchkDate=dateFormat.parse(endPoint);
		Date MpointDate=dateFormat.parse(morningPoint);
		Date AftpointDate=dateFormat.parse(afternoonPoint);
		Date NghtpointDate=dateFormat.parse(nightPoint);
		//Date NXTMpointDate=dateFormat.parse(nextMorningPoint);
		//System.out.println("NghtpointDate:"+NghtpointDate);

		long morning=0;
		long afternoon=0;
		long night=0;

		long morningH=0;
		long morningM=0;

		long afternoonH=0;
		long afternoonM=0;

		long nightH=0;
		long nightM=0;

		//오전:  06:00~12:00 , 오후: 12:00~22:00 , 야간: 22:00~6:00
		//출근시간이 어디에 속해있는지 오전인지 오후인지 야간인지
		if((Integer.parseInt(startPoint.substring(8,10))<12)
				&&(6<=Integer.parseInt(startPoint.substring(8,10)))) {//출근시간 ?오전
			//퇴근 시간은 어디에 속해 있는가?
			if((Integer.parseInt(endPoint.substring(8,10))<12)
					&&(6<=Integer.parseInt(endPoint.substring(8,10)))) {//퇴근시간 ?오전: 퇴근시간-출근시간 ->"오전":""
				morning=endchkDate.getTime()-startchkDate.getTime();
				morningH=morning/3600000; //1000 * 60 * 60=3600000
				morningM=(morning-morningH*3600000)/60000;
			}
			else if((Integer.parseInt(endPoint.substring(8,10))>=12)
					&&(22>Integer.parseInt(endPoint.substring(8,10)))) {//퇴근시간 ?오후: 12:00-출근시간->"오전":"", 퇴근시간-12:00->"오후":""
				morning=AftpointDate.getTime()-startchkDate.getTime();
				morningH=morning/3600000;
				morningM=(morning-morningH*3600000)/60000;

				afternoon=endchkDate.getTime()-AftpointDate.getTime();
				afternoonH=afternoon/3600000;
				afternoonM=(afternoon-afternoonH*3600000)/60000;
			}
			else{//퇴근시간 ?야간: 퇴근시간-2020.06.11.22:00->"야간":"", 10시간++->"오후":"", 12:00-출근시간 ->"오전":""
				morning=AftpointDate.getTime()-startchkDate.getTime();
				morningH=morning/3600000;
				morningM=(morning-morningH*3600000)/60000;

				//afternoon=10;//10시간??
				afternoonH=10;

				night=endchkDate.getTime()-NghtpointDate.getTime();
				nightH=night/3600000;
				nightM=(night-nightH*3600000)/60000;
			}
		}
		else if((Integer.parseInt(startPoint.substring(8,10))>=12)
				&&(22>Integer.parseInt(startPoint.substring(8,10)))) {//출근시간 ?오후
			//퇴근 시간은 어디에 속해 있는가?
			if((Integer.parseInt(endPoint.substring(8,10))<12)
					&&(6<=Integer.parseInt(endPoint.substring(8,10)))) {//퇴근시간 ?오전: 퇴근시간-6:00 ->"오전":"" , 야간 8시간++, 22:00-출근시간 -> "오후":""
				morning=endchkDate.getTime()-MpointDate.getTime();
				morningH=morning/3600000;
				morningM=(morning-morningH*3600000)/60000;

				//night=8; //8시간?
				nightH=8;

				afternoon=NghtpointDate.getTime()-startchkDate.getTime();
				afternoonH=afternoon/3600000;
				afternoonM=(afternoon-afternoonH*3600000)/60000;
			}
			else if((Integer.parseInt(endPoint.substring(8,10))>=12)
					&&(22>Integer.parseInt(endPoint.substring(8,10)))) {//퇴근시간 ?오후: 퇴근시간-출근시간->"오후":""
				afternoon=endchkDate.getTime()-startchkDate.getTime();
				afternoonH=afternoon/3600000;
				afternoonM=(afternoon-afternoonH*3600000)/60000;
			}
			else {//(퇴근시간 ?야간): 22:00-출근시간->"오후":"", 퇴근시간-22:00->"야간":""
				afternoon=NghtpointDate.getTime()-startchkDate.getTime();
				afternoonH=afternoon/3600000;
				afternoonM=(afternoon-afternoonH*3600000)/60000;

				night=endchkDate.getTime()-NghtpointDate.getTime();
				nightH=night/3600000;
				nightM=(night-nightH*3600000)/60000;
			}

		}
		else {//(출근시간 ?야간)
			//퇴근 시간은 어디에 속해 있는가?
			if((Integer.parseInt(endPoint.substring(8,10))<12)
					&&(6<=Integer.parseInt(endPoint.substring(8,10)))) {//퇴근시간 ?오전: 6:00-출근시간  ->"야간":"", 퇴근시간-6:00 ->"오전":""
				night=MpointDate.getTime()-startchkDate.getTime();
				nightH=night/3600000;
				nightM=(night-nightH*3600000)/60000;

				morning=endchkDate.getTime()-MpointDate.getTime();
				morningH=morning/3600000;
				morningM=(morning-morningH*3600000)/60000;
			}
			else if((Integer.parseInt(endPoint.substring(8,10))>=12)
					&&(22>Integer.parseInt(endPoint.substring(8,10)))) {//퇴근시간 ?오후: 6:00-출근시간 -> "야간":"", 오전: 6시간++, 퇴근시간-12:00->"오후":""
				night=MpointDate.getTime()-startchkDate.getTime();
				nightH=night/3600000;
				nightM=(night-nightH*3600000)/60000;

				//morning=6시간;
				morningH=6;

				afternoon=endchkDate.getTime()-AftpointDate.getTime();
				afternoonH=afternoon/3600000;
				afternoonM=(afternoon-afternoonH*3600000)/60000;
			}
			else { //(퇴근시간 ?야간): 퇴근시간-출근시간 ->"야간":""
				//System.out.println("endchkDate.getTime()"+endchkDate.getTime());
				//System.out.println("startchkDate.getTime()"+startchkDate.getTime());
				night=endchkDate.getTime()-startchkDate.getTime();
				//System.out.println("night:"+night);
				nightH=night/3600000;
				//System.out.println("nightH:"+nightH);
				nightM=(night-nightH*3600000)/60000;
				//System.out.println("nightM:"+nightM);
			}
		}


		String morningM2="";
		String morningH2="";
		String afternoonH2="";
		String afternoonM2="";
		String nightH2="";
		String nightM2="";

		Gson gson=new Gson();
		JsonObject morningObj=new JsonObject();

		if(String.valueOf(morningM).length()==1) morningM2="0"+morningM;
		if(String.valueOf(morningH).length()==1) morningH2="0"+morningH;
		String morningObjstr=morningH2+":"+morningM2;
		morningObj.addProperty("오전", morningObjstr);
		String morning_str=gson.toJson(morningObj);
		System.out.println("morning_str:"+morning_str);


		JsonObject afternoonObj=new JsonObject();
		if(String.valueOf(afternoonM).length()==1) afternoonM2="0"+afternoonM;
		if(String.valueOf(afternoonH).length()==1) afternoonH2="0"+afternoonH;
		String afternoonObjstr=afternoonH2+":"+afternoonM2;
		afternoonObj.addProperty("오후", afternoonObjstr);
		String afternoon_str=gson.toJson(afternoonObj);
		System.out.println("afternoon_str:"+afternoon_str);

		JsonObject nightObj=new JsonObject();
		if(String.valueOf(nightM).length()==1) nightM2="0"+nightM;
		if(String.valueOf(nightH).length()==1) nightH2="0"+nightH;
		String nightObjstr=nightH2+":"+nightM2;
		nightObj.addProperty("야간", nightObjstr);
		String night_str=gson.toJson(nightObj);
		System.out.println("night_str:"+night_str);

		JsonObject excObj=new JsonObject();
		String excObjstr="";
		excObj.addProperty("초과", excObjstr);
		String exc_str=gson.toJson(excObj);
		System.out.println("exc_str:"+exc_str);


		String resultStr="["+morning_str+","+afternoon_str+","+night_str+","+exc_str+"]";		

		String sch_str= "{"+"\""+"일정"+"\""+":"+resultStr+"}";

		System.out.println("sch_str(일정 전체 jsonstr):"+sch_str);

		return sch_str;
	}


}
