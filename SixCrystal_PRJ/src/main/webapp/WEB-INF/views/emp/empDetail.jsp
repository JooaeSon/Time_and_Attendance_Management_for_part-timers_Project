<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>직원 상세보기 화면</title>
</head>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
   <h1 style="text-align: center">직원 상세보기</h1>
 
   
   <form method="get" id="empDetail" name="empDetail" action="./empModify.do" >
      <div class="form-group">
         <label for="user_id">직원아이디</label>
         <input type="text" class="form-control" name="user_id" id="user_id" value='${lists.user_id}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="user_name">직원이름</label>
         <input type="text" class="form-control" name="user_name" id="user_name" value='${lists.user_name}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="user_address">직원주소</label>
         <input type="text" class="form-control" name="user_address" id="user_address" value='${lists.user_address}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="user_birth">직원생일</label>
         <input type="text" class="form-control" name="user_birth" id="user_birth" value='${lists.user_birth}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_email">직원이메일</label>
         <input type="text" class="form-control" name="emp_email" id="emp_email" value='${lists.user_email}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="user_gender">직원성별</label>
         <input type="text" class="form-control" name="user_gender" id="user_gender" value='${lists.user_gender}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="user_phone">직원전화번호</label>
         <input type="text" class="form-control" name="user_phone" id="user_phone" value='${lists.user_phone}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_code">직원코드</label>
         <input type="text" class="form-control" name="emp_code" id="emp_code" value='${lists.emp_code}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_apply">직원 입사일</label>
         <input type="text" class="form-control" name="emp_apply" id="emp_apply" value='${lists.emp_apply}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="ws_code">사업장 코드</label>
         <input type="text" class="form-control" name="ws_code" id="ws_code" value='${lists.ws_code}' readonly="readonly">
      </div>
      <div class="form-group">
         <label for="emp_hiredate">근무계약 시작일</label>
         <input type="date" class="form-control" id="emp_hiredate" name="emp_hiredate" value='${lists.emp_hiredate}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_hiredate_end">근무계약 종료일</label>
         <input type="date" class="form-control" id="emp_hiredate_end" name="emp_hiredate_end" value='${lists.emp_hiredate_end}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_position">업무내용</label>
         <input type="text" class="form-control" name="emp_position" id="emp_position" value='${lists.emp_position}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_worktime_time">소정근로 시간</label>
         <input type="text" class="form-control" id="emp_worktime_time" name="emp_worktime_time" value='${lists.emp_worktime_time}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_holiday">주휴일</label>
         <input type="text" class="form-control" id="emp_holiday" name="emp_holiday" value='${lists.emp_holiday}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_salary">임금</label>
         <input type="text" class="form-control" id="emp_salary" name="emp_salary" value='${lists.emp_salary}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_salary_day">임금지급일(매주/매월)</label>
         <input type="text" id="emp_salary_day" class="form-control" name="emp_salary_day" value='${lists.emp_salary_day}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_apply">입사일</label>
         <input type="date" class="form-control" id="emp_apply" name="emp_apply" value='${lists.emp_apply}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_confirm">입사승인여부</label>
         <input type="text" class="form-control" id="emp_confirm" name="emp_confirm" value='${lists.emp_confirm}' readonly="readonly">
      </div>
      
      <div class="form-group">
      <label for="emp_rank">직급</label>
      <input type="text" class="form-control" id="emp_rank" name="emp_rank" value='${lists.emp_rank}' readonly="readonly">
      </div>   
          
      <div id="botton">
         <input id="empOK" type="button" class="btn btn-primary" value="확인" onclick="location.href='./empAllSelect.do'">
         <input id="empModify" type="submit" class="btn btn-primary" value="수정">
      </div>
   </form>
         </div>
      </div>
   </div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>