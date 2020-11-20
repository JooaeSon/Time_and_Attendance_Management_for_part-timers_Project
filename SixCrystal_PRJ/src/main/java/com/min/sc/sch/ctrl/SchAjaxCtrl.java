package com.min.sc.sch.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.min.sc.emp.dtos.EmpDTO;
import com.min.sc.emp.model.IService_Emp;
import com.min.sc.sch.dtos.SchBasicDTO;
import com.min.sc.sch.dtos.SchDTO;
import com.min.sc.sch.model.IService_SchBasic;
import com.min.sc.sch.model.Sch_IService;
import com.min.sc.user.dtos.UserInfoDTO;

@RestController
public class SchAjaxCtrl {
   
   @Autowired
   private Sch_IService service;
   
   @Autowired
   private IService_Emp service_emp;
   
   @Autowired
   private IService_SchBasic service_basic;

   ///updateSchInfo찾아서 dto setWs_code이거 해야함!! 
   // insert도 setWs_code해야함!! 
   
   @RequestMapping(value="/getScheduleData.do",method=RequestMethod.POST)
   public String getScheduleData(String schedule_string,HttpSession session) {
      //schedule_string : 화면에서 팝업을 통해 생성한 스케쥴의 정보를 JSON.stringify를 이용하여 String으로 만들어서 뒷단으로 보낸 값
	   
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");
	   
      JsonArray jsonArray =new JsonArray();
      //날짜별 스케쥴을 저장하는 전체 제이슨어레이
      System.out.println("===========getScheduleData============");
      System.out.println(schedule_string);
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(schedule_string);
      
      //화면에서 요청받은 스케쥴을 파서를 통해 스트링을 제이슨형태로 변경 (밑에서 사용할 신청받은스케쥴의 정보는 다 element사용)
      
//      System.out.println(element);
      String year_month=element.getAsJsonObject().remove("year_month").toString().substring(1,8);
      //요청된 스케쥴의 year_month에 대한 정보
      System.out.println(year_month);
      String day=element.getAsJsonObject().get("start").toString().substring(9,11);
      //요청된 스케쥴의 day에 대한 정보
      System.out.println(day);//요청된 스케쥴의 day에 대한 정보
      SchDTO dto = new SchDTO();
      dto.setSch_month(year_month);
      dto.setWs_code(ws_code);
      String beforeUpdateSchedule=service.selectSchInfo(dto);
      boolean flag=false;
      //내가 입력하고자 하는 월에 대한 정보가 디비에 저장되어있는지 확인
      if(beforeUpdateSchedule==null) {
         //디비에 저장되어있지않다면 ex) 디비에는 2020-04월까지의 스케쥴만 저장되어있고 내가 처음으로 2020-05에 대한 스케쥴을 입력하고자 할때 해당 조건문에 들어오게 됩니당
         System.out.println("null값 -------------insert문 실행");
         JsonArray jsonArray_schedule =new JsonArray();
         //해당 날짜에 해당하는 스케쥴을 저장하는 스케쥴 제이슨 어레이 
         jsonArray_schedule.add(element);
         //스케쥴어레이에 입력한 현재 저장하려고 하는 스케쥴에 대한정보를 어펜드하고 
         JsonObject insertSchedule =new JsonObject();
         //처음으로 insert를 해야하기 때문에 json객체를 다시 생성하고
         insertSchedule.add("day", parser.parse(day));
         //day 에 대한 정보를 제이슨에 입력해주고
         insertSchedule.add("schedule", jsonArray_schedule);
         //schedule에 대한 정보를 제이슨에 입력해주어서 입력해야 할 제이슨 객체 완성
         jsonArray.add(insertSchedule);
         //완성된 객체를 전체 어레이에 넣고 
         System.out.println(jsonArray.toString());
         SchDTO dto_insert = new SchDTO();
         dto_insert.setSch_month(year_month);
         dto_insert.setWs_code(ws_code);//나중에 합칠때 쿼리수정할까? 아냐 지금 하자 
         //해당 월에 대한 정보와
         dto_insert.setSch_schedule(jsonArray.toString());
         //스케쥴에 대한 정보를 스트링으로 바꾸어
         service.insertSchInfo(dto_insert);
//          디비에 저장
      }else {
         //디비에 내가 입력하고자 하는 월의 정보가 있다면 ex) 내가 2020-05에 대한 정보를 입력하려고 하는데 2020-05에 대한 행이 존재한다면 해당 행을 가져와서 update쿼리를 실행하여 정보를 입력해야한다.
         System.out.println("null값이 아님  -------------update문 실행");
         JsonArray updateSchedule = (JsonArray) parser.parse(beforeUpdateSchedule);
         //디비에 저장되어있는 스케쥴에 대한 정보를 불러와서 제이슨 형태로 파싱
         for (int i = 0; i < updateSchedule.getAsJsonArray().size(); i++) {
            System.out.println(i+"번째 엘리먼트~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            if(updateSchedule.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(parser.parse(day))) {
               System.out.println("같음 있음");
               System.out.println(updateSchedule.getAsJsonArray().get(i).getAsJsonObject().get("day"));//numberFormat(day)
               System.out.println(parser.parse(day));
//               System.out.println(updateSchedule.toString());
               updateSchedule.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().add(element);
//               System.out.println(updateSchedule.toString());
               SchDTO dto_update = new SchDTO();
               dto_update.setSch_month(year_month);
               dto_update.setWs_code(ws_code);
               //해당 월에 대한 정보와
               dto_update.setSch_schedule(updateSchedule.toString());
               //스케쥴에 대한 정보를 스트링으로 바꾸어
               service.updateSchInfo(dto_update);
//                디비에 저장
               flag=false;
               break;
            }else {
               //여기 코드를 좀 다시 짜봐야겠군,, 일단 있는거대로  등록하는거 generate 해봐야게땅!! 아니다 delete먼저 해보자!!! 
               System.out.println("다름 없음 다른 날짜로 제이슨 새로 만들어야함");
               flag=true;
            }
         }
         if(flag) {
            System.out.println("다름 없음 다른 날짜로 제이슨 새로 만들어야함");
            System.out.println(parser.parse(day));
            JsonObject newObject = new JsonObject();
            JsonArray insertAnotherDaySchedule =new JsonArray();
            newObject.add("day", parser.parse(day));
            insertAnotherDaySchedule.add(element);
            newObject.add("schedule", insertAnotherDaySchedule);
            updateSchedule.add(newObject);
            System.out.println(updateSchedule.toString());
            SchDTO dto_update = new SchDTO();
            dto_update.setSch_month(year_month);
            dto_update.setWs_code(ws_code);
            //해당 월에 대한 정보와
            dto_update.setSch_schedule(updateSchedule.toString());
            //스케쥴에 대한 정보를 스트링으로 바꾸어
            service.updateSchInfo(dto_update);
//             디비에 저장
         }
      }

      System.out.println("===================================");
      JsonObject json = new JsonObject();
      return json.toString();
   }
   
   
   
   
   @RequestMapping(value="/generateScueduleData.do",method=RequestMethod.POST)
   public String generateScueduleData(String boundary_string,HttpSession session) {
      
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
//		String rank=(String)session.getAttribute("rank");////////////////////////////////////
	   
      System.out.println(boundary_string);
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(boundary_string);

      String start_month=element.getAsJsonObject().remove("start_month").toString().substring(1,8);
      String end_month=element.getAsJsonObject().remove("end_month").toString().substring(1,8);
      System.out.println(start_month);
      System.out.println(end_month);
      String dayStart=element.getAsJsonObject().get("start").toString().substring(9,11);
      String dayEnd=element.getAsJsonObject().get("end").toString().substring(9,11);
      JsonElement rank=element.getAsJsonObject().get("rank");
      System.out.println(dayStart);//요청된 스케쥴의 day에 대한 정보
      System.out.println(dayEnd);//요청된 스케쥴의 day에 대한 정보
      System.out.println(rank);//요청된 스케쥴의 day에 대한 정보
      JsonElement ctrank=parser.parse("\""+rank+"\"");
      System.out.println("===========generateScueduleData============");
      
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
//         1) 첫날과 막날이 같은 달일경우 첫날과 막날 사이에 있는 스케쥴을 가져오자!!
         for (int i = 0; i < array_common.size(); i++) {
            whatDay_ele=array_common.get(i).getAsJsonObject().get("day").toString();
//            System.out.println(whatDay_ele);
//            System.out.println(dayStart);
//            System.out.println(dayEnd);
            if((convertNumber(whatDay_ele).compareTo(dayStart)>=0)&&(convertNumber(whatDay_ele).compareTo(dayEnd)<=0)) {
               for (int j = 0; j < array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                  if(array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(rank)||array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(ctrank)) {
                     send.add( array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
                  }else {
//                     System.out.println(array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId"));
//                     System.out.println("rank : "+rank);
                  }
                  
               }
            }
         }
      }
         else {
         System.out.println("다릅니다~");
////         2) 첫날과 막날이 다른 달일경우
////         첫날의 달 과 첫날일을 기준으로 크거나 같은경우의 일정만 골라서 가져오고
////         막날의 달과 막날을 기준으로 작거나 같은경우의 일정만 골라서 가져오자!!
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
                  if(array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(rank)) {
                  send.add( array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
                  }
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
                  if(array_end.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(rank)) {
                  send.add(array_end.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
                  }
               }
            }
         }
      }

      
      System.out.println(send);
      System.out.println("===================================");
      return send.toString();
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
   
