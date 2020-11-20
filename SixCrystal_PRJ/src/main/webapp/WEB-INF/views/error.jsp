<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC</title>
<style type="text/css">
#container{
		width:500px; 
		margin: 100px auto; 
		border: 1px solid #ccc; 
		padding:10px;
		text-align: center;
	}
</style>
</head>
<body>
<%@include file="/WEB-INF/views/topMenu.jsp" %>
<div id="container">
<h2>${msg}</h2>
<h5>관리자에게 문의해 주세요.</h5>
<a href="./usermain.do">메인으로 돌아가기</a>
</div>
</body>
</html>