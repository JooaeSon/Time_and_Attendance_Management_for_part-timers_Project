
package com.min.sc.ws.ctrl;

import java.util.List;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




import com.min.sc.emp.dtos.EmpUserDTO;

import com.min.sc.emp.model.IService_Emp;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;
import com.min.sc.ws.model.IService_Ws;

@Controller
public class WsCtrl {

   private Logger log = LoggerFactory.getLogger(this.getClass());

   @Autowired
   private IService_Ws service;
   
   @Autowired
   private IService_Emp service_emp;

   // 06.11
   /**
    * 로그인한 회원의 타입을 판단후, 사업장 소유 혹은 존재 여부 확인 후 각 조건에 맞는 화면으로 분기하는 기능
    * 
    * @param session
    * @param model
    * @param ws_code_selected
    * @return ws/insertFormo, ws/select, emp/workSearch, ws/selectWS
    */
   @RequestMapping(value = "/ws.do", method = RequestMethod.GET)
   public String ws(HttpSession session, Model model, String ws_code_selected) {
      // session에서 user_id와 user_type을 get
      UserInfoDTO UIDto=(UserInfoDTO) session.getAttribute("user");
      String user_id=UIDto.getUser_id();
      String user_type=UIDto.getUser_type();
      
      if(user_type.equalsIgnoreCase("employer")) { // 고용주 회원
         String ws_code=service.wsErCodeSelect(user_id); // List<String>
         if(ws_code==null) { // 소유한 사업장 존재 x
            return "ws/insertForm"; // 사업장 등록(정보 입력) form
         } else { // 소유한 사업장 존재 o
            WorkSpaceDTO WSDto=service.wsInfoSelect(ws_code);
            model.addAttribute("WSDto", WSDto);
            session.setAttribute("ws_code", ws_code);
            return "ws/select"; // 사업장 정보 조회
         }
      } else { // 일반 회원
         List<String> ws_code_lists=service.wsEeCodeSelect(user_id); // ws_code: List<String>
         if(ws_code_lists.size()==0) { // 소속된 사업장 존재 x
            model.addAttribute("msg","소속된 사업장이 존재하지 않습니다, 사업장 검색 페이지로 이동합니다!");
            List<WorkSpaceDTO> lists = service_emp.wsSearch();
				    model.addAttribute("lists", lists);
            return "emp/workSearch";
         } else { // 소속된 사업장 존재 o
            model.addAttribute("ws_code_lists", ws_code_lists);
            if(ws_code_selected!=null) {
               session.setAttribute("ws_code", ws_code_selected); 
               return "redirect:select.do";
            }
            return "ws/selectWS";
         }
      }
   }
   
   // 사업장 등록(정보 입력)
   /**
    * 사업장 등록(정보 입력)을 하는 기능
    * 
    * @param session
    * @param WSDto
    * @param model
    * @param ws_num1
    * @param ws_num2
    * @return redirect:select.do
    */
   @RequestMapping(value = "/insert.do", method = RequestMethod.POST)
   public String insert(HttpSession session, WorkSpaceDTO WSDto, Model model, String ws_num1, String ws_num2) {
      // user_id - session에서 get
      UserInfoDTO UIDto=(UserInfoDTO)session.getAttribute("user");
      String user_id=UIDto.getUser_id();
      
      // ws_code - 등록날짜6자리+user_id
      String nowDate=service.wsDateSelect();
      String ws_code=nowDate+user_id;
      
      // ws_num
      String ws_num=ws_num1+ws_num2;
      
      // ws_ip: 숫자 10자리
      String ws_ip=WSDto.getWs_ip();
        String ws_ip_refined =ws_ip.substring(0, 3)+"."
        +ws_ip.substring(3,6)+"."
        +ws_ip.substring(6, 8)+"."
        +ws_ip.substring(8);
      
      // WorkSpaceDTO 에 ws_code, user_id, ws_num, ws_ip 값을 set
      WSDto.setWs_code(ws_code);
      WSDto.setUser_id(user_id);
      WSDto.setWs_num(ws_num);
      WSDto.setWs_ip(ws_ip_refined);
      
      // wsInfoInsert Query 실행
      service.wsInfoInsert(WSDto);
      
      model.addAttribute("ws_code", ws_code);
      
      return "redirect:select.do";
   }

   /**
    * 일반 회원중에 사업장에 소속된 회원이 정보 조회를 원하는 사업장을 선택하는 기능
    * 
    * @param session
    * @param model
    * @return ws/selectWS
    */
	@RequestMapping(value = "/selectWS.do", method = RequestMethod.GET)
	public String selectWS(HttpSession session, Model model) {
		List<String> ws_code=(List<String>) session.getAttribute("ws_code");
		model.addAttribute("ws_code", ws_code);
		
		return "ws/selectWS";
	}

