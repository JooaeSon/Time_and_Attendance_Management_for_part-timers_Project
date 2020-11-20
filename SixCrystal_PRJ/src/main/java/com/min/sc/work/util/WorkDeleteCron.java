package com.min.sc.work.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.min.sc.work.model.IService_Work;

@Component
public class WorkDeleteCron {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_Work work_service;
	
//	@Scheduled(cron = "0/5 * * * * *") 
//	public void run() {
//		log.info("Scheduler_User CRON Scheduler 테스트 : \t {}",new Date());
//	}
	
	/**
	 * 6개월마다 한번씩 근태 기록 자동 삭제
	 * # 매월 1일 새벽 한시 실행 
	 */
	@Scheduled(cron="0 0 01 1 * *")
	public void workDelete() {
		log.info("workDelete 6개월 지난 근태 영구삭제 : \t {}",new Date());
		Date date = new Date();
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM");
		Date before6Mon = addMonth(date,-6);
		
		String deldate=sDate.format(before6Mon);//현재 년 월에 해당하는 기록을 조회

		boolean isc=work_service.deleteWorkRecord(deldate);
		log.info("삭제 성공 여부: "+isc);
	}
	
	
	public static Date addMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

}
