package com.min.sc.emp.model;

import java.util.List;
import java.util.Map;

import com.min.sc.emp.dtos.EmpDTO;
import com.min.sc.emp.dtos.EmpUserDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;

public interface IService_Emp {

//   사업장 조회
   public List<WorkSpaceDTO> wsSearch();
//   일반회원이 입사신청시 자동으로 입력되는 사항
   public boolean empApplyInsert(EmpDTO dto);
//   emp_confirm이 D인일반회원 조회
   public List<EmpUserDTO> empConfirmSelect(String ws_code);
//   입사승인전 입력하는 정보 update
   public boolean empApplyUpdate(EmpDTO dto);
// 직원상세정보
   public EmpUserDTO empDetailSelect(EmpUserDTO dto);
//   입사거절
   public boolean empApplyReject(String emp_code);
//   직원모두 조회
   public List<EmpUserDTO> empAllSelect(String ws_code);
//   직원조회(아이디)
   public EmpUserDTO empSelect(EmpUserDTO EUDto);
//   직원정보 수정
   public boolean empUpdate(String emp_code);
// 퇴사처리
   
   
   //스케줄에서 사용할 사업장 내 직급별 지원조회
   public List<String> selectWsMember(EmpDTO dto);
   
   
}