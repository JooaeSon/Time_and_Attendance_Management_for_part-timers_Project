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
 
<title>개인 근태 조회 페이지 입니다.</title>
</head>
<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
	<br>
	<br>
	<br>
	<h1>개인 근태 조회 페이지 입니다.</h1>
	
	<p>당신의 근무 기록을 조회 할 수 있습니다.</p>
	<table  class="table table-striped">
		<thead style="background-color: #392f31; color: white;">
			<tr>
				<th>날짜</th>
				<th>출근시각</th>
				<th>출근상태</th>
				<th>퇴근시각</th>
				<th>퇴근상태</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="wdto" items="${WorkPersonalList}" varStatus="vs">
				<tr>
					<td>${wdto.day}</td>
					<td>${wdto.startDay}</td>
					<td>${wdto.startState}</td>
					<td>${wdto.endDay}</td>
					<td>${wdto.endState}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	

	<br>
	<h3>*퇴사한 사업장의 기록은 열람이 불가능합니다.</h3>
	<br>
	<br>
     </div>
      </div>
   </div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>