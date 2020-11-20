package com.min.sc.emp.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.lucene.search.MaxNonCompetitiveBoostAttribute;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.sc.emp.dtos.EmpDTO;
import com.min.sc.emp.model.IService_Emp;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;
import com.min.sc.ws.model.IService_Ws;
@Controller
public class AjaxController {
   private Logger log = LoggerFactory.getLogger(this.getClass());
   @Autowired
   private IService_Emp service;
   
   @Autowired
   private IService_Ws wsservice;
   
//   @RequestMapping(value="/empSearch.do",method = RequestMethod.POST)
//   @ResponseBody
//   public Map<String, Object> EmpSearch(String emp_code) {
////      log.info(emp_code);
//      log.info("Welcome EmpSearch");
////      LuceneSearch searcher = new LuceneSearch();
////      List<EmpDTO> list = searcher.Search(emp_code);
//      Map<String, Object> map = new HashMap<String, Object>();
//      map.put("emp_code", emp_code);
//      return map;
//   }
   
//   @RequestMapping(value="/workSpaceSelect.do",method = RequestMethod.POST)
//   @ResponseBody
//   public Map<String, Object> WorkSearch(String ws_name){
//      log.info("Welcome WorkSearch");
//      LuceneSearchWorkSpace searcher = new LuceneSearchWorkSpace();
//      List<WorkSpaceDTO> list = searcher.Search(ws_name);
//      Map<String, Object> map = new HashMap<String, Object>();
//      map.put("ws_name", list);
//      return map;
//   }
   
   @RequestMapping(value="/wscodeChk.do",method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> wscodeChk(String ws_code){
//      System.out.println("!!!!!!!!"+ws_code);
//      dto.setWs_code(ws_code);
//      model.addAttribute("ws_code", ws_code);
      WorkSpaceDTO  lists = wsservice.wsInfoSelect(ws_code);
      
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("lists", lists);
//      if(lists == null) {
//         map.put("isc", "실패");
//      }else {
//         map.put("isc", "성공");
//      }
      return map;
   }
   
   @RequestMapping(value="/empAutoInsert.do", method = RequestMethod.GET)
   @ResponseBody
   public Map<Object, String> empAutoInsert(EmpDTO dto, HttpSession session) {
      UserInfoDTO UDto = (UserInfoDTO) session.getAttribute("user");
      String user_id = UDto.getUser_id();
      System.out.println("??????????"+user_id);
      dto.setUser_id(user_id);
      service.empApplyInsert(dto);
      Map<Object, String> map = new HashMap<Object, String>();
      map.put("good", "축하합니다 입사신청이 되었습니다");
      return map;
   }
}


