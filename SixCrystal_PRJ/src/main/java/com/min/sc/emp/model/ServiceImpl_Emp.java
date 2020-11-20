
package com.min.sc.emp.model;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.sc.emp.dtos.EmpDTO;
import com.min.sc.emp.dtos.EmpUserDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;
import com.min.sc.ws.model.IDao_Ws;


@Service
public class ServiceImpl_Emp implements IService_Emp{

   private Logger log = LoggerFactory.getLogger(this.getClass());
   
   @Autowired
   private IDao_Emp dao;
   
   
//   일반회원이 입사신청시 자동으로 입력되는 사항
   @Override
   public boolean empApplyInsert(EmpDTO dto) {
      log.info("empApplyInsert 입사신청시 자동으로 입력되는 사항");
      return dao.empApplyInsert(dto);
   }
//   emp_confirm이 D인 회원 조회
   @Override
   public List<EmpUserDTO> empConfirmSelect(String ws_code) {
      log.info("emp_confirm이 D인 회원 조회");
      return dao.empConfirmSelect(ws_code);
   }

// 입사승인시 해당일반회원 정보 입력 후 승인완료
   @Override
   public boolean empApplyUpdate(EmpDTO dto) {
      log.info("입사승인");
      boolean isc = dao.empApplyUpdate(dto);
      EmpUserDTO Edto = new EmpUserDTO();
      Edto.setUser_id(dto.getUser_id());
      Edto.setWs_code(dto.getWs_code());
      Edto = dao.empSelect(Edto);
      return isc;
   }
////   모든직원 조회
//   @Override
//   public List<EmpUserDTO> empAllSelect() {
//      log.info("모든직원 조회");
//      return dao.empAllSelect();
//   }
////   직원 조회
//@Override
//public EmpDTO empSelect(String user_id) {
//   log.info("직원조회");
//   return dao.empSelect(user_id);
//}

   
//   직원정보 수정
   @Override
   public boolean empUpdate(String emp_code) {
      log.info("직원정보 수정");
      return dao.empUpdate(emp_code);
   }
 //직원상세정보 조회
 	@Override
 	public EmpUserDTO empDetailSelect(EmpUserDTO dto) {
 		log.info("ServiceImpl 직원 상세정보 조회");
 		return dao.empDetailSelect(dto);
 	}  

//   사업장 조회
@Override
public List<WorkSpaceDTO> wsSearch() {
   return dao.wsSearch();
}
//@Override
//public boolean empApplyUpdate(EmpDTO dto) {
//	 log.info("empApplyUpdate 화면 {}",dto);
//	return false;
//}
@Override
public List<EmpUserDTO> empAllSelect(String ws_code) {
	 log.info("empAllSelect 화면{}",ws_code);
	return dao.empAllSelect(ws_code);
}
@Override
public EmpUserDTO empSelect(EmpUserDTO EUDto) {
	 log.info("empSelect 화면{}",EUDto);
	return dao.empSelect(EUDto);
}

//	입사거절
@Override
public boolean empApplyReject(String emp_code) {
	log.info("Service 입사거절");
	return dao.empApplyReject(emp_code);
}


@Override
public List<String> selectWsMember(EmpDTO dto) {
	log.info("ServiceImpl_Emp selectWsMember{}",dto);
	return dao.selectWsMember(dto);

}

   
   

   

}