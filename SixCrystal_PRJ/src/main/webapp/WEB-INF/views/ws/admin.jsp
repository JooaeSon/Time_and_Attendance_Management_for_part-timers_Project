<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자가 사업장 삭제</title>
</head>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
function y(ws_code_y){
// 	alert(ws_code_y);
	$("#admin-form").attr("action","./wsDelReqYUpdate.do");
	$("#admin-input").val(ws_code_y);
	swal("삭제요청 승인 완료");
	$("#admin-form").submit();
}

function n(ws_code_n){
// 	alert(ws_code_n);
	$("#admin-form").attr("action","./wsDelReqNUpdate.do");
	$("#admin-input").val(ws_code_n);
	swal("삭제요청 승인 거절");
	$("#admin-form").submit();
}
</script>
<body>
<%@include file="/WEB-INF/views/topNavA.jsp"%>
<form method="get" id="admin-form">
	<input type="hidden" id="admin-input" name="ws_code">
</form>
<div class="container-fluid-full">
	<div class="row-fluid">
		<%@include file="/WEB-INF/views/sideBarA.jsp" %>
		<div id="content" class="span10" style="min-height: 844px; margin-left: 20px;"> 
			<div id="tableSpace">
				<table class="table table-bordered">
					<tr>
						<th>사업장 코드</th>
						<th>소유주 아이디</th>
						<th>처리</th>
					</tr>
					<c:forEach items="${lists}" varStatus="vs" var="wsdto">
					<tr>
						<td>${vs.count}${wsdto.ws_code}</td>
						<td>${vs.count}${wsdto.user_id}</td>
						<td>
							<input type="button" value="승인" onclick="y('${wsdto.ws_code}')">
							<input type="button" value="거절" onclick="n('${wsdto.ws_code}')">
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>