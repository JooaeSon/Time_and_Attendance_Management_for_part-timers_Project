<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC</title>
<style type="text/css">
body {
	background: url(img/bg-login.jpg) !important;
}

.login-box {
	margin: 10px auto;
	padding: 10px;
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

#chkInfo, #chkPhone, #chkEmail {
	text-align: center;
	margin-top: 10px;
}

#phone, #sms, #email, #emKey {
	width: 300px;
}
</style>
</head>
<script type="text/javascript">
window.onload = function(){
	
	$("#back").click(function(){
		location.replace('./loginForm.do');
	});
	
	$("#next").click(function(){
		$.ajax({ 
			url: "./chkIdForPw.do",
			data: "user_id="+$("#user_id").val(), 
			type: "post", 
			success: function(msg) { 
				if(msg=="leave"){
					swal("탈퇴한 회원입니다.");
					$("#next").css("display","none");
					$("#back").css("display","");
				}
				else if (msg=="true") { 
					$("#id").val($("#user_id").val());
					$("#anker").css("display","none");
					$("#insertId").css("display","none");
					$("#chkInfo").css("display","");
				} else { 
					swal("존재하지 않는 아이디입니다.");
					$("#anker").css("display","");
				} 
			},
			error: function(){
				swal("오류","잘못된 요청입니다.");
			}	
		});
	});
	
	$("#goPhone").click(function(){
		$("#chkInfo").css("display","none");
		$("#chkPhone").css("display","");
	});
	
	$("#goEmail").click(function(){
		$("#chkInfo").css("display","none");
		$("#chkEmail").css("display","");
	});
	
	$("#chkPFin").click(function(){
		$("#chkPhone").css("display","none");
		$("#newPwInsert").css("display","");
	});
	
	$("#chkEFin").click(function(){
		$("#chkEmail").css("display","none");
		$("#newPwInsert").css("display","");
	});
	
	$("#user_password").keyup(function(){
		var pw = $(this).val();
		var pwLen = $(this).val().length;
		var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;

		if(pw.indexOf(" ") != -1){
			$("#msg1").css({"color":"red","font-size":"12px"});
			$("#msg1").html("공백이 포함된 비밀번호는 입력 하실 수 없습니다.");
		}else if(!regExpPw.test(pw)){
			$("#msg1").css({"color":"red","font-size":"12px"});
			$("#msg1").html("영문,숫자,특수문자[ex) `~!@#$%^&*]를 조합한 8자 이상의 비밀번호를 입력해야 합니다.");
		}else{
			$("#msg1").html("");
		}
	});
	
	$("#passwordChk").keyup(function(){
		var pw = document.getElementById('user_password');
		var pwChk = document.getElementById('passwordChk');
		if(pw.value != pwChk.value){
			$("#msg2").css({"color":"red","font-size":"12px"});
			$("#msg2").html("비밀번호가 일치하지 않습니다.");
			pwChk.focus();
		}else{
			$("#msg2").css({"color":"blue","font-size":"12px"});
			$("#msg2").html("비밀번호가 일치합니다.");
		}
	});
	
	document.getElementById('change').onclick = function(){
		var pw = $("#user_password").val();
		var pwChk = $("#passwordChk").val();
		var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;
		if(pw.indexOf(" ") != -1){
			swal("비밀번호 형식 오류","공백이 포함된 비밀번호는 입력 하실 수 없습니다.");
		}else if(!regExpPw.test(pw)){
			swal("비밀번호 형식 오류","영문,숫자,특수문자[ex) `~!@#$%^&*]를 조합한 8자 이상의 비밀번호를 입력해야 합니다.");
		}else if(pw != pwChk){
			swal("비밀번호 불일치","비밀번호 확인을 올바로 입력해 주세요.");
		}else{
			swal("비밀번호 수정 완료","로그인 창으로 이동합니다.");
			document.form.submit();
		}
	}
	
}

function sendSms() { // 인증번호 전송 ajax 처리
	var regPhone = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
	
	var phone = $("#phone").val();
	if(!regPhone.test(phone)){
		swal("전화번호 형식 오류","( - )포함 13자리를 입력해 주세요.\n\t ex) 010-0000-0000");
	}else{
		$.ajax({ 
			url: "./sendSmsForPw.do",
			data: "user_id="+$("#id").val()+"&phone="+$("#phone").val(), 
			type: "post", 
			success: function(msg) { 
				if(msg.match == "false"){
					swal("SMS인증 오류","등록된 핸드폰 번호가 아닙니다."); 
				}else{
					if (msg.isc == "true") { 
						console.log(msg);
						swal("SMS인증","인증번호 전송 성공"); 
						$("#phone").attr("readonly","readonly");
						$("#sendS").val("재전송");
					} else { 
						swal("SMS인증 오류","인증번호 전송 실패"); 
					} 
				}
			},
			error: function(){
				swal("오류","잘못된 요청입니다.");
			}
		}); 
	}
} 

function numberCheck(){ // 인증번호 체크 ajax 처리
	$.ajax({ 
		url: "./smsCheck.do", 
		type: "post", 
		data: "sms="+$("#sms").val(),
		success: function(msg) { 
			if (msg == "ok") { 
				swal("SMS인증","번호 인증 성공"); 
				$("#sms").attr("readonly","readonly");
				$("#chkPFin").css("display","");
			} else { 
				swal("SMS인증 오류","번호 인증 실패"); 
				$("#chkPFin").css("display","none");
			} 
		},
		error: function(){
			swal("잘못된 요청입니다.");
		}
	}); 
}

