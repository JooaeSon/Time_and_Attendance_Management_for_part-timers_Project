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
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
   src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>사업장 검색</title>
<style type="text/css">
.sidebar-nav{
      display: none;
   }
</style>
</head>
<script type="text/javascript">

$(document).ready(function(){
     $("#myInput").on("keyup", function() {
       var value = $(this).val().toLowerCase();
       $("#workSpace tr").filter(function() {
         $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
       });
     });
   });   
   
function submit(){
//    alert("동작");
   var inputws_code = document.getElementById("inputws_code").value;
   var ws_code= document.getElementsByName("ws_code");
//    alert(inputws_code);
//    alert(ws_code.get(0));
//    var frm = document.getElementById("workSearch").value;
//    alert(frm);
//    frm.action="./workDetail.do";
   for (var i = 0; i < ws_code.length; i++) {
//       alert(ws_code[i].value);
   
   if(inputws_code == ws_code[i].value){
//       alert("동작");
      $.ajax({
         url:"./empAutoInsert.do",
         type:"get",
         data:"ws_code="+ws_code[i].value,
         dataType :"json",
         success:function(msg){
//             alert(msg.good);
            location.href="./workSearch.do"
         },
         error:function(){
            alert("오류!!다시시도해 주세요.");
         }
      });
   }
   }
   if(inputws_code !=ws_code.value){
      alert("사업장 코드가 일치하지 않습니다. 다시입력해 주세요.");
   }
}   
</script>

<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
     <div class="container">
   <h1 style="text-align: center;">사업장 검색</h1>
      <input class="form-control" id="myInput" type="text" placeholder="사업장명을 입력해주세요.">
<!--       <form method="post" id="workSearch" name="workSearch"> -->
      <table class="table table-bordered table-striped">
         <thead>
            <tr>
               <th>사업장 이름</th>
               <th>사업장 위치</th>
               <th>사업장 번호</th>
            </tr>
         </thead>
         <tbody id="workSpace">
            <c:forEach var="dto" items="${lists}">
            <tr>
               <td><button type="button" data-toggle="modal" data-target="#myModal" value="${dto.ws_name}">${dto.ws_name}</button></td>
                  <div class="modal fade" id="myModal" role="dialog">
                     <div class="modal-dialog">
                        <div class="modal-content">
                           <div class="modal-header">
         <button type="button" class="close" data-dismiss="modal">&times;</button>
         <h4 class="modal-title">사업장 코드를 입력해 주세요.</h4>
         </div>
         <div class="modal-body">
            <input type="text" name="inputws_code" id="inputws_code">
         </div>
         <div class="modal-footer">
            <input  type="button" class="btn btn-primary" value="입사신청" onclick="submit()">
            <button type='button' class='btn btn-primary' data-dismiss='modal'>닫기</button>
<!--             <input  type="button" class="btn btn-primary" value="취소"> -->
         </div>
         </div>
         </div>
         </div>
         
         <td>${dto.ws_loc}</td>
         <td>${dto.ws_num}</td>
      </tr>
         <input type="hidden" value="${dto.ws_code}"  id="ws_code" name="ws_code">
      </c:forEach>
         </tbody>
      </table>
<!--          </form> -->
   </div>
         </div>
      </div>
   </div>


   <%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>