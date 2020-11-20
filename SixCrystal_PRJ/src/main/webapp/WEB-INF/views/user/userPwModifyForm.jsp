<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC</title>
<style type="text/css">
	form{
		width:500px; 
		padding:20px;
		text-align: center;
	}
	h2{
		text-align: center;
	}
	.sidebar-nav{
		display: none;
	}
	.box{
		margin: 20px auto;
		text-align: center;
	}
	#bottom{
		text-align: center;
	}
</style>
</head>
<script type="text/javascript">
 window.onload = function(){
	 document.getElementById('conf').onclick = function(){
			$.ajax({
				url: "./chkNowPw.do",
				data: "user_id="+$("#user_id").val()+"&user_password="+$("#user_password").val(),
				type: "post",
				success: function(msg){
					if(msg=="false"){
						swal("비밀번호 불일치","비밀번호를 확인해주세요.");
					}else{
						location.href="./insertNewPwForm.do";
					}
				},
				error: function(){
					swal("오류","잘못된 요청입니다.");
				}
			});
		}
 }
</script>
<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
	<div class="row-fluid">
		<%@include file="/WEB-INF/views/sideBar.jsp" %>
		<div id="content" class="span10" style="min-height: 844px; margin: auto;"> 
			<h2>비밀번호 수정</h2>
			<form id="form" name="form" class="box">
				<input type="hidden" id="user_id" name="user_id" value=${user.user_id}>
				<div class="form-group">
			      <label for="user_password">현재 비밀번호 입력</label>
			      <input type="password" class="form-control" id="user_password" name="user_password" required="required">
			    </div>
			    <div class="form-group" style="text-align: center;">
			      <input type="button" class="btn btn-basic" id="conf" value="확 인">
			    </div>
			</form>
			<div id="bottom">
				<input type="button" class="btn btn-basic" value="돌아가기" onclick="javascript:location.replace('./usermain.do')">
			</div>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>