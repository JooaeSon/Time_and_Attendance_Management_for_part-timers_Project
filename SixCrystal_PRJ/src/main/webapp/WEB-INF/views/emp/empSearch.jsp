<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
     $("#myInput").on("keyup", function() {
       var value = $(this).val().toLowerCase();
       $("#empsearch tr").filter(function() {
         $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
       });
     });
   });
   
// function empDetail(){
// // 	alert("동작");
// 	var emp_code = document.getElementById("emp_code").value;
// // 	alert(emp_code);
// 	var ws_code = document.getElementById("ws_code").value;
// // 	alert(ws_code);
// // 	var user_id = document.getElementById("user_id").value;
// // 	alert(user_id);
// 	location.href="./empDetail.do?emp_code="+emp_code+"?ws_code="+ws_code;
// }  
</script>
<title>직원 검색</title>
</head>
<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
          <div class="container">
      <input class="form-control" id="myInput" type="text" placeholder="직원명을 입력해주세요.">
      <form method="post" id="workSearch" name="workSearch" action="./empDetail.do">
      <table class="table table-bordered table-striped">
         <thead>
            <tr>
               <th>직원코드</th>
               <th>이름</th>
               <th>번호</th>
               <th>직급</th>
               <th>입사일</th>
               <th>사업장 코드</th>
            </tr>
         </thead>
         <tbody id="empsearch">
            <c:forEach var="dto" items="${lists}">
            <tr>
         <td><button type="submit" name="emp_code" id="emp_code" data-toggle="modal" data-target="#myModal" value="${dto.emp_code}">${dto.emp_code}</button></td>
         <td>${dto.user_name}</td>
         <td>${dto.user_phone}</td>
         <td>${dto.emp_rank}</td>
         <td>${dto.emp_apply}</td>
         <td><input type="text" name="ws_code" id="ws_code" value="${dto.ws_code}" readonly="readonly"></td>
<%--          <td><input type="hidden" name="user_id" id="user_id" value="${user_id}"></td> --%>
      </tr>
      </c:forEach>
         </tbody>
      </table>
         </form>
   </div>
         </div>
      </div>
   </div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>