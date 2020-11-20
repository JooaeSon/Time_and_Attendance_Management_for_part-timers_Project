package com.min.sc.emp.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.sc.emp.dtos.EmpDTO;
import com.min.sc.emp.dtos.EmpUserDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;

@Repository
public class DaoImpl_Emp implements IDao_Emp {
   private Logger log = LoggerFactory.getLogger(this.getClass());
   private final String NS="com.min.sc.emp.";
   
   @Autowired
   private SqlSessionTemplate sqlSession;

//   일반회원이 입사신청시 자동으로 입력되는 사항
   @Override
   public boolean empApplyInsert(EmpDTO dto) {
      log.info("DaoImpl 입사신청시 자동으로 입력되는 사항");
      int n = sqlSession.insert(NS+"empApplyInsert", dto);
      return (n>0)?true:false;
   }
//   emp_confirm이 D인 회원 조회
   @Override
   public List<EmpUserDTO> empConfirmSelect(String ws_code) {
      log.info("Daoimpl emp_confirm이 D인 회원 조회");
      return sqlSession.selectList(NS+"empConfirmSelect",ws_code);
   }
//   입사승인시 해당일반회원 정보 입력 후 승인완료
   @Override
   public boolean empApplyUpdate(EmpDTO dto) {
      log.info("Daoimpl 승인시 일반회원 정보 입력");
      int n=sqlSession.insert(NS+"empApplyUpdate", dto);
      return (n>0)?true:false;
   }
////   모든직원 조회
//   @Override
//   public List<EmpUserDTO> empAllSelect() {
//      log.info("Daoimpl 모든직원 조회");
//      return sqlSession.selectList(NS+"empAllSelect");
//   }
////   직원 조회
//@Override
//public EmpDTO empSelect(String user_id) {
//   log.info("직원조회");
//   return sqlSession.selectOne(NS+"empSelect", user_id);
//}

//   직원정보 수정
   @Override
   public boolean empUpdate(String emp_code) {
      log.info("직원정보 수정");
      int n = sqlSession.update(NS+"empUpdate", emp_code);
      return (n>0)?true:false;
   }
 //직원 상세정보 조회
   @Override
   public EmpUserDTO empDetailSelect(EmpUserDTO dto) {
   	log.info("DaoImple 직원상세 정보 조회");
   	return sqlSession.selectOne(NS + "empDetailSelect", dto);
   }  


//   사업장 명으로 사업장 검색
@Override
public List<WorkSpaceDTO> wsSearch() {
   log.info("DaoImple_Ws wsSearch : {}\"");
   return sqlSession.selectList(NS+"wsSearch");
}
@Override
public List<EmpUserDTO> empAllSelect(String ws_code) {
	 log.info("DaoImple_Ws empAllSelect : {}\"",ws_code);
	return sqlSession.selectList(NS+"empAllSelect", ws_code);
}
@Override
public EmpUserDTO empSelect(EmpUserDTO EUDto) {
	 log.info("DaoImple_Ws empSelect : {}\"",EUDto);
	return sqlSession.selectOne(NS+"empSelect",EUDto);
}


//	입사거절
@Override
public boolean empApplyReject(String emp_code) {
	log.info("DaoImpl 입사거절");
	int n = sqlSession.update(NS+"empApplyReject",emp_code);
	return (n>0)?true:false;
}


//스케줄에서 사용할 사업장 내 직급별 지원조회
@Override
public List<String> selectWsMember(EmpDTO dto) {
	log.info("DaoImple_Ws selectWsMember : {}\"",dto);
	return sqlSession.selectList(NS+"selectWsMember", dto);
}


   

}