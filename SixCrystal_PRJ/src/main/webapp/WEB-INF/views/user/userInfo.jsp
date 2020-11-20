<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC</title>
<style type="text/css">
	#bottom{
		text-align: center;
		margin: 50px auto;
	}
	h2{
		text-align: center;
	}
	form{
		padding: 10px;
	}
	.form-group{
		border-bottom: 1px solid LIGHTGRAY;
		margin-top: 10px;
	}
	.sidebar-nav{
		display: none;
	}
}
</style>
</head>
<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
	<div class="row-fluid">
		<%@include file="/WEB-INF/views/sideBar.jsp" %>
		<div id="content" class="span10" style="min-height: 844px; margin: auto;"> 
			<h2>개인정보 조회</h2>
			<div class="box">
			<form method="post"id="form" name="form" action="./modifySuccess.do">
				<input type="hidden" id="confmKey" name="confmKey" value="">
				<div class="form-group">
			      <label for="user_id"><strong>아이디</strong></label>
			      <p class="form-control">${user.user_id}</p>
			    </div>
				<div class="form-group">
			      <label for="user_name"><strong>성명</strong></label>
			      <p class="form-control">${user.user_name}</p>
			    </div>
			    <div class="form-group">
			      <label for="user_email"><strong>이메일</strong></label> 
			      <p class="form-control">${user.user_email}</p>
			    </div>
			    <div class="form-group">
			      <label for="user_phone"><strong>전화번호</strong></label>
			      <p class="form-control">${user.user_phone}</p>
			    </div>
			    <div class="form-group">
			      <label for="user_address"><strong>주소</strong></label>
			      <p class="form-control">${user.user_address}</p>
			    </div>
			    <div class="form-group">
			      <label for="user_birth"><strong>생일</strong></label>
			      <p class="form-control">${user.user_birth}</p>
			    </div>
			    <div class="form-group">
			    	<label for="email"><strong>이메일 수신 동의 여부</strong></label>
			    	<c:if test="${user.user_eagree eq 'Y'}">
			    		<p class="form-control">동의</p>
			    	</c:if>
			    	<c:if test="${user.user_eagree eq 'N'}">
			    		<p class="form-control">미동의</p>
			    	</c:if>
			    </div>
			    <div class="form-group" style="border: none;">
			    	<label for="user_sagree"><strong>SMS 수신 동의 여부</strong></label>
			    	<c:if test="${user.user_sagree eq 'Y'}">
			    		<p class="form-control">동의</p>
			    	</c:if>
			    	<c:if test="${user.user_sagree eq 'N'}">
			    		<p class="form-control">미동의</p>
			    	</c:if>
			    </div>
			  </form>
			  </div>
			  <div id="bottom">
				<a class="btn btn-basic" href="./modifyMyInfo.do">내 정보 수정</a>
				<input type="button" class="btn btn-basic" value="돌아가기" onclick="javascript:location.replace('./usermain.do')">
			  </div>
			</div>	
		</div>
	</div>
<%@include file="/WEB-INF/views/footer.jsp"%>		
</body>
</html>