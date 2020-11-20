package com.min.sc.sch.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.min.sc.sch.dtos.SchBasicDTO;
import com.min.sc.sch.dtos.SchDTO;
import com.min.sc.sch.model.IService_SchBasic;
import com.min.sc.sch.model.Sch_IService;




@Component
public class Scheduler_Sch {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_SchBasic sbservice;
	
	@Autowired
	private Sch_IService schservice;
	/**
	 * 일정 기초사항중 6개월이 지난 기초사항을 삭제한다.
	 * @throws ParseException
	 */
	@Scheduled(cron = "0 0 08 * * ? ")
	public void deleteScBasic() throws ParseException {
		Date dchk = new Date();
		log.info("Scheduler_schBasic 사용하지 않는 기초사항 삭제 : \t {}",new Date());
		int cnt = 0;
		int cnt2 = 0;
		List<SchBasicDTO> lists= sbservice.schBasicSelAll();
		boolean cbool = false;
		if(!lists.isEmpty()) {
			log.info("Scheduler_schBasic 전체 사업장 기초사항  조회 완료 : \t {}",lists);
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < lists.size(); i++) {
				String from = lists.get(i).getSchbasic_record();
				Date to = fm.parse(from);
				long calDate = dchk.getTime()-to.getTime();
				long calDateDays = calDate / (24*60*60*1000);
				calDateDays = Math.abs(calDateDays);
				if (calDateDays>180) {
					cbool =sbservice.schBasicDel(lists.get(i).getSchbasic_seq());
					cnt2++;
					if(cbool) {
						cnt++;
						
					}
				}
			}
			if(cnt2==cnt) {
				log.info("Scheduler_schBasic 전체 사업장 기초사항 삭제 완료 : \t {}",new Date());
			}else {
				log.info("Scheduler_schBasic 전체 사업장 기초사항  조회 완료 : \t {}",new Date());
			}
		}else {
			log.info("Scheduler_schBasic 전체 사업장 기초사항 없음 : \t {}",new Date());
		}
	}
	
	@Scheduled(cron = "0 0 08 * * ? ")
	public void modifySch() throws ParseException, org.json.simple.parser.ParseException {
		Date dchk = new Date();
		log.info("Scheduler_sch 모든 일정 json 조회 : \t {}",new Date());
		int cnt = 0;
		int cnt2 = 0;
		int cnt3 = 0;
		List<SchDTO> lists= schservice.selectSchAll();
		boolean cbool = false;
		if(!lists.isEmpty()) {
			log.info("Scheduler_sch 모든 일정 json 조회: \t {}",lists);
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			JSONParser jp = new JSONParser();
			Date checked = fm.parse(fm.format(dchk));
			Date jsday = null;
			for (int i = 0; i < lists.size(); i++) {
				String jstirng = lists.get(i).getSch_schedule();
				JSONArray joarr= (JSONArray) jp.parse(jstirng);
				JSONArray jrr = new JSONArray();
				for (int j = 0; j < joarr.size(); j++) {
					JSONObject jjobj = (JSONObject) joarr.get(j);
					JSONArray jarr = (JSONArray) jjobj.get("schedule");
					JSONObject jcobj = new JSONObject();
					String start = "";
					String[] sdayarr;
					String sday ="";
					
					for (int k = 0; k < jarr.size(); k++) {
						jcobj =(JSONObject) jarr.get(k-cnt);
						start = (String) jcobj.get("start");
						sdayarr =start.split("T");
						sday = sdayarr[0];
						jsday = fm.parse(sday);
						long calDate = checked.getTime()-jsday.getTime();
						long calDateDays = calDate / (24*60*60*1000);
						calDateDays = Math.abs(calDateDays);
						if (calDateDays>7) {
							jarr.remove(k-cnt);
							cnt++;
						}	
					}
					cnt=0;
					jjobj.put("schedule", jarr.toJSONString());
					jrr.add(jjobj.toJSONString());
					
				}
				
				int sch_code =lists.get(i).getSch_code();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sch_code", sch_code);
				map.put("sch_schedule", jrr.toJSONString());
				cbool =schservice.cornUpdate(map);
				if (cbool) {
					cnt2++;
				}
				cnt3++;
				log.info(cnt2+":"+cnt3);
			}
			if(cnt2==cnt3) {
				log.info("Scheduler_sch 모든 일정 json 업데이트 완료: \t {}",new Date());
			}else {
				log.info("Scheduler_sch 모든 일정 json 중간에 몇개 빠졌나: \t {}",new Date());
			}
		}else {
			log.info("\"Scheduler_sch 일정없어요  :\t {}",new Date());
		}
	}
}
