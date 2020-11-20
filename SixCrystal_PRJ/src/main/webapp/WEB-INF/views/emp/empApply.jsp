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
<title>입사 승인</title>
</head>
<script type="text/javascript">
$('#exampleModal').on('show.bs.modal', function (event) {
     var button = $(event.relatedTarget) // Button that triggered the modal
     var recipient = button.data('whatever') // Extract info from data-* attributes
     // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
     // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
     var modal = $(this)
     modal.find('.modal-title').text('New message to ' + recipient)
     modal.find('.modal-body input').val(recipient)
   })
   
function fixed1(val){
//    alert("동작됬니?");
   location.href="./empApplyYN.do"+user_name;
}

function reject1(emp_code){
	location.href="./empReject.do?emp_code="+emp_code;
}
</script>

<body>

<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
<c:forEach items="${EDto}" var="dto" varStatus="vs">
<form action="./empApplyYN.do" method="get">
<div>
   <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo" name="user_name">${dto.user_name}</button>
   <input type="submit" class="btn btn-primary" id="fixed" value="승인" onclick="fixed1('${dto.user_name}')">
   <input type="button" class="btn btn-primary" id="reject" value="거절" onclick="reject1('${dto.emp_code}')">
</div>

         
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">직원 정보</h4>
      </div>
      <div class="modal-body">
          <div class="form-group">
            <label for="recipient-name" class="control-label">이름</label>
            <input type="text" class="form-control" id="user_name" name="user_name" value="${dto.user_name}" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">이메일</label>
            <input type="text" class="form-control" id="user_email" name="user_email" value="${dto.user_email}" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">휴대전화</label>
            <input type="text" class="form-control" id="user_phone" name="user_phone" value="${dto.user_phone}" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">주소</label>
            <input type="text" class="form-control" id="user_address" name="user_address" value="${dto.user_address}" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">성별</label>
            <input type="text" class="form-control" id="user_gender" name="user_gender" value="${dto.user_gender}" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">생년월일</label>
            <input type="text" class="form-control" id="user_birth" name="user_birth" value="${dto.user_birth}" readonly="readonly">
          </div>
          <input type="hidden" class="form-control" id="emp_code" name="emp_code" value="${dto.emp_code}">
          <input type="hidden" class="form-control" id="ws_code" name="ws_code" value="${dto.ws_code}" >
          <input type="hidden" class="form-control" id="user_id" name="user_id" value="${dto.user_id}">
          <input type="hidden" class="form-control" id="emp_confirm" name="emp_confirm" value="${dto.emp_confirm}">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
      </div>
    </div>
  </div>
</div>   
</form>
</c:forEach>
</div>
      </div>
   </div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>