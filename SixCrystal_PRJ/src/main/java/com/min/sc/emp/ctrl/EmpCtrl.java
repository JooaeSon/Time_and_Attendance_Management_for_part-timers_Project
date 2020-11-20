package com.min.sc.emp.ctrl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.sc.emp.dtos.EmpDTO;
import com.min.sc.emp.dtos.EmpUserDTO;
import com.min.sc.emp.model.IService_Emp;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;
import com.min.sc.ws.model.IService_Ws;

@Controller
public class EmpCtrl {

   
   private Logger log = LoggerFactory.getLogger(this.getClass());

   @Autowired
   private IService_Emp service;

   @Autowired
   private IService_Ws wsservice;
   
   @RequestMapping(value = "/empSearch.do", method = RequestMethod.GET)
   public String empSelect(Model model,HttpSession session, EmpUserDTO Edto) {
      List<EmpUserDTO> lists = service.empAllSelect((String)session.getAttribute("ws_code"));
      String ws_code = Edto.getWs_code();
      model.addAttribute("ws_code", ws_code);
      System.out.println("@@@@@@@@@@@@@@@@"+lists);
//      String user_id = Edto.getUser_id();
//	   model.addAttribute("user_id", user_id);

      model.addAttribute("lists", lists);
      return "emp/empSearch";
   }

//   @RequestMapping(value = "/empIndexing.do", method = RequestMethod.GET)
//   public String empIndexing() {
//      LuceneSearch searcher = new LuceneSearch();
//      List<EmpDTO> lists = service.empAllSelect();
//      searcher.indexing(lists, null);
//      return "";
//   }
   
//   @RequestMapping(value = "workSpaceSelect.do", method = RequestMethod.GET)
//   public String wsSearch() {
//      return "workSpaceSelect";
//
//   }

//   @RequestMapping(value = "/workIndexing.do", method = RequestMethod.GET)
//   public String workIndexing() {
//      LuceneSearchWorkSpace searcher = new LuceneSearchWorkSpace();
//      List<WorkSpaceDTO> lists = wsservice.wsSearch();
//      searcher.indexing(lists, null);
//      return "";
//   }
   
   
   
   
   
//   사업장 검색
   @RequestMapping(value="/workSearch.do", method = RequestMethod.GET)
   public String workSearch(Model model) {
      List<WorkSpaceDTO> lists = service.wsSearch();
//      System.out.println("/////////////////"+lists);
//      service.empApplyInsert(dto);
      
      model.addAttribute("lists", lists);
         return "emp/workSearch";
   }
   
   /**
    * 사업장 고유코드 입력 후 입사신청시 자동으로 직원코드, 사업장코드, 입사승인여부'D'로 부여되는 기능
    * @param dto
    * @return workSearch.do
    */
//   @RequestMapping(value="/empAutoInsert.do", method = RequestMethod.GET)
//   public String empAutoInsert(EmpDTO dto, HttpSession session) {
//      UserInfoDTO UDto = (UserInfoDTO) session.getAttribute("user");
//      String user_id = UDto.getUser_id();
//      System.out.println("??????????"+user_id);
//      service.empApplyInsert(dto);
//      return "redirect:/workSearch.do";
//   }
   
   
   
   @RequestMapping(value="/workDetail.do", method = RequestMethod.GET)
   public String workDetail(String ws_code, Model model) {
      wsservice.wsInfoSelect(ws_code);
      model.addAttribute("lists",ws_code);
      return "emp/workDetail";
   }
   
//   사업장 상세보기
//   @RequestMapping(value = "/workDetail.do", method = RequestMethod.POST)
//   public String workDetail(String ws_name, WorkSpaceDTO dto, Model model) {
//      dto.setWs_name(dto.getWs_name());
//      dto.setWs_loc(dto.getWs_loc());
//      dto.setWs_num(dto.getWs_num());
//      dto.setWs_code(dto.getWs_code());
//      model.addAttribute("lists",dto);
//      wsservice.wsInfoSelect(dto.getWs_code());
//      System.out.println(dto);
//      return"workDetail";
//   }


   // 직원정보 상세보기
   @RequestMapping(value = "/empDetail.do", method = RequestMethod.POST)
   public String empDetailSelect(Model model, EmpUserDTO dto, HttpSession session) {
	   EmpUserDTO lists = service.empDetailSelect(dto);
	   model.addAttribute("lists", lists);
      return "emp/empDetail";
   }

   // 직원정보 수정하기
   @RequestMapping(value = "/empModify.do", method = RequestMethod.GET)
   public String empModify(Model model, EmpDTO dto, HttpSession session) {
      model.addAttribute("lists", dto);
      System.out.println(dto);
      return "emp/empModify";
   }

   
   
   
   @RequestMapping(value = "/empModifyOK.do", method = RequestMethod.POST)
   public String empModifyOK(EmpUserDTO dto, String emp_code) {
	   System.out.println("!!!!"+emp_code);
	   boolean isc = service.empUpdate(emp_code);
	   System.out.println("!!!!!!!!!"+dto.getEmp_code());
      return isc?"emp/empSearch":"emp/empModify";
   }