   @RequestMapping(value="/generateAllScueduleData.do",method=RequestMethod.POST)
   public String generateAllScueduleData(String boundary_string,HttpSession session) {
	   
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
//		String rank=(String)session.getAttribute("rank");
      
      System.out.println(boundary_string);
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(boundary_string);

      String start_month=element.getAsJsonObject().remove("start_month").toString().substring(1,8);
      String end_month=element.getAsJsonObject().remove("end_month").toString().substring(1,8);
      System.out.println(start_month);
      System.out.println(end_month);
      String dayStart=element.getAsJsonObject().get("start").toString().substring(9,11);
      String dayEnd=element.getAsJsonObject().get("end").toString().substring(9,11);
      JsonElement rank=element.getAsJsonObject().get("rank");
      System.out.println(dayStart);//요청된 스케쥴의 day에 대한 정보
      System.out.println(dayEnd);//요청된 스케쥴의 day에 대한 정보
//      System.out.println(rank);//요청된 스케쥴의 day에 대한 정보
      
      System.out.println("===========generateAllScueduleData============");
      
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
//         1) 첫날과 막날이 같은 달일경우 첫날과 막날 사이에 있는 스케쥴을 가져오자!!
         for (int i = 0; i < array_common.size(); i++) {
            whatDay_ele=array_common.get(i).getAsJsonObject().get("day").toString();
//            System.out.println(whatDay_ele);
//            System.out.println(dayStart);
//            System.out.println(dayEnd);
            if((convertNumber(whatDay_ele).compareTo(dayStart)>=0)&&(convertNumber(whatDay_ele).compareTo(dayEnd)<=0)) {
               for (int j = 0; j < array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                  send.add( array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
               }
            }
         }
      }
         else {
         System.out.println("다릅니다~");
////         2) 첫날과 막날이 다른 달일경우
////         첫날의 달 과 첫날일을 기준으로 크거나 같은경우의 일정만 골라서 가져오고
////         막날의 달과 막날을 기준으로 작거나 같은경우의 일정만 골라서 가져오자!!
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
      System.out.println("===================================");
      return send.toString();
   }
   
   @RequestMapping(value="/generateEmpScueduleData.do",method=RequestMethod.POST)
   public String generateEmpScueduleData(String boundary_string,HttpSession session) {
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
//		String rank=(String)session.getAttribute("rank");////////////////////////////////////
	   
     System.out.println(boundary_string);
     JsonParser parser = new JsonParser();
     JsonElement element = parser.parse(boundary_string);

     String start_month=element.getAsJsonObject().remove("start_month").toString().substring(1,8);
     String end_month=element.getAsJsonObject().remove("end_month").toString().substring(1,8);
     System.out.println(start_month);
     System.out.println(end_month);
     String dayStart=element.getAsJsonObject().get("start").toString().substring(9,11);
     String dayEnd=element.getAsJsonObject().get("end").toString().substring(9,11);
     JsonElement rank=element.getAsJsonObject().get("rank");
     System.out.println(dayStart);//요청된 스케쥴의 day에 대한 정보
     System.out.println(dayEnd);//요청된 스케쥴의 day에 대한 정보
     System.out.println(rank);//요청된 스케쥴의 day에 대한 정보
     JsonElement ctrank=parser.parse("\""+rank+"\"");
     System.out.println("===========generateEmpScueduleData============");
     
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
//        1) 첫날과 막날이 같은 달일경우 첫날과 막날 사이에 있는 스케쥴을 가져오자!!
        for (int i = 0; i < array_common.size(); i++) {
           whatDay_ele=array_common.get(i).getAsJsonObject().get("day").toString();
//           System.out.println(whatDay_ele);
//           System.out.println(dayStart);
//           System.out.println(dayEnd);
           if((convertNumber(whatDay_ele).compareTo(dayStart)>=0)&&(convertNumber(whatDay_ele).compareTo(dayEnd)<=0)) {
              for (int j = 0; j < array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                 if(array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(rank)||array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(ctrank)) {
                    send.add( array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
                 }else {
//                    System.out.println(array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId"));
//                    System.out.println("rank : "+rank);
                 }
                 
              }
           }
        }
     }
        else {
        System.out.println("다릅니다~");
////        2) 첫날과 막날이 다른 달일경우
////        첫날의 달 과 첫날일을 기준으로 크거나 같은경우의 일정만 골라서 가져오고
////        막날의 달과 막날을 기준으로 작거나 같은경우의 일정만 골라서 가져오자!!
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
            	  if(array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(rank)||array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(ctrank))  {
                 send.add( array_common.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
                 }
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
            	  if(array_end.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(rank)||array_end.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("calendarId").equals(ctrank)) {
                 send.add(array_end.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
                 }
              }
           }
        }
     }

     
     System.out.println(send);
     System.out.println("===================================");
     return send.toString();
   }
   
   
   @RequestMapping(value="/updateScheduleData.do",method=RequestMethod.POST)//하아.... 처음부터 읽어보자,,, 내가 도대체 어뜨케 짰더라,,? 
   public String updateScheduleData(String updateInfo,HttpSession session) {
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");
      JsonObject json= new JsonObject();
       JsonParser parser = new JsonParser();
         System.out.println(updateInfo);//오케 업데이트할때 변경 할 수 있는 내용은 이렇게 정해두고!!! 
         JsonObject changeElement = (JsonObject) parser.parse(updateInfo);
         //정보판별할 때 사용하는 데이터 start_updatebefore year_month  id
         //기존 정보에서 변경할 데이터  isAllDay isReadOnly end start
         
//         System.out.println(changeElement);
         String year_month=changeElement.getAsJsonObject().remove("year_month").toString().substring(1,8);
         System.out.println("year_month : "+year_month);//2
         JsonElement daybefore=parser.parse(changeElement.getAsJsonObject().get("start_updatebefore").toString().substring(9,11));
         System.out.println("daybefore : "+daybefore);//랜더링하기전에 스타트
//         String monthafter=changeElement.getAsJsonObject().get("start").toString().substring(1,8);
//         System.out.println("monthafter : "+monthafter);
//         JsonElement dayafter=parser.parse(changeElement.getAsJsonObject().get("start").toString().substring(9,11));
//         System.out.println("dayafter : "+dayafter);
         JsonElement id=changeElement.getAsJsonObject().get("id");
         System.out.println("id : "+id);
//         
            
         String monthafter="";
         JsonElement dayafter=new JsonObject();
//         end start는 변경된 내용이 없다면 수정하도록!! changeElement에서 remove 할 수 있도록!! 
         if(changeElement.getAsJsonObject().get("start").toString().contains("NaN")) {
            System.out.println("start가 null임!!!!");
            changeElement.getAsJsonObject().remove("start");
            monthafter=changeElement.getAsJsonObject().get("start_updatebefore").toString().substring(1,8);
            System.out.println("monthafter : "+monthafter);
          dayafter=parser.parse(changeElement.getAsJsonObject().get("start_updatebefore").toString().substring(9,11));
          System.out.println("dayafter : "+dayafter);
         }else {
            monthafter=changeElement.getAsJsonObject().get("start").toString().substring(1,8);
             System.out.println("monthafter : "+monthafter);
             dayafter=parser.parse(changeElement.getAsJsonObject().get("start").toString().substring(9,11));
             System.out.println("dayafter : "+dayafter);
         }
         if(changeElement.getAsJsonObject().get("end").toString().contains("NaN")) {
            System.out.println("end가 null임!!!!");
            changeElement.getAsJsonObject().remove("end");
         }
         //그럼 이제 changeElement의 알짜배기만 남겟네?
         
         System.out.println("changeElement : "+changeElement);
         ArrayList<String> objectKeys=new ArrayList<String>(changeElement.keySet()); 
         //화면에서 요청받은 스케쥴을 파서를 통해 스트링을 제이슨형태로 변경 (밑에서 사용할 신청받은스케쥴의 정보는 다 element사용)
         System.out.println("objectKeys  : "+objectKeys);
         changeElement.getAsJsonObject().remove("start_updatebefore");
         System.out.println("===========updateScheduleData============");
         int update_day_idx = 0;// 전체어레이에서 내가 일정을 추가하고자 하는 일의 인덱스번호!!
         int update_id_idx = 0;//선택된 스케줄제이슨어레이중에서 내가 업데이트를 한 스케쥴의 스케쥴아이디
//         JsonObject extraObj = new JsonObject();
//         JsonArray extraArray = new JsonArray();
//         //아 날짜 자체를 바꿨을 경우도 있짜나!! 
         SchDTO dto_select = new SchDTO();
         dto_select.setSch_month(year_month);
         dto_select.setWs_code(ws_code);
         String updateTarget_str = service.selectSchInfo(dto_select);
         JsonArray updateTarget_elem = (JsonArray) parser.parse(updateTarget_str);
         System.out.println("updateTarget_elem : "+updateTarget_elem);
         String updateTarget_str_after ="";
         JsonArray updateTarget_elem_after=new JsonArray();
//       만약에 첫날연월과 막날연월이 같다면 첫날연월로 삭제할 정보를 갖고있는 행을 디비에서 가져오고 
         if(!monthafter.contains("NaN")) {
            SchDTO dto_select_another = new SchDTO();
            dto_select_another.setSch_month(monthafter);
            dto_select_another.setWs_code(ws_code);
            updateTarget_str_after = service.selectSchInfo(dto_select_another);
            if(updateTarget_str_after==null) {
            	updateTarget_str_after="";
            }else {
            	updateTarget_elem_after = (JsonArray) parser.parse(updateTarget_str_after);
            }
            
            System.out.println("updateTarget_elem_after : "+updateTarget_elem_after);
         }



         // 그 행을 제이슨어레이으로 다시 변환한 다음에
      if (year_month.equals(monthafter)) {
         // 변경전데이터와 변경 후 데이터가 월이 같을 경우
         System.out.println("변경전데이터와 변경 후 데이터가 월이 같을 경우");
         if (daybefore.toString().equalsIgnoreCase(dayafter.toString())) {
            // 변경 전 날짜랑 변경 후 날짜랑 같은경우
            System.out.println(" 변경 전 날짜랑 변경 후 날짜랑 같은경우");
            for (int i = 0; i < updateTarget_elem.getAsJsonArray().size(); i++) {
               if (updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(daybefore)) {// 이따가 else
                  // 제이슨어레이를 돌아다니면서//전체일정어레이에서 내가 일정을 추가하고자 하는 일의 인덱스를 찾았다면
                  update_day_idx = i;
                  System.out.println("날짜 같은거 찾았다!!");
                  System.out.println(daybefore);
                  System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day"));
                  for (int j = 0; j < updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                     if (updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(id)) {//
                        //// 그 일 안에있는 스케줄어레이 안에서 내가 찾는 스케쥴아이디가 어디에 있는지있는지 찾아보고
                        update_id_idx = j;// 스케줄어레이에서 내가 변경하고자 하는 스케줄아이디를 갖고있는 오브젝트의 인덱스
                        System.out.println("아이디 같은거 찾았다!!");
                        System.out.println(id);
                        System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
                        for (int j2 = 0; j2 < objectKeys.size(); j2++) {
                           updateTarget_elem.getAsJsonArray().get(update_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().get(update_id_idx).getAsJsonObject().remove(objectKeys.get(j2));
                           updateTarget_elem.getAsJsonArray().get(update_day_idx).getAsJsonObject().get("schedule").getAsJsonArray().get(update_id_idx).getAsJsonObject().add(objectKeys.get(j2), changeElement.get(objectKeys.get(j2)));// 추가하고
                        }
                        break;
                     } //
                     else {
                        System.out.println("못찾을리가 없다진짜,,,");
                     }
                  }
                  break;
               }
            }
            ///여기 넣어야하나?
            
         } else {
            // 변경 전 날짜랑 변경 후 날짜랑 다른경우
            System.out.println("변경 전 날짜랑 변경 후 날짜랑 다른경우");
            JsonObject beforeObject = new JsonObject();
            for (int i = 0; i < updateTarget_elem.getAsJsonArray().size(); i++) {
               if (updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(daybefore)) {
                  //변경 전 날짜를 찾아서 해당 오브젝트를 꺼내자
                  System.out.println("변경 전 날짜를 찾아서 해당 오브젝트를 꺼내자");
                  System.out.println("날짜 같은거 찾았다!!");
                  System.out.println(daybefore);
                  System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day"));
                  for (int j = 0; j < updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                     if(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(id)) {
                        //해당되는 스케쥴아이디를 찾아서 변경함
                        System.out.println("아이디 같은거 찾았다!!");
                        System.out.println(id);
                        System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j));
                        System.out.println("updateTarget_elem : "+updateTarget_elem);
                        beforeObject=(JsonObject) updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
                        System.out.println("beforeObject : "+beforeObject);
                        System.out.println("updateTarget_elem : "+updateTarget_elem);
                        if(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
                           // 변경 전 날짜에 스케줄이 하나밖에 없을경우 (옮기면 해당 day를 가지는 제이슨오브젝트를 삭제해야함)
                           System.out.println(" 변경 전 날짜에 스케줄이 하나밖에 없을경우 (옮기면 해당 day를 가지는 제이슨오브젝트를 삭제해야함)");
                           updateTarget_elem.getAsJsonArray().remove(i);
                           System.out.println(updateTarget_elem);
                        }
                        
                        break;
                     }
                  }

                  for (int j2 = 0; j2 < objectKeys.size(); j2++) {

                     beforeObject.remove(objectKeys.get(j2));
                     beforeObject.add(objectKeys.get(j2), changeElement.get(objectKeys.get(j2)));// 추가하고
                     System.out.println("변경 후 beforeObject : "+beforeObject);
                  }
                  
                  break;
               }
            }
            
            for (int i = 0; i < updateTarget_elem.getAsJsonArray().size(); i++) {
//               System.out.println(i);
               if (updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(dayafter)) {
                  // 변경 후 날짜가 전체 어레이에 존재하는경우
                  System.out.println("변경 후 날짜가 전체 어레이에 존재하는경우");
                  System.out.println(dayafter);
                  System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day").toString());
                  updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().add(beforeObject);
                  System.out.println(updateTarget_elem);
//                   해당인덱스에 해당하는 스케줄어레이에 오브젝트를 add한다
                  break;
               } else if(i==updateTarget_elem.getAsJsonArray().size()-1) {
                  // 변경 후 날짜가 전체어레이에 존재하지않는경우.
                  System.out.println(i);
                  System.out.println("변경 후 날짜가 전체어레이에 존재하지않는경우");
                  JsonArray newschedule = new JsonArray();
                  newschedule.add(beforeObject);
                  JsonObject newobject = new JsonObject();
                  newobject.add("day", dayafter);
                  newobject.add("schedule", newschedule);
                  updateTarget_elem.add(newobject);
                  System.out.println(updateTarget_elem);
                  // {"":"","":[]} 오브젝트를 생성하여 전체어레이에 add한다.
                  break;
               }
            }
         }
       System.out.println("최종 updateTarget_elem : "+updateTarget_elem);
       
      SchDTO dto_update = new SchDTO();
      dto_update.setSch_month(year_month);
      dto_update.setWs_code(ws_code);
      dto_update.setSch_schedule(updateTarget_elem.toString());
       service.updateSchInfo(dto_update);
       
         
         
       ////////////////////////////////////////////이 밑에는 코드 하 더이상 생각하기싫어,,,ㅎ ㅠㅜ
       //여기는 업뎃업뎃 또는 인서트업뎃 이렇게 두개 해줘야되거든,,, 
      } else {
    	  System.out.println("변경전데이터와 변경 후 데이터가 월이 다를 경우");
    	  JsonObject beforeObject = new JsonObject();
    	  //변경전데이터와 변경 후 데이터가 월이 다를 경우
         if(updateTarget_elem_after.size()!=0) {
        	 System.out.println("변경 후 다음달 월 어레이에 스케쥴이 하나라도 존재할 경우");
        	 System.out.println("updateTarget_elem : "+updateTarget_elem);
            for (int i = 0; i < updateTarget_elem.getAsJsonArray().size(); i++) {
               if(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(daybefore)) {
            	   //변겅전 전체어레이에서 day와 같은것을 찾고 해당 오브젝트를 꺼내자
                   System.out.println("변경 전 날짜를 찾아서 해당 오브젝트를 꺼내자");
                   System.out.println("날짜 같은거 찾았다!!");
                   System.out.println("daybefore : "+daybefore);
                   System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day"));
                  for(int j = 0; j < updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                     if(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(id)) {
                    	 System.out.println("해당 day의 schedule에서 id와 같은것을 찾아서 뽑아온다(remove)사용");
                    	//해당되는 스케쥴아이디를 찾아서 변경함
                         System.out.println("아이디 같은거 찾았다!!");
                         System.out.println(id);
                         System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j));
                         System.out.println("updateTarget_elem : "+updateTarget_elem);
                         beforeObject=(JsonObject) updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
                         System.out.println("beforeObject : "+beforeObject);
                         System.out.println("updateTarget_elem : "+updateTarget_elem);
                        
                        if(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
                            // 변경 전 날짜에 스케줄이 하나밖에 없을경우 (옮기면 해당 day를 가지는 제이슨오브젝트를 삭제해야함)
                            System.out.println(" 변경 전 날짜에 스케줄이 하나밖에 없을경우 (옮기면 해당 day를 가지는 제이슨오브젝트를 삭제해야함)");
                            updateTarget_elem.getAsJsonArray().remove(i);
                            System.out.println(updateTarget_elem);
                            //updateTarget_elem의길이가 흠 그거까지 해야된다고?? 저쪽에서 안돼?? create할때 안된다고?? 
                        }
                        break;
                     }
                     
                  }
////                  스케줄만 생성해서 스케쥴만 추가해주면댐 //근데 스케쥴을어떻게 생성해줘야하지,,? 
                for (int j2 = 0; j2 < objectKeys.size(); j2++) {
                    beforeObject.remove(objectKeys.get(j2));
                    beforeObject.add(objectKeys.get(j2), changeElement.get(objectKeys.get(j2)));// 추가하고
                    System.out.println("변경 후 beforeObject : "+beforeObject);
                 }
                  break;
               }
            }
               for(int i = 0; i < updateTarget_elem_after.getAsJsonArray().size(); i++){
            	   System.out.println("▒▒▒▒▒▒▒▒여기 들어는 오니??,,?▒▒▒▒▒▒▒▒");
                     if (updateTarget_elem_after.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(dayafter)){
                    	 System.out.println("변경 후 날짜가  변경후 월어레이에 존재하는경우");//날짜찾아서 
                    	   //변경 전 날짜를 찾아서 해당 오브젝트를 꺼내자
                         System.out.println("변경 후 날짜를 찾아서 해당 오브젝트를 꺼내자");
                         System.out.println("날짜 같은거 찾았다!!");
                         System.out.println("dayafter : "+dayafter);
                         System.out.println(updateTarget_elem_after.getAsJsonArray().get(i).getAsJsonObject().get("day"));
                         updateTarget_elem_after.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().add(beforeObject);
                         System.out.println("updateTarget_elem_after : "+updateTarget_elem_after);
                         break;
                         
                     } else {
                    	 System.out.println("변경 후 날짜가 변경후전체어레이에 존재하지않는경우");
                         JsonElement dayAndSchedule = new JsonObject();
                         JsonArray scheduleArray = new JsonArray();
                         scheduleArray.add(beforeObject);
                         dayAndSchedule.getAsJsonObject().add("day", dayafter);
                         dayAndSchedule.getAsJsonObject().add("schedule", scheduleArray);
                         updateTarget_elem_after.add(dayAndSchedule);//아 이게 전체어레이니까 괜찮겠지??
                         System.out.println("updateTarget_elem_after : "+updateTarget_elem_after);
                        // {"":"","":[]} 오브젝트를 생성하여 전체어레이에 add한다.\
                    	// 스케줄만들고
                    	 //날짜만들고 스케줄만들고 스케줄넣고
                    	 //그다음에 전체어레이에 넣고 
                         break;
                     }
                  }
          System.out.println(updateTarget_elem.size());
          SchDTO dto_update = new SchDTO();
          dto_update.setSch_month(year_month);
          dto_update.setWs_code(ws_code);
          dto_update.setSch_schedule(updateTarget_elem.toString());
          if(updateTarget_elem.size()==0) {
        	 service.deleteSchInfo(dto_update) ;
          }else {
        	  service.updateSchInfo(dto_update); 
          }

           
           
