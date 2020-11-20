<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC 로그인</title>
<link rel="shortcut icon" href="img/favicon.ico">
<style type="text/css">
	body { background: url(img/bg-login.jpg) !important; }
</style>
</head>
<script type="text/javascript">
function valChk(){
	var user = document.getElementById("user");
	var pw = document.getElementById("pw");
	if(user.value == ""){
		document.getElementById("err").innerHTML = "";
		document.getElementById("msg").innerHTML = "아이디를 입력해 주세요.";
		return false;
	}else if(pw.value == ""){
		document.getElementById("err").innerHTML = "";
		document.getElementById("msg").innerHTML = "비밀번호를 입력해 주세요.";
		return false;
	}
	return true;
}
</script>
<body>
<%@include file="/WEB-INF/views/topLogin.jsp" %>
<!-- 시큐리티를 사용하기 위해서는 반드시 POST여야만 한다 -->
		<div class="container-fluid-full">
		<div class="row-fluid">
					
			<div class="row-fluid">
				<div class="login-box">
					<div class="icons">
<!-- 						<a href="index.jsp"><i class="halflings-icon home"></i></a> -->
<!-- 						<a href="#"><i class="halflings-icon cog"></i></a> -->
					</div>
					<h2><strong>로그인</strong></h2>
					<form class="form-horizontal" action="./login.do" method="POST" onsubmit="return valChk();">
						<fieldset>
							<div class="input-prepend" title="Username">
								<span class="add-on"><i class="halflings-icon user"></i></span>
								<input class="input-large span10" name="username" id="user" type="text" placeholder="아이디를 입력하세요"/>
							</div>
							<div class="clearfix"></div>
							<div class="input-prepend" title="Password">
								<span class="add-on"><i class="halflings-icon lock"></i></span>
								<input class="input-large span10" name="password" id="pw" type="password" placeholder="비밀번호를 입력하세요"/>
								<div style="height:16px; text-align: left; margin-left: 20px; margin-top: 10px;">
									<label id="msg" style="color: red; font-size: 13px;"></label>		
      								<label id="err" style="color: red; font-size: 13px;">${ERRORMSG}</label>
      							</div>
							</div>
							<div class="clearfix"></div>
							<div class="remember">
								<label for="remember-me"><input name="remember-me" type="checkbox" id="remember" style="margin-top: -3px;"/> 자동 로그인</label>
							</div>
							<div class="button-login">	
								<button type="submit" class="btn btn-primary">로그인</button>
							</div>
							<div class="clearfix"></div>
							</fieldset>
					</form>
					<hr>
					<h3>회원가입</h3>
					<p>
						<a href="./agreeForm.do?employer">고용주로 회원가입</a> / <a href="./agreeForm.do?employee">일반직원으로 회원가입</a>
					</p>
					<hr>
					<h3>비밀번호를 잊으셨나요?</h3>
					<p>
						<a href="./idFindForm.do">아이디 찾기</a> / <a href="./pwFindForm.do">비밀번호 찾기</a>
					<br>
					<br>
					</p>
				</div><!--/span-->
			</div><!--/row-->
		</div><!--/.fluid-container-->
	</div><!--/fluid-row-->
	
</body>
</html>