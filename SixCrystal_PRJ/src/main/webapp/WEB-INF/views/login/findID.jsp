<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC</title>
<style type="text/css">
body { background: url(img/bg-login.jpg) !important; }
.login-box{
	margin: 10px auto;
	padding : 10px;
}
#form {
	width: 500px;
	margin: 10px auto;
	border: 1px solid #ccc;
	padding: 10px;
}
#head {
	text-align: center;
	font-size: 25px;
	margin-top: 80px;
}
</style>
</head>
<script type="text/javascript">
window.onload = function(){
	
	var regPhone = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
	var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	
	$("#user_info").keyup(function(){
		var info = $(this).val();
		if(regPhone.test(info) || regEmail.test(info)){
			$("#msg").css({"color":"blue","font-size":"12px"});
			$("#msg").html("  올바른 형식입니다.");
		}else{
			$("#msg").css({"color":"red","font-size":"12px"});
			$("#msg").html("  올바른 형식이 아닙니다.");
		}
	});	
	
	$("#chk").click(function(){
		$("#result").html("");
		if($("#user_name").val() == ""){
			swal("정보입력 오류","이름을 입력해주세요.");
		}else if(!regPhone.test($("#user_info").val()) && !regEmail.test($("#user_info").val())){
			swal("정보입력 오류","전화번호 또는 이메일의 형식을 확인해주세요.");
		}else{
			$("#msg").html("");
			$.ajax({ 
				url: "./findID.do",
				data: (regEmail.test($("#user_info").val())) ? "user_name="+$("#user_name").val()+"&user_email="+$("#user_info").val() : "user_name="+$("#user_name").val()+"&user_phone="+$("#user_info").val() , 
				type: "post", 
				success: function(msg) { 
					if(msg.isc=="leave"){
						$("#result").css("display","");
						$("#here").css({"font-size":"14px","color":"red"});
						$("#result").html("탈퇴한 회원입니다.");
						$("#chk").css("display","none");
						$("#anker").css("display","");
					}else if (msg.isc=="true") { 
						$("#result").css("display","");
						$("#here").css("font-size","14px");
						$("#result").html("아이디 : "+msg.id);
						$("#chk").css("display","none");
						$("#anker").css("display","");
					} else { 
						$("#result").css("display","");
						$("#result").html("가입된 회원이 아닙니다.");
					} 
				},
				error: function(){
					swal("오류","잘못된 요청입니다.");
				}	
			});
		}
	});
	
}
</script>
<body>
<%@include file="/WEB-INF/views/topLogin.jsp" %>
<div class="container-fluid-full">
	<div class="row-fluid">
	<div id="head">
		<h1>아이디 찾기</h1>
	</div>
	<div class="login-box" style="width:550px;">
	<form class="form-horizontal" method="post" id="form" name="form">
	<div class="input-prepend">
	<br>
      <label for="user_name">이름&nbsp;&nbsp;
      <input type="text" class="input-large" id="user_name" name="user_name">
      </label>
    </div>
    <div class="input-prepend">
    <br>
      <label for="user_phone">전화번호 또는 이메일&nbsp;
      <input type="text" class="input-large span8" id="user_info" placeholder="전화번호(- 포함 13자리) / 이메일(xxxxx@xxx.xxx)">
      </label>
      <div style="height: 14px; ">
      <label id="msg"> </label>
      </div>
    </div>
    <div id="here" class="input-prepend" style="height: 50px; "> 
    	<label id="result" style="display: none;"> </label>
    </div>
    <div class="input-prepend" style="text-align: right;">
      <input type="button" class="btn btn-basic" id="chk" value="확인">
    </div>
    
    <div id="anker" style="text-align: center; display: none;">
    <br>
    	<a href=" ./loginForm.do">로그인 하러 가기</a> / <a href="./pwFindForm.do">비밀번호 찾기</a>
    </div>
</form>
</div>
</div>
</div>
</body>
</html>