           SchDTO dto_update_after = new SchDTO();
           dto_update_after.setSch_month(monthafter);
           dto_update_after.setWs_code(ws_code);
           dto_update_after.setSch_schedule(updateTarget_elem_after.toString());
           service.updateSchInfo(dto_update_after);
            
            
         }else {
            JsonArray newjsonArray = new JsonArray();
            System.out.println("변경 후 월어레이에 아무런 데이터가 없을 경우");
//            //
            JsonObject beforeObj=new JsonObject();
            for (int i = 0; i < updateTarget_elem.getAsJsonArray().size(); i++) {
               if(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(daybefore)) {
            	   //변겅전 전체어레이에서 day와 같은것을 찾아서 해당 오브젝트를 꺼내자
                   System.out.println("변경 전 날짜를 찾아서 해당 오브젝트를 꺼내자");
                   System.out.println("날짜 같은거 찾았다!!");
                   System.out.println(daybefore);
                   System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day"));
                  for(int j = 0; j < updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                     if(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(id)) {
                    	 System.out.println("해당 day의 schedule에서 id와 같은것을 찾아서 뽑아온다(remove)사용");
                         //해당되는 스케쥴아이디를 찾아서 변경함
                         System.out.println("아이디 같은거 찾았다!!");
                         System.out.println(id);
                         System.out.println(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j));
                         System.out.println("updateTarget_elem : "+updateTarget_elem);
                         beforeObj=(JsonObject) updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
                         System.out.println("beforeObject : "+beforeObj);
                         System.out.println("updateTarget_elem : "+updateTarget_elem);
                        if(updateTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
                            // 변경 전 날짜에 스케줄이 하나밖에 없을경우 (옮기면 해당 day를 가지는 제이슨오브젝트를 삭제해야함)
                            System.out.println(" 변경 전 날짜에 스케줄이 하나밖에 없을경우 (옮기면 해당 day를 가지는 제이슨오브젝트를 삭제해야함)");
                            updateTarget_elem.getAsJsonArray().remove(i);
                            System.out.println("updateTarget_elem : "+updateTarget_elem);
                        }
                        break;
                     }
                     
                  }
	//              스케줄만 생성해서 스케쥴만 추가해주면댐 //근데 스케쥴을어떻게 생성해줘야하지,,? 
	            for (int j2 = 0; j2 < objectKeys.size(); j2++) {
	            	beforeObj.remove(objectKeys.get(j2));
	            	beforeObj.add(objectKeys.get(j2), changeElement.get(objectKeys.get(j2)));// 추가하고
	                System.out.println("변경 후 beforeObject : "+beforeObj);
	             }
               }
            }
            //디비에 저장되어있지않다면 ex) 디비에는 2020-04월까지의 스케쥴만 저장되어있고 내가 처음으로 2020-05에 대한 스케쥴을 입력하고자 할때 해당 조건문에 들어오게 됩니당
            System.out.println("null값 -------------insert문 실행");
            JsonArray jsonArray_schedule =new JsonArray();//앗 저 위에 잘못했다 ,,ㅎ 큰 어레이 안만들어줬다,,ㅎ
            //해당 날짜에 해당하는 스케쥴을 저장하는 스케쥴 제이슨 어레이 
            jsonArray_schedule.add(beforeObj);
            //스케쥴어레이에 입력한 현재 저장하려고 하는 스케쥴에 대한정보를 어펜드하고 
            JsonObject insertSchedule =new JsonObject();
            //처음으로 insert를 해야하기 때문에 json객체를 다시 생성하고
            insertSchedule.add("day", dayafter);
            //day 에 대한 정보를 제이슨에 입력해주고
            insertSchedule.add("schedule", jsonArray_schedule);
            //schedule에 대한 정보를 제이슨에 입력해주어서 입력해야 할 제이슨 객체 완성
            newjsonArray.add(insertSchedule);
            //완성된 객체를 전체 어레이에 넣고 
            System.out.println("newjsonArray : "+newjsonArray.toString());
            SchDTO dto = new SchDTO();
            dto.setSch_month(monthafter);
            dto.setWs_code(ws_code);
            //해당 월에 대한 정보와
            dto.setSch_schedule(newjsonArray.toString());
            //스케쥴에 대한 정보를 스트링으로 바꾸어
            service.insertSchInfo(dto);
