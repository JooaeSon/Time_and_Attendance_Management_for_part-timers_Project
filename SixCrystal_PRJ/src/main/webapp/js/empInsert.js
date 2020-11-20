
function empinsert(){
//       alert("등록");
   var ecode = document.getElementById("emp_code").value;
   var wcode = document.getElementById("ws_code").value;
   var sHiredate = document.getElementById("emp_hiredate").value;
   var eHiredate = document.getElementById("emp_hiredate_end").value;
   var position = document.getElementById("emp_position").value;
   var worktime = document.getElementById("emp_worktime_time").value;
   var holiday = document.getElementById("emp_holiday").value;
   var salary = document.getElementById("emp_salary").value;
   var dSalary = document.getElementById("emp_salary_day").value;
   var apply = document.getElementById("emp_apply").value;
   var confirm = document.getElementById("emp_confirm").value;
   var rank = document.getElementById("emp_rank").value;
   
   if(ecode==""){
      alert("직원코드를 입력해 주세요.");
      ecode.focus();
      return false;
   }else if(wcode==""){
      alert("사업장 코드를 입력해 주세요.");
      wcode.focus();
      return false;
   }else if (sHiredate=="") {
      alert("근무계약 시작일을 입력해 주세요.");
      sHiredate.focus();
      return false;
   }else if(eHiredate==""){
      alert("근무계약 종료일을 입력해 주세요.");
      eHiredate.focus();
      return false;
   }else if(position==""){
      alert("업무내용을 입력해 주세요.");
      position.focus();
      return false;
   }else if(worktime==""){
      alert("소정근로시간을 입력해 주세요.");
      worktime.focus();
      return false;
   }else if(holiday==""){
      alert("주휴일을 입력해 주세요.");
      holiday.focus();
      return false;
   }else if(salary==""){
      alert("임금을 입력해 주세요.");
      salary.focus();
      return false;
   }else if(dSalary==""){
      alert("임금지급일을 입력해 주세요.");
      dSalary.focus();
      return false;
   }else if(apply==""){
      alert("입사일을 입력해 주세요.");
      apply.focus();
      return false;
   }else if(confirm==""){
      alert("입사승인여부를 입력해 주세요.");
      confirm.focus();
      return false;
   }else if(rank==""){
      alert("직급을 입력해 주세요.");
      rank.focus();
      return false;
   }else{
//      document.form.submit();
       var hiredate = document.getElementById("emp_hiredate").value;
       var hiredate_end = document.getElementById("emp_hiredate_end").value;
       var position = document.getElementById("emp_position").value;
       var worktime_time = document.getElementById("emp_worktime_time").value;
       var holiday = document.getElementById("emp_holiday").value;
       var salary = document.getElementById("emp_salary").value;
       var salary_day = document.getElementById("emp_salary_day").value;
       var apply = document.getElementById("emp_apply").value;
       var rank = document.getElementById("emp_rank").value;
       var emp_code = document.getElementById("emp_code").value;
       var user_name= document.getElementById("user_name").value;
       var user_id = document.getElementById("user_id").value;
      var ws_code = document.getElementById("ws_code").value;
      
        location.href="./empInsertOK.do?emp_hiredate="+hiredate+"&emp_hiredate_end="+hiredate_end+
              "&emp_position="+position+"&emp_worktime_time="+worktime_time+"&emp_holiday="+holiday+"&emp_salary="+salary+
              "&emp_salary_day="+salary_day+"&emp_apply="+apply+"&emp_rank="+rank+
              "&emp_code="+emp_code+"&user_name="+user_name+"&user_id="+user_id+"&ws_code="+ws_code;
        alert("정상적으로 등록되었습니다.");
//        location.reload(); 
   }
   }
