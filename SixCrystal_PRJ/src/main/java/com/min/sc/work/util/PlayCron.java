package com.min.sc.work.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.min.sc.work.model.IService_Work;


//@Component
public class PlayCron {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	//@Autowired
	private IService_Work work_service; 

	//@Autowired
	ServletContext context;

	public static Map<String, Object> DayMap = new HashMap<String, Object>();//날짜:
	public static Map<String, Object> schmap=new HashMap<String, Object>();


	public boolean chkName(String User_id) {

		String name="gang"; //세션에서 가져온 아이디
		if(name.equals(User_id)) { //JSON에서 가져온 아이디(직원아이디) ->이 가져온 아이디는 근태 테이블 user_id에다 집어넣는다.
			logger.info("오늘의 스케줄에 존재합니다.");
			return true;
		}else {
			logger.info("오늘의 스케줄에 존재하지 않습니다.");
			return false;
		}

	}

	/////////application검사////////////// 한시간에 한번씩 돌려준다. 현재 시간과 가장 가까운 퇴근 시간을 구해서 그 퇴근시간
	//@Scheduled(cron = "* * 1 * * *")
	public void chkApplication(String schTends) throws ParseException, java.text.ParseException {
		//ONLYSTARTWORK :출근시간
		//ONLYENDWORK  : 퇴근시간

		//Tend를  이미 지나가버린 시간이면 이미 퇴근 시간 이후를 말함 하지만 퇴근 시간 이후에도 찍힐 수 있다. (출근을 찍을 때 Tend시간을 받을 수 있다.)
		//sysdate랑 tend시간(그 당시에 정시 퇴근 시간)을 비교한다.
		Date date=new Date();
		System.out.println("date:"+date);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");


		String currentTime=dateFormat.format(date)+"";
		System.out.println("currentTime:"+currentTime);
		String DD=currentTime.substring(6,8);

		System.out.println("DD:"+DD);

		schmap.put("sch_month", "2020-06");
		schmap.put("ws_code", 13);
		schmap.put("user_id", "gang");//여기 나중에 세션 객체 넣기!!!!!!!!!!!!!
		System.out.println("map.put까지는 완료");

		String SCschedule= work_service.workInfoSelect(schmap);
		System.out.println("SCschedule"+SCschedule);//타입은 String 타입이다.


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

		String daySchedule=(String) DayMap.get(DD);//애는 map임
		//여기에서 user_id가 맞다면 start와 end를 구한다.
		System.out.println("daySchedule"+daySchedule);

		int user_count=0;
		long min=1000000000;
		String Tuser_id="";//기억되는 user_id

		String Tstart="";
		String Tend="";

		if(daySchedule!=null) {//일정이 있다
			//gSon
			JsonParser parser1 = new JsonParser();
			JsonElement jsonElement = (JsonElement) parser1.parse(daySchedule);
			System.out.println("jsonElement:"+jsonElement);


			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) { //배열안에 user_id개수 세기
				JsonObject user_chk = (JsonObject) jsonElement.getAsJsonArray().get(i);
				String User_id=user_chk.get("title").getAsString();
				if(chkName(User_id)) {
					user_count++; //이 for문을 다 도는 순간 내가 오늘하루 몇개의 스케줄을 가지고 있는지 판명난다.
				}
			}


			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				JsonObject dayby = (JsonObject) jsonElement.getAsJsonArray().get(i);
				System.out.println(i+"번째 dayby jsonElement:"+dayby);
				String User_id=dayby.get("title").getAsString();
				String start=dayby.get("start").getAsString().replaceAll(":","").replaceAll("-", "").replaceAll("T", "");
				String end=dayby.get("end").getAsString().replaceAll(":","").replaceAll("-", "").replaceAll("T", "");


				System.out.println("start:"+start);
				System.out.println("end:"+end);


				if(chkName(User_id)) {//만약 스케줄에도 day에 해당하는 일정이있고 이름도 있다면?
					//user_id가 한명이  두번이상 있다면 매번 할때마다 realtime을 갱신해야된다.
					System.out.println("오늘의 일정입니다./이름도 존재하고 스케줄도 존재");

					if(user_count==1) { //한명이 한개 스캐줄 가지고 있을 경우
						//초기 차이값	
						Tstart=start;
						Tend=end;
						Tuser_id=User_id;

					}


					if(user_count>=2) {//한명이 두개 이상의 스캐줄을 가지고 있을 경우
						if((Long.parseLong(end)>Long.parseLong(currentTime))) {//아직 끝나기 전 시간이라면
							if(min>Math.abs(Long.parseLong(currentTime)-Long.parseLong(end))) {
								min=Math.abs(Long.parseLong(currentTime)-Long.parseLong(end));//실제 시간과 스케줄 출근시간 차이
								Tstart=start;
								Tend=end;
								Tuser_id=User_id;

							}
						}

					}

				}
			}//for문 끝남->그날 스케줄 기록탐색 다 끝난거임

