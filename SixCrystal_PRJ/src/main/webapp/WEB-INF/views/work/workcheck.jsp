<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>근태 체크 페이지 입니다.</title>
<link type="text/css" rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="./css/bootstrap-theme.css">
<link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-sweetalert/1.0.1/sweetalert.css">
<style type="text/css">
	#timebox{
		text-align:center;
	
	}

</style>

</head>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-sweetalert/1.0.1/sweetalert.js"></script>

<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
	<h1>출퇴근 하기</h1>

<!-- 	<form action="./commute.do" method="post" onsubmit="commute()"> -->
<!--     	<input class="btn btn-success" type="submit" value="출근"> -->
<!--     </form> -->

<!-- 	<form action="./leaveWork.do" method="post" onsubmit="leaveWork()"> -->
<!--     	<input class="btn btn-info" type="submit" value="퇴근"> -->
<!--     </form> -->
	 <br>
<div id="timebox">
	${userType}
	${empType}
	<input onclick="commute()" class="btn btn-success btn-lg" name="commute" type="button" value="출근">
	<div id="commute">${startDATE}</div>
	<br>

	<input onclick="leaveWork()" class="btn btn-info btn-lg" name="leaveWork" type="button" value="퇴근">
	<div id="leaveWork">${endDATE}</div>
	
		<form action="#" method="get" id="checkRoutine"></form>
	

	</div>
	
	
	<script>
		function commute(){
			var date =new Date();
			var commutedate = getFormatDate(date);
			//document.getElementById("commute").innerHTML=date.toLocaleTimeString();
			var time=date.toLocaleTimeString();
			var name=document.getElementsByName("commute")[0].value;
			
			$.ajax({
				url:"./dayCheck.do",
				data:"YYYYMMDDtime="+commutedate+"&name="+name,
				type:"get",
				success:function(msg){
					//swal("출근되었습니다.");
					$("#commute").html(msg.commute); //msg.commute
					swal(msg.message);
					$("#commute").html(msg.blank);//경고 메시지가 있을 경우
				}, error:function(){	
					swal("일정표에 일정이 없습니다.");
				}
		
			});
		}
	
		function leaveWork(){
			var date =new Date();
			var leaveWorkdate = getFormatDate(date);
			//document.getElementById("leaveWork").innerHTML=date.toLocaleTimeString();
			var time=date.toLocaleTimeString();
			var name=document.getElementsByName("leaveWork")[0].value;
			
			$.ajax({
				url:"./dayCheck.do",
				data:"YYYYMMDDtime="+leaveWorkdate+"&name="+name,
				type:"get",
				success:function(msg){
					//alert("퇴근되었습니다.");
					$("#leaveWork").html(msg.leaveWork);
					swal(msg.message);//경고 메시지가 있을 경우
					$("#leaveWork").html(msg.blank);
				}, error:function(){	
					swal("출근 처리부터 해주세요");
				}
		
			});
		}
	
		function getFormatDate(date){
		    var year = date.getFullYear();              //yyyy
		    var month = (1 + date.getMonth());          //M
		    month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
		    var day = date.getDate();                   //d
		    day = day >= 10 ? day : '0' + day;			//day 두자리로 저장
		    var hours=date.getHours();					//h
		    hours = hours >= 10 ? hours : '0'+hours;	//hours 두자리로 저장
		    var minutes=date.getMinutes();				//m
		    minutes = minutes >= 10 ? minutes : '0'+minutes; //minutes 두자리로 저장
		    var seconds=date.getSeconds();				//s
		    seconds = seconds >= 10 ? seconds : '0'+seconds; //seconds 두자리로 저장
		    return  year + '-' + month + '-' + day+'T'+hours+':'+minutes+':'+seconds;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
		}
	
	</script>
	
	
	
	
	   </div>
      </div>
   </div>

	<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>