   /**
    * 입사승인 여부가 'D'인 회원 조회
    * @param model
    * @return empApply
    */
   @RequestMapping(value = "/empApply.do", method = RequestMethod.GET)
   public String empApply(Model model, HttpSession session, EmpUserDTO dto) {
      UserInfoDTO UDto = (UserInfoDTO) session.getAttribute("user");
//      System.out.println("???????????????"+UDto);
//      lists.getUser_id();
//      UDto.getUser_name();
//      WorkSpaceDTO WSDto = (WorkSpaceDTO)session.getAttribute("WSDto");
//      System.out.println(WSDto);
//      EmpUserDTO EDto = service.empConfirmSelect(WSDto.getWs_code()).get(0);
//      session.setAttribute("EDto", EDto);
//      System.out.println("!!!!!!!!!!!!!!!!!!!"+EDto);
//      model.addAttribute("EDto", EDto);
      
      List<EmpUserDTO> EDto = service.empConfirmSelect((String)session.getAttribute("ws_code"));
      session.setAttribute("EDto", EDto);
      System.out.println("!!!!!!!!!!!!!!!!!!!"+EDto);
      model.addAttribute("EDto", EDto);
      
      return "emp/empApply";
   }

   /**
    * 입사 승인 후 직원 정보 등록 하는 기능
    * @param dto
    * @return empInsert
    */
   @RequestMapping(value = "/empApplyYN.do", method = RequestMethod.GET)
   public String empApplyYN(Model model, HttpSession session,String emp_confirm, String ws_code, String user_name, String user_id,String emp_code) {
//      String id = principal.getName();
//      System.out.println("@@@@@@@"+id);
//      System.out.println(session.getAttribute("lists"));
      model.addAttribute("emp_code", emp_code);
      model.addAttribute("user_name", user_name);
      model.addAttribute("user_id", user_id);
      model.addAttribute("ws_code", ws_code);
      model.addAttribute("emp_confirm", emp_confirm);
      System.out.println("======================="+emp_confirm);
      
//      EDto.getEmp_code();
//      EmpUserDTO lists = service.empDetailSelect(id);
//      List<EmpUserDTO> lists =(List<EmpUserDTO>) session.getAttribute("lists");
//      System.out.println("@@@@@받았뜸~~~@@@@@@@@@"+lists);
//      EmpUserDTO dto = service.empDetailSelect(id);
//      dto.getEmp_code();
//      System.out.println(dto.getEmp_code());
//      lists.getEmp_code();
//      System.out.println(EDto);
//      model.addAttribute("lists", session.getAttribute("EDto"));
//      model.addAttribute("lists",lists);
//      UserInfoDTO UDto = (UserInfoDTO) session.getAttribute("user");
//      System.out.println(UDto+":::::::::::::::::::");
      System.out.println("*******empInsert로 가즈아~~`************");
      return "emp/empInsert";
   }
   
// 입사신청 거절
   @RequestMapping(value="/empReject.do", method = RequestMethod.GET)
   public String empReject(String emp_code, HttpSession session, Model model) {
	   System.out.println("!!!!!!!!!!!!!!"+emp_code);
	   service.empApplyReject(emp_code);
	   List<EmpUserDTO> lists = service.empConfirmSelect((String)session.getAttribute("ws_code"));
	   session.setAttribute("EDto", lists);
	   System.out.println("!!!!!!!!!!입사신청이 거절되었습니다.!! !!!!!!!!!"+lists);
	   model.addAttribute("EDto", lists);
	return "emp/empApply";
   }

   
//   @RequestMapping(value="/empCodeInsert.do",method = RequestMethod.GET)
//   public String empCodeInsert() {
//      
//   }
   @RequestMapping(value = "/empInsertOK.do", method = RequestMethod.GET)
   public String empInsert(HttpSession session, EmpDTO EDto, EmpUserDTO euDto,Model model) {
      System.out.println("????????????"+euDto);
//      System.out.println("!!!!!!!!!!!!!!"+dto);
//      UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
//      String id = user.getUser_id();
      //      log.info("직원등록 확인");
//      log.info(dto.getEmp_position() + ":" + dto.getEmp_worktime_time());
//      dto.getEmp_hiredate();
//      dto.getEmp_hiredate_end();
//      dto.getEmp_position();
//      dto.getEmp_worktime_time();
//      dto.getEmp_holiday();
//      dto.getEmp_salary();
//      dto.getEmp_salary_day();
//      dto.getEmp_apply();
//      dto.getEmp_rank();
//      dto.setUser_id(id);
//      session.setAttribute("", euDto);
//      System.out.println("??????????????????"+euDto);
      log.info("??????{}",euDto);
//      String emp_code = EDto.getEmp_code();
//      EDto.setEmp_code(emp_code);
      EDto.setEmp_code(euDto.getEmp_code());
      EDto.setWs_code(euDto.getWs_code());
      System.out.println(euDto.getWs_code());
      service.empApplyUpdate(EDto);
      List<EmpUserDTO> lists = service.empConfirmSelect((String)session.getAttribute("ws_code"));
      session.setAttribute("EDto", lists);
      System.out.println("!!!!!!!!!!입사신청완료!! !!!!!!!!!"+lists);
      model.addAttribute("EDto", lists);
      
//      System.out.println("성공:"+isc);
      return "emp/empApply";
//      return null;
   }
}