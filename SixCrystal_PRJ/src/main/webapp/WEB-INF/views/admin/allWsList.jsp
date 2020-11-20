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
		url:"./wsDetailForm.do",
		type:"post",
		data:"ws_code="+val,
		success: function(msg){
			  html = "<div class='form-group'>";
			  html += "<input type='hidden'  name='ws_code' value='"+val+"'>";
	          html += "<label><strong>소유자</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.user_id+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>사업장 코드</strong></label>";
	          html += "<p class='form-control'>"+val+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>사업장 이름</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.ws_name+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>사업장 주소</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.ws_loc+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>대표 전화번호</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.ws_num+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>대표 이메일</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.ws_email+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>WI-FI 이름</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.ws_ssid+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>WI-FI IP</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.ws_ip+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>사업장 규모</strong></label>";
	          html += "<p class='form-control'>"+msg.dto.ws_vol+"</p>";
	          html += "</div>";
	          html += "<div class='form-group'>";
	          html += "<label><strong>사업장 삭제여부</strong></label>";
	          if(msg.dto.ws_delflag == "N"){
	        	  html += "<p class='form-control'>사용중</p>";
	          }else if(msg.dto.ws_delflag == "D"){
	        	  html += "<p class='form-control'>삭제 완료</p>";
	          }else if(msg.dto.ws_delflag == "R"){
	        	  html += "<p class='form-control'>요청 대기중</p>";
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
			<div>
				<input class="input-large" id="myInput" type="text" placeholder="검색..">
			</div>
		  	<br>
		  	<div id="tableSpace">
				<table class="table table-bordered" id="allTable">
				<thead>
				      <tr>
				        <th>연번</th><th>사업장 이름</th>
				        <th>소유자</th><th>전화번호</th>
				        <th>사업장 코드</th><th>삭제 여부</th>
				      </tr>
				    </thead>
				    <tbody id="myTable">
				    	<c:forEach var="dto" items="${lists}" varStatus="vs" >
				    		<tr>
				    			<td>${fn:length(lists)-vs.index}</td>
				    			<td><a onclick="seeDetail('${dto.ws_code}');">${dto.ws_name}</a></td>
				    			<td>${dto.user_id}</td><td>${dto.ws_num}</td>
				    			<td>${dto.ws_code}</td>
				    			<td>
				    				<c:if test="${dto.ws_delflag eq 'N'}">
										사용중   				
				    				</c:if>
				    				<c:if test="${dto.ws_delflag eq 'R'}">
										삭제 요청 대기중    				
				    				</c:if>
				    				<c:if test="${dto.ws_delflag eq 'D'}">
										삭제 완료    				
				    				</c:if>
				    			</td>
				    		</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
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
		          		<h4 class="modal-title">사업장 상세정보</h4>
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