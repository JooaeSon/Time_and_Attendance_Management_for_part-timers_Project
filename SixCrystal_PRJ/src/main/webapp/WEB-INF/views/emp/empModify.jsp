<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<title>직원정보 수정하기</title>
</head>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
   var form = document.getElementsByTagName('form')[0];
//    form.submit();
   window.onload=function(){
      var work = document.getElementById("emp_worktime_time");
//       alert(work);S
      for (var i = 0; i < work.options.length; i++) {
         if(work.options[i].value==${lists.emp_worktime_time}){
            
            work.options[i].selected=true;
         }
      }
      var hol = document.getElementById("emp_holiday");
      for (var i = 0; i < hol.length; i++) {
         if(hol.options[i].value==${lists.emp_holiday}){
            hol.options[i].selected=true;
         }
      }
      
      var salaryDay = document.getElementById("emp_salary_day");
      
      var chksalDay ="${lists.emp_salary_day}";
      var salDay = chksalDay.split(",");
      for(var i = 0; i < salaryDay.length;i++){
         if(salaryDay[i].value==salDay[0]) {
            salaryDay[i].selected=true;
            var salaryDayS = document.getElementById("emp_salary_days");
            for (var j = 0; j < salaryDayS.length; j++) {
               if(salaryDayS[j].value==salDay[0]){
                  salaryDayS[j].selected=true;
            }
         }
      }
   }
      var empRank = document.getElementById("emp_rank");
      for (var i = 0; i < empRank.length; i++) {
		if(empRank.option[i].value==${lists.emp_rank}){
			empRank.option[i].selected=true;
		}
	}

//       var salary = $("#lists.emp_salary_day").val();
//       var salarys = $("#lists.emp_salary_days").val();
}
   function change(){
      var week = ["월","화","수","목","금","토","일"];
      var month =["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"];
   
      var selectItem =$("#emp_salary_day").val();
//       alert(selectItem);
      var changeItem;
      
      if(selectItem == "week"){
         changeItem = week;
      }else if(selectItem =="month"){
         changeItem=month;
      }
      $('#emp_salary_days').empty();
//       $('#emp_salary_days').append($("<option>--선택 --</option>"));
      for(var count = 0; count < changeItem.length; count++){
         var option = $("<option value='"+changeItem[count]+"'>"+changeItem[count]+"</option>");
         $('#emp_salary_days').append(option);
      }
   }
   
function empMo(){
   var emp_mocode = document.createElement('input');
   emp_mocode.type='hidden';
   emp_mocode.setAttribute('name','emp_code');
   emp_mocode.value='${lists.emp_code}';
   var form = document.getElementsByTagName('form')[0];
   form.appendChild(emp_mocode);
   form.submit();
}   
</script>




<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
    <h1 style="text-align: center">직원 수정하기</h1>
   <form method="post" id="empIn" name="empIn" action="./empModifyOK.do">
      <div class="form-group">
         <label for="user_id">직원아이디</label>
         <input type="text" class="form-control" id="user_id" value='${lists.user_id}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_code">직원코드</label>
         <input type="text" class="form-control" id="emp_code" value='${lists.emp_code}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_apply">직원 입사일</label>
         <input type="text" class="form-control" value="${lists.emp_apply}" readonly="readonly" >
      </div>
      
      <div class="form-group">
         <label for="ws_code">사업장 코드</label>
         <input type="text" class="form-control" id="ws_code" value='${lists.ws_code}' readonly="readonly">
      </div>
      
      <div class="form-group">
         <label for="emp_hiredate">근무계약 시작일</label>
         <input type="date" class="form-control" id="emp_hiredate" name="emp_hiredate" value='${lists.emp_hiredate}'>
      </div>
      
      <div class="form-group">
         <label for="emp_hiredate_end">근무계약 종료일</label>
         <input type="date" class="form-control" id="emp_hiredate_end" name="emp_hiredate_end" value='${lists.emp_hiredate_end}'>
      </div>
      
      <div class="form-group">
         <label for="emp_position">업무내용</label>
         <input type="text" class="form-control" name="emp_position" id="emp_position" value='${lists.emp_position}'>
      </div>
      
      <div class="form-group">
         <label for="emp_worktime_time">소정근로 시간</label>
         <select name="emp_worktime_time" id="emp_worktime_time" class="form-control">
         <option value="" disabled selected hidden>--선택--</option>
         <option value="30">30분</option>
         <option value="60">1시간</option>
         <option value="90">1시간30분</option>
         <option value="120">2시간</option>
         <option value="150">2시간30분</option>
         <option value="180">3시간</option>
         <option value="210">3시간30분</option>
         <option value="240">4시간</option>
         <option value="270">4시간30분</option>
         <option value="300">5시간</option>
         <option value="330">5시간30분</option>
         <option value="360">6시간</option>
         <option value="390">6시간30분</option>
         <option value="420">7시간</option>
         <option value="450">7시간30분</option>
         <option value="480">8시간</option>
         <option value="510">8시간30분</option>
         <option value="540">9시간</option>
         <option value="570">9시간30분</option>
         <option value="600">10시간</option>
         <option value="630">10시간30분</option>
         <option value="660">11시간</option>
         <option value="690">11시간30분</option>
         <option value="720">12시간</option>
      </select>
      </div>
      
      <div class="form-group">
         <label for="emp_holiday">주휴일</label>
         <select name="emp_holiday" id="emp_holiday" class="form-control">
         <option value="" disabled selected hidden>--선택--</option>
         <option value="1">1</option>
         <option value="2">2</option>
         <option value="3">3</option>
         <option value="4">4</option>
         <option value="5">5</option>
         <option value="6">6</option>
         <option value="7">7</option>
      </select>
      </div>
      
      <div class="form-group">
         <label for="emp_salary">임금</label>
         <input type="text" class="form-control" id="emp_salary" name="emp_salary" value='${lists.emp_salary}'>
      </div>
      
      <div class="form-group">
         <label for="emp_salary_day">임금지급일(매주/매월)</label>
         <select name="emp_salary_day" id="emp_salary_day" class="form-control" onchange="change()">
         <option value="week">주급</option>
         <option value="month">월급</option>
      </select>
      <select  id="emp_salary_days" name="emp_salary_day" class="form-control">
         
      </select>
      </div>
      
      <div class="form-group">
         <label for="emp_confirm">입사승인여부</label>
         <input type="text" class="form-control" id="emp_confirm" value='${lists.emp_confirm}' readonly="readonly">
      </div>
      
      <div class="form-group">
      <label for="emp_rank">직급</label>
      <select name="emp_rank" id="emp_rank" class="form-control">
<!--          <option value="" disabled selected hidden>--선택--</option> -->
         <option value="man">매니저</option>
         <option value="emp">직원</option>
      </select>
      </div>   
          
      <div id="botton">
         <input id="empModifyOK" type="button" class="btn btn-primary" value="확인" onclick="empMo()">
         <input id="empModifyCancel" type="button" class="btn btn-primary" value="취소" onclick="javascript:history.back(-1)">
      </div>
   </form>
         </div>
      </div>
   </div>

<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>