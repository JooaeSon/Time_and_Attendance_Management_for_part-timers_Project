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
	#msg1, #msg2{
		height: 17px;
	}
</style>
</head>
<script type="text/javascript">
window.onload = function(){
	
	$("#user_password").keyup(function(){
		var pw = $(this).val();
		var pwLen = $(this).val().length;
		var pastPw = $("#pastPw").val();
		var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;

		if(pw.indexOf(" ") != -1){
			$("#msg1").css({"color":"red","font-size":"12px"});
			$("#msg1").html("공백이 포함된 비밀번호는 입력 할 수 없습니다.");
		}else if(!regExpPw.test(pw)){
			$("#msg1").css({"color":"red","font-size":"12px"});
			$("#msg1").html("영문,숫자,특수문자[ex) `~!@#$%^&*]를 조합한 8자 이상의 비밀번호를 입력해야 합니다.");
		}else if(pw.trim() == pastPw.trim()){
			$("#msg1").css({"color":"red","font-size":"12px"});
			$("#msg1").html("현재 비밀번호와 같은 비밀번호는 사용 할 수 없습니다.");
		}else{
			$("#msg1").html("");
		}
	});
	
	$("#passwordChk").keyup(function(){
		var uPw = document.getElementById('user_password');
		var pwChk = document.getElementById('passwordChk');
		if(uPw.value != pwChk.value){
			$("#msg2").css({"color":"red","font-size":"12px"});
			$("#msg2").html("비밀번호가 일치하지 않습니다.");
			pwChk.focus();
		}else{
			$("#msg2").css({"color":"blue","font-size":"12px"});
			$("#msg2").html("비밀번호가 일치합니다.");
		}
	});
	
	document.getElementById('change').onclick = function(){
		var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;
		
		if($("#user_password").val() != $("#passwordChk").val()){
			swal("비밀번호 불일치","비밀번호 확인을 올바로 입력해 주세요.");
		}else if($("#user_password").val() == $("#pastPw").val()){
			swal("비밀번호 입력오류","현재 비밀번호와 같은 비밀번호는 사용 할 수 없습니다.");
		}else if(!regExpPw.test($("#user_password").val())){
			swal("비밀번호 입력오류","영문,숫자,특수문자[ex) `~!@#$%^&*]를 조합한 8자 이상의 비밀번호를 입력해야 합니다.");
		}else{
			var con = confirm("비밀번호를 수정하면 재로그인 해야합니다.\n정말 수정하시겠습니까?");
			if(con){
				document.form.submit();
			}else{
				location.href="./usermain.do";				
			}
		}
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
			<form method="post" action="./modifyPwSuccess.do" id="form" name="form" class="box">
				<input type="hidden" id="user_id" name="user_id" value=${user.user_id}>
				<input type="hidden" id="pastPw" value=${nowPw}>
				<div class="form-group">
			      <label for="user_name">새 비밀번호 입력</label>
			      <input type="password" class="form-control" id="user_password" name="user_password">
			      <label id="msg1"> </label>
			    </div>
			    <div class="form-group">
			      <label for="user_name">새 비밀번호 확인</label>
			      <input type="password" class="form-control" id="passwordChk">
			      <label id="msg2"> </label>
			    </div>
			    <div class="form-group" style="text-align: center;">
			      <input type="button" class="btn btn-basic" id="change" value="변 경">
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