function sendEmail(){
	
	var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	
	var email = $("#email").val();
	if(!regEmail.test(email)){
		swal("이메일 형식 오류","입력 예) scproject@gmail.com ");
	}else{
		$.ajax({ 
			url: "./sendEmailForPw.do",
			data: "user_id="+$("#id").val()+"&email="+$("#email").val(), 
			type: "post", 
			success: function(msg) { 
				if(msg.match == "false"){
					swal("Email인증 오류","등록된 이메일이 아닙니다."); 
				}else{
					if (msg.isc == "true") { 
						console.log(msg);
						swal("Email인증","인증키 전송 성공"); 
	    				$("#email").attr("readonly","readonly");
	    				$("#sendE").val("재전송");
					} else { 
						swal("Email인증 오류","인증키 전송 실패");  
					} 
				}
			},
			error: function(){
				swal("오류","잘못된 요청입니다.");
			}
		}); 
	}
	
}

function keyCheck(){
	$.ajax({ 
		url: "./emailCheck.do", 
		type: "post", 
		data: "emKey="+$("#emKey").val(),
		success: function(msg) { 
			if (msg == "true") { 
				swal("Email인증","인증 성공"); 
				$("#chkEFin").css("display","");
			} else { 
				swal("Email인증 오류","인증 실패"); 
				$("#chkEFin").css("display","none");
			} 
		},
		error: function(){
			swal("잘못된 요청입니다.");
		}
	}); 
}
</script>
<body>
<%@include file="/WEB-INF/views/topLogin.jsp" %>
<div class="container-fluid-full">
	<div class="row-fluid">
	<div id="head">
		<h1>비밀번호 찾기</h1>
	</div>
	<div class="login-box" style="width:550px;">
		<form method="post" action="./modifyPwSuccess.do" id="form" name="form">
			<div id="insertId">
				<div class="input-prepend" id="UserId">
				<br>
			      <label for="user_id">아이디 입력&nbsp;&nbsp;
			      <input type="text" class="input-large" id="user_id" name="user_id">
			      </label>
			    </div>
			    <div class="input-prepend" style="text-align: center;">
			      <input type="button" class="btn btn-basic" id="next" value="다음">
			      <input type="button" class="btn btn-basic" id="back" value="돌아가기" style="display:none;">
			    </div>
		    </div>
		    <input type="hidden" id="id">
		    <div id="chkInfo" style="display: none;">
			    <div class="input-prepend">
			      <br>
			      <label>
			      <input type="button" class="btn btn-basic" id="goPhone" value="휴대전화로 인증하기">&nbsp;&nbsp; 
			      <input type="button" class="btn btn-basic" id="goEmail" value="이메일로 인증하기">
			      <br>
			      </label>
			    </div>
			    <br>
		    </div>
		    <div id="chkPhone" style="display: none;">
			   <div class="input-prepend">
			   <br>
			   <label>
					<input type="text" class="input-large" name="phone" id="phone" placeholder="전화번호 입력 (-포함 13자리 )" />
	      			<input type="button" class="btn btn-basic" onclick="sendSms();" id="sendS" value="전송"/>
	      		</label>
				</div>
				<div class="input-prepend">
					<br>
					<label>
					<input type="text" class="input-large" name="sms" id="sms" placeholder="인증 번호 6자리 입력" /> 
					<input type="button" class="btn btn-basic" id="chkP" onclick="numberCheck();" value="인증"/> 
					</label>
				</div>
				<div class="input-prepend" style="text-align: center;">
					<input type="button" class="btn btn-basic" id="chkPFin" value="인증완료" style="display:none;"/> 
				</div>
		    </div>
			<div id="chkEmail" style="display: none;">
				<div class="input-prepend">
					<label>
					<input type="text" class="input-large" name="email" id="email" placeholder="이메일 입력 ( xxxxx@xxx.xxx )" />
					<input type="button" class="btn btn-basic" onclick="sendEmail();" id="sendE" value="전송" />
					</label>
				</div>
				<div class="input-prepend">
				<label>
					<input type="text" class="input-large" name="emKey" id="emKey" placeholder="인증 키 입력" /> 
					<input type="button" class="btn btn-basic" id="chkE" onclick="keyCheck();" value="인증" />
				</label>
				</div>
				<div class="input-prepend" style="text-align: center;">
					<input type="button" class="btn btn-basic" id="chkEFin" value="인증완료" style="display: none;" />
				</div>
			</div>
			<div id="newPwInsert" style="display: none;">
				<div class="input-prepend">
				<br>
					<label>새 비밀번호 입력
					<input type="password" class="input-large" id="user_password" name="user_password">
					</label> 
					<div style="height: 14px;">
					<label id="msg1"> </label>
					</div>
				</div>
				<div class="input-prepend">
					<label>새 비밀번호 확인
					<input type="password" class="input-large" id="passwordChk"> 
					</label> 
					<div style="height: 14px;">
					<label id="msg2"> </label>
					</div>
				</div>
				<div class="input-prepend" style="text-align: center;">
					<input type="button" class="btn btn-basic" id="change" value="변경">
				</div>
			</div>
		</form>
		<div id="anker" style="text-align: center; display: none;">
	    	<a href="./idFindForm.do">아이디 찾기</a> / <a href=" ./loginForm.do">로그인 하러 가기</a>
	    </div>
	</div>
</div>
</div>
</body>
</html>