	// 사업장 정보 조회
	/**
	 * 선택한 사업장의 정보를 조회하는 기능
	 * 
	 * @param ws_code_selected
	 * @param session
	 * @param model
	 * @param ws_code
	 * @param EUDto
	 * @return ws/select
	 */
	@RequestMapping(value = "/select.do", method = RequestMethod.GET)
	public String select(String ws_code_selected, HttpSession session, Model model, String ws_code, 
                       EmpUserDTO EUDto) {
		 String user_id = ((UserInfoDTO) session.getAttribute("user")).getUser_id();
     String rank="";
     EUDto.setUser_id(user_id);
    if (ws_code_selected!=null) {
		WorkSpaceDTO WSDto=service.wsInfoSelect(ws_code_selected);
      session.setAttribute("ws_code", ws_code_selected);
      EUDto.setWs_code(ws_code_selected);
      rank=service_emp.empSelect(EUDto).getEmp_rank(); // 직급별 직원조회
      session.setAttribute("rank", rank);
		model.addAttribute("WSDto", WSDto);
		}else {
			WorkSpaceDTO WSDto=service.wsInfoSelect(ws_code);
      session.setAttribute("ws_code", ws_code);
      EUDto.setWs_code(ws_code);
      if (service_emp.empSelect(EUDto)!=null) {
		
    	  rank=service_emp.empSelect(EUDto).getEmp_rank();
	}
      session.setAttribute("rank", rank);
			model.addAttribute("WSDto", WSDto);
		}
		
		return "ws/select";
	}

   
   // 사업장 정보 수정 form
	/**
	 * 사업장 정보수정 form 으로 이동하는 기능
	 * 
	 * @param session
	 * @param model
	 * @return ws/updateForm
	 */
   @RequestMapping(value = "/updateForm.do", method = RequestMethod.GET)
   public String updateForm(HttpSession session, Model model) {
      String ws_code=(String)session.getAttribute("ws_code");
      WorkSpaceDTO WSDto=service.wsInfoSelect(ws_code);
      model.addAttribute("WSDto", WSDto);
      
      return "ws/updateForm";
   }
   
   // 사업장 정보 수정
   /**
    * 사업장 정보수정을 하는 기능
    * 
    * @param WSDto
    * @param model
    * @param ws_num1
    * @param ws_num2
    * @return redirect:select.do
    */
   @RequestMapping(value = "/update.do", method = RequestMethod.POST)
   public String upate(WorkSpaceDTO WSDto, Model model, String ws_num1, String ws_num2) {
      String ws_num=ws_num1+ws_num2;
      
      WSDto.setWs_num(ws_num);
      String ws_ip=WSDto.getWs_ip();
      String ws_ip_refined =ws_ip.substring(0, 3)+"."
    	        +ws_ip.substring(3,6)+"."
    	        +ws_ip.substring(6, 8)+"."
    	        +ws_ip.substring(8);
      WSDto.setWs_ip(ws_ip_refined);
      service.wsInfoUpdate(WSDto);
      
      String ws_code=WSDto.getWs_code();
      model.addAttribute("ws_code", ws_code);
      
      return "redirect:select.do";
   }
   
   // 사업장 삭제 요청
   /**
    * 사업장 삭제요청을 하는 기능
    * 
    * @param session
    * @param model
    * @return redirect:select.do
    */
   @RequestMapping(value = "/delReq.do", method = RequestMethod.GET)
   public String delReq(HttpSession session, Model model) {
      // 해당 사업장에 관련된 처리중인 삭제 요청이 있는지 조회
      String ws_code=(String)session.getAttribute("ws_code");
      String ws_delflag=service.wsReqSelect(ws_code);
      if(!ws_delflag.equalsIgnoreCase("R")) { // 처리중인 삭제 요청 존재x
         service.wsDelReqUpdate(ws_code);
      } 
      model.addAttribute("ws_code", ws_code);
      return "redirect:select.do";
   }
   
   // 사업장 삭제요청목록 조회
   /**
    * 사업장 삭제요청 목록을 조회하는 기능
    * 
    * @param model
    * @return ws/admin
    */
   @RequestMapping(value = "/adminWsDeleteForm.do", method = RequestMethod.GET)
   public String adminWsDeleteForm(Model model) {
      // 관리자가 사업장 삭제 요청을 조회
      List<WorkSpaceDTO> lists=service.wsDelReqSelect();
      if(lists.size()!=0) { // 사업장 삭제 요청 존재o
         model.addAttribute("lists", lists);
      }
      return "ws/admin";
   }
   
   // 관리자가 사업장 삭제요청 승인
   /**
    * 관리자가 사업장 삭제요청을 승인하는 기능
    * 
    * @param ws_code
    * @return redirect:/adminWsDeleteForm.do
    */
   @RequestMapping(value = "/wsDelReqYUpdate.do", method = RequestMethod.GET)
   public String wsDelReqYUpdate(String ws_code) {
      service.wsDelReqYUpdate(ws_code);
      
      return "redirect:/adminWsDeleteForm.do";
   }
   
   // 관리자가 사업장 삭제요청 거절
   /**
    * 관리자가 사업장 삭제요청을 거절하는 기능
    * 
    * @param ws_code
    * @return redirect:/adminWsDeleteForm.do
    */
   @RequestMapping(value = "/wsDelReqNUpdate.do", method = RequestMethod.GET)
   public String wsDelReqNUpdate(String ws_code) {
      service.wsDelReqNUpdate(ws_code);   
      
      return "redirect:/adminWsDeleteForm.do";
   }
   
   
   
   // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
}