//             디비에 저장
            SchDTO dto_update = new SchDTO();
            dto_update.setSch_month(year_month);
            dto_update.setWs_code(ws_code);
            dto_update.setSch_schedule(updateTarget_elem.toString());
             service.updateSchInfo(dto_update);
         }
         
      }
      
      System.out.println("===================================");
      
      return json.toString();
   }
   
   @RequestMapping(value="/deleteScheduleData.do",method=RequestMethod.POST)
   public String deleteScheduleData(String deleteInfo,HttpSession session) {
//      System.out.println("===========deleteScheduleData============");
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");
      
      System.out.println(deleteInfo);
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(deleteInfo);
      
      String year_month=element.getAsJsonObject().remove("year_month").toString().substring(1,8);
      System.out.println(year_month);
      JsonElement day=parser.parse((element.getAsJsonObject().get("start_update").toString().substring(9,11)));
      JsonElement id=element.getAsJsonObject().get("id");

      //화면에서 요청받은 스케쥴을 파서를 통해 스트링을 제이슨형태로 변경 (밑에서 사용할 신청받은스케쥴의 정보는 다 element사용)
      SchDTO dto_select = new SchDTO();
      dto_select.setSch_month(year_month); 
      dto_select.setWs_code(ws_code);
      String deleteTarget_str = service.selectSchInfo(dto_select);
      //삭제할 정보를 갖고있는 행을 디비에서 가져오고 
      JsonArray deleteTarget_elem = (JsonArray) parser.parse(deleteTarget_str);
      //그 행을 제이슨어레이으로 다시 변환한 다음에 
      System.out.println(deleteTarget_elem);
//      deleteTarget_elem.getAsJsonArray().iterator() // 이거 나중에 써보고 일단 for문으로 돌리자,,^^
      for (int i = 0; i < deleteTarget_elem.getAsJsonArray().size(); i++) {
//         //제이슨 안에 "day":day를 갖고있는 제이슨이 몇번째 인지 찾아내서
         if(deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day").equals(day)) {
            System.out.println("찾았다!!날짜!!");
            System.out.println(i);
            System.out.println(day);
            System.out.println(deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("day"));
//            //그 인덱스번호에 해당하는 스케쥴제이슨어레이로 들어가서 지금 요청된 스케쥴아이디를 갖고있는 제이슨의 인덱스를 찾아서
            for (int j = 0; j < deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
//               //그 인덱스에 해당하는 제이슨을 삭제해야해!! 
               if(deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id").equals(id)) {
                  System.out.println("찾았다!!스케쥴아이디!!!!");
                  System.out.println(j);
                  System.out.println(id);
                  System.out.println(deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray());
                  deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().remove(j);
                  //만약에 하나밖에없다면 일자도 지워야겠네 day 1 이거까지 지워야겠네~~
                  if(deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().size()==0) {
                     deleteTarget_elem.getAsJsonArray().remove(i);
                  }
//                  System.out.println(deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray());
                  System.out.println(deleteTarget_elem.toString());
                  SchDTO dto = new SchDTO();
                  dto.setSch_month(year_month); 
                  dto.setWs_code(ws_code);
                  dto.setSch_schedule(deleteTarget_elem.toString());
                  service.updateSchInfo(dto);
                  break;
               }
                  else {
                  System.out.println("===========================");
                  System.out.println("못찾았음 ㅠㅜㅠㅜ스케쥴아이디!그럴리가 없는데,,!!");
                  System.out.println(deleteTarget_elem.getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject().get("id"));
                  System.out.println("현재 위치(index j : "+j);
                  System.out.println("찾는 id : "+id);
                  System.out.println("===========================");
               }
            }
            break;
         }
      }
      
      System.out.println("===================================");
      JsonObject json = new JsonObject();
      return json.toString();
   }
   
   @RequestMapping(value="/exchange.do",method=RequestMethod.POST)//ㅎ 어떻게 했는지 까먹엇따,,ㅎ 하나씩 읽어보면서 한줄씩 주석달면서 해야겠다,,,ㅎ 
   public String exchangeData(String userId,String id,String calendarId,HttpSession session) {
      
		UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
		session.setAttribute("id", user.getUser_id());//지금 직원이라고 생각합시다~~ 
		String ws_code = (String) session.getAttribute("ws_code");
	     System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+ws_code+"받았다~~~▒▒▒▒▒▒▒");
		String rank=(String)session.getAttribute("rank");
      System.out.println("현재 클릭된 스케쥴주인의 아이디 : "+userId);
      JsonParser parser = new JsonParser();
