<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사업장 선택</title>
<style type="text/css">
.sidebar-nav{
		display: none;
	}
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
//	[ : %5B, ] : %5D
function submit(){
// 	alert("submit()");
	var val=$("select[name=selected-val]").val();
// 	alert(val);

	if(val.indexOf("[")!=-1){ // 무조건 앞에붙음
// 		alert(val.indexOf("["));
		val=val.substring(val.indexOf("[")+1, val.length);
// 		alert(val);
	}
	
	if(val.indexOf("]")!=-1) { // 무조건 뒤에 붙음
// 		alert(val.indexOf("]"));
		val=val.substring(0, val.indexOf("]"));
// 		alert(val);
	}
	
// 	$("select[name=ws_code]").val(val);
	alert(val);
	
	$("#hidden-val").val(val.trim());
	$("#real-val").submit();
	
	
}
</script>
<body>

<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
	<div class="row-fluid">
		<%@include file="/WEB-INF/views/sideBar.jsp" %>
		<div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
			<select name="selected-val">
				<c:forTokens var="i" items="${ws_code_lists}" delims=",">
					<option value="${i}">
						${i}
					</option>
				</c:forTokens>	
			</select>
			<form action="./select.do" method="get" class="box" id="real-val">
				<input type="hidden" id="hidden-val" name="ws_code">
			</form>
			<input class="btn btn-basic" type="button" value="사업장 선택" onclick="submit()">
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>				
</body>

</html>