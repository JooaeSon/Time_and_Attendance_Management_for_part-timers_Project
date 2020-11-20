<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC</title>
<style type="text/css">
body { background: url(img/bg-login.jpg) !important; }
#container {
	width: 500px;
	margin: 100px auto;
	border: 1px solid #ccc;
	padding: 10px;
	text-align: center;
}
.login-box{
	margin: 10px auto;
	padding : 10px;
}
#head {
	text-align: center;
	font-size: 20px;
	margin-top:80px;
}
</style>
</head>
<body>
<%@include file="/WEB-INF/views/topLogin.jsp" %>
<div class="login-box" style="width:600px;">
	<div id="head">
	<h1>${msg}</h1>
	<br>
	<h1>관리자에게 문의해 주세요.</h1>
	</div>
	<div id="container">
	<a href="./logout.do">로그인창으로 돌아가기</a>
	</div>
</div>
</body>
</html>