//      JsonObject json = new JsonObject();
//      return json.toString();
      //현재 스케쥴설정을하는 사업자이 어떤 사업장인지 알아야해
      //직원테이블에서 현재사업장에 근무하는 직원이 누구인지 아이디와 직급 이름 목록을 뽑아와서 (select에서 컨켄해서 가져와야겠따)
//      ArrayList<String> memberId_Name = new ArrayList<String>();
      EmpDTO dto = new EmpDTO();
      dto.setEmp_rank(rank);
      dto.setWs_code(ws_code);
      ArrayList<String> memberId_Name = (ArrayList<String>) service_emp.selectWsMember(dto);
      System.out.println(memberId_Name);
      
      Iterator<String> ite = memberId_Name.iterator();
      
      while(ite.hasNext()) {
           String str = ite.next();
           if(str.contains(user.getUser_id())) {
                ite.remove();
           }
      }
      
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("employee_rank", rank.equals("emp")?"G":"M");
		map.put("ws_code", ws_code);
		SchBasicDTO dtoBasic=service_basic.schBasicChkSelect(map);
		String schedule_basic_str=dtoBasic.getSchbasic_json();
      
      
      JsonObject schedule_basic_obj = (JsonObject) parser.parse(schedule_basic_str);
      //내가 클릭한 스케쥴이 어디일정에 해당하는건지 파악하기위해서 기초사항을 가져옴!! (이거 어떻게 가져와야하나여 오빠,, 쿼리 나 아직안봤는데에,,,,하핳,,,염치,,,노노,,,)
      System.out.println(schedule_basic_obj.get("BASIC"));
      String work_start_month=schedule_basic_obj.get("BASIC").getAsJsonObject().get("WORK_START").getAsString().substring(0, 7);
      //시작날짜의 연월
      System.out.println(work_start_month);
      String work_start_day=schedule_basic_obj.get("BASIC").getAsJsonObject().get("WORK_START").getAsString().substring(8, 10);
      //시작날짜의 일
      System.out.println(work_start_day);
      String work_end_month=schedule_basic_obj.get("BASIC").getAsJsonObject().get("WORK_END").getAsString().substring(0, 7);
      //일정종료날짜의 연월
      String work_end_day=schedule_basic_obj.get("BASIC").getAsJsonObject().get("WORK_END").getAsString().substring(8, 10);
      //일정종료날짜의 일
         
      JsonArray scheduleStartMonth_ele=new JsonArray();//service.selectSchInfo(work_start_month)
      JsonArray scheduleEndMonth_ele=new JsonArray();//service.selectSchInfo(work_end_month)
