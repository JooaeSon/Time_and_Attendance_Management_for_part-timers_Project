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
		border: 3px solid #ccc; 
		padding: 30px;
	}
	h2{
		text-align: center;
	}
	#bottom{
		text-align: center;
	}
	.sidebar-nav{
		display: none;
	}
	.box{
		margin: 20px auto;
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
						var con = confirm("탈퇴 이후 서비스 이용이 불가합니다.\n정말 탈퇴하시겠습니까?");
						if(con){
							swal("탈퇴되었습니다.");
							document.form.submit();
						}else{
							return false;				
						}
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
			<h2>회원 탈퇴</h2>
				<form method="post"id="form" name="form" action="./leaveUsSuccess.do" class="box">
					<input type="hidden" id="confmKey" name="confmKey" value="">
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