			System.out.println("당신이 출근할 시간Tstart:"+Tstart);
			System.out.println("당신이 퇴근할 시간Tend:"+Tend);
			System.out.println("당신이 현재 접속되어 있는 id:"+Tuser_id);
		}


		Calendar cal = Calendar.getInstance();

		Date dateTend=dateFormat.parse(Tend);
		cal.setTime(dateTend);
		cal.add(Calendar.HOUR, 6);//6시간 더해주기 위해 cal형태로 변환

		Date cinderellaTime=cal.getTime(); //다시 cal에서 date로 변환
		System.out.println("6시간 후 : " + cinderellaTime);


		System.out.println("currentTime:"+currentTime);


		System.out.println("ONLYSTARTWORK:"+context.getAttribute("ONLYSTARTWORK"));
		System.out.println("ONLYENDWORK:"+context.getAttribute("ONLYENDWORK"));

		if(cinderellaTime.getTime()< date.getTime()) {//(Tend+6시간)//실제 퇴근 시간보다 6시간이 지나버리면 퇴근 값 없는대로 그대로 넣는다. 
			if (context.getAttribute("ONLYSTARTWORK")!=null && context.getAttribute("ONLYENDWORK")==null) {//출근 시간만 있고 퇴근시간은 없다면
				String ONLYSTARTWORK=(String)context.getAttribute("ONLYSTARTWORK");
				//tableInfo_Input(ONLYSTARTWORK, Tuser_id, Tcalendar_id);
				//context.removeAttribute(ONLYSTARTWORK); 테이블에 넣으면 그 순간 지우기
			}
		}

		//출근, 결근 체크 둘다 안했을 때
		if(cinderellaTime.getTime()< date.getTime()) {//6시간이 지나고도 출근체크와 결근 체크가 되어있지 않을때
			if(context.getAttribute("ONLYSTARTWORK")==null && context.getAttribute("ONLYSTARTWORK")==null) {
				//둘다 없다면 그냥 결근한 거니까 
				String Absenteeism="{"+"\""+DD+"\""+":"+"[{\"일정\":[{\"오전\":\"\"},{\"오후\":\"\"},{\"야간\":\"\"},{\"초과\":\"\"}]}"
						+","+"{"+"\""+"출퇴"+"\""+":"+"["+"{"+"\""+"출근"+"\""+":"+"\"\""+"}"+","+"{"+"\""+"퇴근"+"\""+":"+"\"\""+"}"+"]"+"}"+
						","+"{"+"\""+"상태"+"\""+":"+"["+"{"+"\""+"출근상태"+"\""+":"+"\"\""+"}"+","+"{"+"\""+"퇴근상태"+"\""+":"+"\"\""+"}"+"]"+"}"+"]"+"}";

				//tableInfo_Input(Absenteeism, Tuser_id, Tcalendar_id);
			}
		}


	}


}
