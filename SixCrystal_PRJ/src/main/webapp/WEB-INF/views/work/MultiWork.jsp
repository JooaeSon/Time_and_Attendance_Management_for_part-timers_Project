<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

<title>직원들 조회 페이지 입니다.</title>

</head>
<script type="text/javascript" src="./js/multiWork.js"></script>
<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 

<br>
	<br>
	<br>
<h1>직원들 조회 페이지 입니다.</h1>
  <p>당신의 하위 직원들의 근무 정보를 조회 할 수 있습니다.</p>
<br>
	<table  class="table table-striped">
		<thead style="background-color: #392f31; color:white;">
			<tr>
				<th><input type="checkbox" onclick="checkAll(this.checked)"> </th>
				<th>연번</th>
				<th>날짜</th>
				<th>아이디</th>
				<th>직원등급</th>
				<th>출근시각</th>
				<th>출근상태</th>
				<th>퇴근시각</th>
				<th>퇴근상태</th>
			</tr>
		</thead>
			<jsp:useBean id="format" class="com.min.sc.work.bean.InputList" scope="page"/>
				<jsp:setProperty property="lists" name="format" value="${rdlists}"/>
				<jsp:getProperty property="listForm" name="format"/>
			
	</table>
	<br>
	<h3>*출근시간과 퇴근시간을 클릭하면 근무시간 기록을 수정 할 수 있습니다.</h3>
	<br>
	<br>
	
	<div class="modal fade" id="modify" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" style="text-align:left;">일정 수정</h4>
          
        </div>
        <div class="modal-body">
        	<!-- ajax를 통해서 수정하고 넘길 데이터를 표출해줌 -->
          <form action="#" method="post" id="frmModify"></form>
        </div>
       
      </div>
      
   
  </div>
  
</div>

      </div>
      </div>
   </div>

<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>