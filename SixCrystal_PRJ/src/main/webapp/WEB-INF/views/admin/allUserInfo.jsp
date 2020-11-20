<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC Admin</title>
<style type="text/css">
#bton{
	text-align: center;
}
#search{
	width:100%;
}
#myInput{
	width: 30%;
	margin: 15px auto;
	float: right;
}
#selectType{
	width: 110px;
	margin: 15px auto;
	float: left;
}
label{
	font-size: 15px;
}
#tableSpace{
	width:100%;
	height: 350px;
	white-space:nowrap;
	overflow-y: scroll;
}
</style>
</head>
<script type="text/javascript">

window.onload = function(){
    $("#selectType").change(function(){
       var sel = $("#selectType").val();
       $.ajax({
          url : "./listChange.do",
          type : "post",
          data : "sel="+sel,
          success : function(data){
             if(data == "basic"){
            	$('#employeeTable').hide();
                $('#employerTable').hide();
                $('#allTable').show();
             }else if(data == "employer"){
            	 $('#employeeTable').hide();
                 $('#employerTable').show();
                 $('#allTable').hide();
             }else{
            	 $('#employeeTable').show();
                 $('#employerTable').hide();
                 $('#allTable').hide();
             }
          },
          error : function(){
             swal("오류","잘못된 요청입니다.");
          }
       });
          
    });
}

//----------------회원정보 상세조회------------------
function seeDetail(val){
// 	alert(val);
	ajaxDetail(val);
	$("#detail").modal();
}
var ajaxDetail = function(val){
// 	alert("Ajax 작동 "+val);
	$.ajax({
		url:"./infoDetailForm.do",
		type:"post",
		data:"user_id="+val,
		success: function(msg){
			  html = "<div class='form-group'>";
			  html += "<input type='hidden'  name='user_id' value='"+val+"'>";
	          html += "<label><strong>아이디</strong></label>";
	          html += "<p class='form-control'>"+val+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>성명</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.user_name+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>이메일</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.user_email+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>휴대전화</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.user_phone+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>주소</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.user_address+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>성별</strong></label>";
	          if(msg.dto.user_gender == "F"){
	        	  html += "<p class='form-control'>여자</p>";
	          }else{
	        	  html += "<p class='form-control'>남자</p>";
	          }
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>생년월일</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.user_birth+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>마지막 로그인</strong></label>";
	          if(msg.dto.logindto.user_lastlogin == null){
	          	html += "<p class='form-control'>없음</p>";
	          }else{
	        	  html += "<p class='form-control'>"+msg.dto.logindto.user_lastlogin+"</p>";
	          }
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>회원 상태</strong></label>";
	          if(msg.dto.logindto.user_delflag == "U"){
	        	  html += "<p class='form-control'>사용중</p>";
	          }else{
	        	  html += "<p class='form-control'>탈퇴</p>";
	          }
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>탈퇴 날짜</strong></label>";
	          if(msg.dto.logindto.user_delflag == "U"){
	        	  html += "<p class='form-control'>없음</p>";
	          }else{
	        	  html += "<p class='form-control'>"+msg.dto.logindto.user_deldate+"</p>";
	          }
	          html += "</div>";
	          
	          html += "<div class='form-group' style='text-align:center;'>";
	          html += "<button type='button' class='btn btn-basic' data-dismiss='modal'>닫기</button>";
	          html +="</div>";
	        	  
	          $("#frmDetail").html(html);
		},
		error: function(){
			alert("잘못된 요청입니다.");
		}
	});
}