//      JsonArray array_end=new JsonArray();
      JsonArray schedule_ele= new JsonArray();
      JsonArray candidate = new JsonArray();


      String whatDay_ele;
//      JsonArray send = new JsonArray();
      if(work_start_month.equals(work_end_month)) {//일정시작연월과 일정종료연월이 같다면 시작월에 맞추어 디비에서 제이슨을 가져오고 
         System.out.println("같습니다~");
         SchDTO dto_select = new SchDTO();
         dto_select.setSch_month(work_start_month); 
         dto_select.setWs_code(ws_code);
         scheduleStartMonth_ele = nullChk(service.selectSchInfo(dto_select));
         System.out.println(scheduleStartMonth_ele);
//         1) 첫날과 막날이 같은 달일경우 첫날과 막날 사이에 있는 스케쥴을 가져오자!!
         for (int i = 0; i < scheduleStartMonth_ele.size(); i++) {
            whatDay_ele=scheduleStartMonth_ele.get(i).getAsJsonObject().get("day").toString();
            System.out.println(whatDay_ele);
            System.out.println(work_start_day);
            System.out.println(work_end_day);
            if((convertNumber(whatDay_ele).compareTo(work_start_day)>=0)&&(convertNumber(whatDay_ele).compareTo(work_end_day)<=0)) {
               //시작일과 종료일자를 비교하여 근무일정내에 존재하는 스케쥴을 가져오자!!
               for (int j = 0; j < scheduleStartMonth_ele.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                  candidate.add( scheduleStartMonth_ele.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
                  //오브젝트형태로 가져와서 캔디데이트어레이에 하나씩 어펜드 해주자!! 
               }
            }
         }
      }
         else {
         System.out.println("다릅니다~");
////         2) 첫날과 막날이 다른 달일경우
////         첫날의 달 과 첫날일을 기준으로 크거나 같은경우의 일정만 골라서 가져오고
////         막날의 달과 막날을 기준으로 작거나 같은경우의 일정만 골라서 가져오자!!
         SchDTO dto_select = new SchDTO();
         dto_select.setSch_month(work_start_month); 
         dto_select.setWs_code(ws_code);
         scheduleStartMonth_ele = nullChk(service.selectSchInfo(dto_select));
         //ㅏ첫날과 막날의 연월이 다를경우 따로 구한다
         // 첫날의 연월을 가지고 디비에서 해당 연월의 데이터를 가져와서 
         System.out.println(scheduleStartMonth_ele);
//         
         for (int i = 0; i < scheduleStartMonth_ele.size(); i++) {
            whatDay_ele=scheduleStartMonth_ele.get(i).getAsJsonObject().get("day").toString();
            if(convertNumber(whatDay_ele).compareTo(work_start_day)>=0) {
               //첫날보다 크거나 같은애들만 오브젝트의 형태로 캔디데이트에 어펜드하고 
               for (int j = 0; j < scheduleStartMonth_ele.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                  candidate.add( scheduleStartMonth_ele.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
               }
            }
         }
         SchDTO dto_select_another = new SchDTO();
         dto_select_another.setSch_month(work_end_month); 
         dto_select_another.setWs_code(ws_code);
         scheduleEndMonth_ele = nullChk(service.selectSchInfo(dto_select_another));
         //막날의 연월을 가지고 디비에서 데이터를 가지고와서 
         System.out.println(scheduleEndMonth_ele);
         for (int i = 0; i < scheduleEndMonth_ele.size(); i++) {
            whatDay_ele=scheduleEndMonth_ele.get(i).getAsJsonObject().get("day").toString();
            if(convertNumber(whatDay_ele).compareTo(work_end_day)<=0) {
               // 막날보다 작거나 같은 일정들만 오브젝트의 형태로 캔디데이트에 어팬드를 하면
               for (int j = 0; j < scheduleEndMonth_ele.get(i).getAsJsonObject().get("schedule").getAsJsonArray().size(); j++) {
                  candidate.add(scheduleEndMonth_ele.get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(j).getAsJsonObject());
               }
            }
         }
      }
      // 자 지금 캔디데이트에는 해당 근무일정에 대한 스케줄이 쫘르르 그냥 있어 흠,,,
      System.out.println("자 필요한 일정들 싸악 뽑아 봤습니다~");
      System.out.println(candidate.size());
      System.out.println(candidate);
      
      for (int i = 0; i < candidate.size(); i++) {
         if(candidate.get(i).getAsJsonObject().get("calendarId").toString().equalsIgnoreCase(calendarId)) {
            candidate.remove(i);
         }
      }
      
      JsonObject sendResult = new JsonObject();
      for (int i = 0; i < memberId_Name.size(); i++) {
         JsonArray send = new JsonArray();
         for (int j = 0; j < candidate.size(); j++) {
            if(memberId_Name.get(i).contains(candidate.get(j).getAsJsonObject().get("title").getAsString())){
               System.out.println(memberId_Name.get(i));
               System.out.println(candidate.get(j).getAsJsonObject().get("title").getAsString());
               System.out.println("candi : "+candidate);
               send.add(candidate.get(j));
               System.out.println(candidate);
               System.out.println(send);
            }
         }
         
         sendResult.add(memberId_Name.get(i), send);
      }
      System.out.println("넘어갈 제이슨 : "+sendResult);
      
      
      
      
      return sendResult.toString();
   }
   
   

}