</script>
<body>
<%@include file="/WEB-INF/views/topNavA.jsp" %>
<div class="container-fluid-full">
	<div class="row-fluid">
		<%@include file="/WEB-INF/views/sideBarA.jsp" %>
		<div id="content" class="span10" style="min-height: 800px; margin-left: 20px;"> 
		<div id="search">
	      <select id="selectType" class="form-control input-xs">
	      	 <option value="all" selected="selected">전체</option>
	         <option value="employer">고용주</option>
	         <option value="employee">일반직원</option>
	      </select>
		<input class="input-large" id="myInput" type="text" placeholder="검색..">
		</div>
	  	<br>
	  	<div id="tableSpace">
			<table class="table table-bordered" id="allTable">
			<thead>
			      <tr>
			        <th>연번</th><th>아이디</th>
			        <th>이름</th><th>이메일</th>
			        <th>회원타입</th><th>회원상태</th>
			      </tr>
			    </thead>
			    <tbody id="myTable">
			    	<c:forEach var="mdto" items="${mAllLists}" varStatus="vs" >
			    		<tr>
			    			<td>${fn:length(mAllLists)-vs.index}</td>
			    			<td><a onclick="seeDetail('${mdto.user_id}');">${mdto.user_id}</a></td>
			    			<td>${mdto.user_name}</td><td>${mdto.user_email}</td>
			    			<td>
			    				<c:if test="${mdto.user_type eq 'employer'}">
									고용주    				
			    				</c:if>
			    				<c:if test="${mdto.user_type eq 'employee'}">
									일반직원    				
			    				</c:if>
			    			</td>
			    			<td>
			    				<c:if test="${mdto.logindto.user_delflag eq 'U'}">
									사용중    				
			    				</c:if>
			    				<c:if test="${mdto.logindto.user_delflag eq 'T'}">
									탈퇴    				
			    				</c:if>
			    			</td>
			    		</tr>
			    	</c:forEach>
			    </tbody>
			</table>
			<table class="table table-bordered" id="employerTable" style="display:none;">
			<thead>
			      <tr>
			        <th>연번</th><th>아이디</th>
			        <th>이름</th><th>이메일</th>
			        <th>회원타입</th><th>회원상태</th>
			      </tr>
			    </thead>
			    <tbody id="myTable">
			    	<c:forEach var="mdto" items="${mListsER}" varStatus="vs" >
			    		<tr>
			    			<td>${fn:length(mListsER)-vs.index}</td>
			    			<td><a onclick="seeDetail('${mdto.user_id}');">${mdto.user_id}</a></td>
			    			<td>${mdto.user_name}</td><td>${mdto.user_email}</td>
			    			<td>
			    				<c:if test="${mdto.user_type eq 'employer'}">
									고용주    				
			    				</c:if>
			    				<c:if test="${mdto.user_type eq 'employee'}">
									일반직원    				
			    				</c:if>
			    			</td>
			    			<td>
			    				<c:if test="${mdto.logindto.user_delflag eq 'U'}">
									사용중    				
			    				</c:if>
			    				<c:if test="${mdto.logindto.user_delflag eq 'T'}">
									탈퇴    				
			    				</c:if>
			    			</td>
			    		</tr>
			    	</c:forEach>
			    </tbody>
			</table>
			<table class="table table-bordered" id="employeeTable" style="display:none;">
			<thead>
			      <tr>
			        <th>연번</th><th>아이디</th>
			        <th>이름</th><th>이메일</th>
			        <th>회원타입</th><th>회원상태</th>
			      </tr>
			    </thead>
			    <tbody id="myTable">
			    	<c:forEach var="mdto" items="${mListsEE}" varStatus="vs" >
			    		<tr>
			    			<td>${fn:length(mListsEE)-vs.index}</td>
			    			<td><a onclick="seeDetail('${mdto.user_id}');">${mdto.user_id}</a></td>
			    			<td>${mdto.user_name}</td><td>${mdto.user_email}</td>
			    			<td>
			    				<c:if test="${mdto.user_type eq 'employer'}">
									고용주    				
			    				</c:if>
			    				<c:if test="${mdto.user_type eq 'employee'}">
									일반직원    				
			    				</c:if>
			    			</td>
			    			<td>
			    				<c:if test="${mdto.logindto.user_delflag eq 'U'}">
									사용중    				
			    				</c:if>
			    				<c:if test="${mdto.logindto.user_delflag eq 'T'}">
									탈퇴    				
			    				</c:if>
			    			</td>
			    		</tr>
			    	</c:forEach>
			    </tbody>
			</table>
		</div>
		<br>
		<div id="bton">
			<input class="btn btn-basic active" type="button" onclick="history.back(-1)" value="돌아가기">
		</div>
	</div>
	</div>
</div>
	
		 <div class="modal fade" id="detail" role="dialog">
	    	<div class="modal-dialog">
		    	<!-- Modal content-->
		      	<div class="modal-content">
		        	<div class="modal-header">
		          		<button type="button" class="close" data-dismiss="modal">&times;</button>
		          		<h4 class="modal-title">회원 상세정보</h4>
		        	</div>
		        	<div class="modal-body">
		          		<form action="#" method="post" id="frmDetail"></form>
		        	</div>
		      	</div>
	    	</div>
	  </div>
<%@include file="/WEB-INF/views/footer.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
	  $("#myInput").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#myTable tr").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});
</script>
</body>
</html>