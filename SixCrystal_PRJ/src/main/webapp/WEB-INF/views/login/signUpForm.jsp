<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 가입</title>
<style type="text/css">
body { background: url(img/bg-login.jpg) !important; }
.login-box{
	margin: 10px auto;
	padding : 10px;
}
#form{
	width: 500px;
	margin: 10px auto;
	border: 1px solid #ccc;
	padding: 10px;
}
/* #idP, #email, #phone, #address { */
/* 	color: Silver; */
/* } */
#head {
	text-align: center;
	font-size: 25px;
	margin-top: 80px;
}
input[type='radio']{
	margin-top: -3px;
}
</style>
</head>
<script type="text/javascript">
//opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. ("팝업API 호출 소스"도 동일하게 적용시켜야 합니다.)
//document.domain = "abc.go.kr";

function phoneChk(){
	var pop = window.open("./phoneChkForm.do", "_blank", "toolbar=yes,scrollbars=yes,resizable=no,top=200,left=700,width=450,height=320");
}

function emailChk(){
	var pop = window.open("./emailChkForm.do", "_blank", "toolbar=yes,scrollbars=yes,resizable=no,top=200,left=700,width=450,height=320");
}

function idChk(){
	var pop = window.open("./idChkForm.do", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=500,width=400,height=400");
}

function goPopup(){
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
  var pop = window.open("./jusoPopup.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
  
	// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
  //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
}
/** API 서비스 제공항목 확대 (2017.02) **/
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn
						, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
	// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
	document.form.user_address.value = roadFullAddr;
}

window.onload = function(){
	
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
	
	$("#passOk").keyup(function(){
		var pw = document.getElementById('user_password');
		var pwChk = document.getElementById('passOk');
		if(pw.value != pwChk.value){
			$("#msg2").css({"color":"red","font-size":"12px"});
			$("#msg2").html("비밀번호가 일치하지 않습니다.");
			pwChk.focus();
		}else{
			$("#msg2").css({"color":"blue","font-size":"12px"});
			$("#msg2").html("비밀번호가 일치합니다.");
		}
	});
	
	document.getElementById('signUp').onclick = function(){
		var idVal = document.getElementById("user_id");
		var pwVal = document.getElementById("user_password");
		var pwChkVal = document.getElementById("passOk");
		var nVal = document.getElementById("user_name");
		var eVal = document.getElementById("user_email");
		var pVal = document.getElementById("user_phone");
		var aVal = document.getElementById("user_address");
		var bVal = document.getElementById("user_birth");
		var regPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;
		
		if(idVal.value== ""){
			swal("아이디를 입력해 주세요");
			idVal.focus();
			return false;
		}else if(pwVal.value == ""){ // 입력을 안하면
			swal("비밀번호를 입력해 주세요");
			pwVal.focus();
			return false;
		}else if(nVal.value == ""){ // 입력을 안하면
			swal("이름을 입력해 주세요");
			pwVal.focus();
			return false;
		}else if(eVal.value == ""){ // 입력을 안하면
			swal("이메일을 입력해 주세요");
			pwVal.focus();
			return false;
		}else if(pVal.value == ""){ // 입력을 안하면
			swal("핸드폰번호를 입력해 주세요");
			aVal.focus();
			return false;
		}else if(aVal.value == ""){ // 입력을 안하면
			swal("주소를 입력해 주세요");
			eVal.focus();
			return false;
		}else if(bVal.value == ""){ // 입력을 안하면
			swal("생년월일을 입력해 주세요");
			eVal.focus();
			return false;
		}else if(!regPw.test(pwVal.value)){
			swal("사용할 수 없는 비밀번호입니다.");
			pwVal.focus();
			return false;
		}else if(pwVal.value.trim() != pwChkVal.value.trim()){
			swal("비밀번호가 다릅니다.");
			pwChkVal.focus();
			return false;
		}else{
			document.getElementById('signUp').setAttribute('disabled','disabled');
			document.form.submit();
		} 
	}
}

</script>
<body>
<%@include file="/WEB-INF/views/topLogin.jsp" %>
<div class="container-fluid-full">
	<div class="row-fluid">
	<div id="head">
		<h1>회원가입</h1>
	</div>
	<div class="login-box" style="width:600px;">
		<form class="form-horizontal" method="post" id="form" name="form" action="./signUpSuccess.do">
			<input type="hidden" id="confmKey" name="confmKey" value="">
 			<div class="input-prepend">
			<br>
			<br>
    			<label>아이디&nbsp;&nbsp;&nbsp;
				<input class="input-large" type="text" name="user_id" id="user_id" readonly="readonly" placeholder="아이디 입력">
    			<input class="btn btn-basic" type="button" value="중복 확인" onclick="idChk();" style="margin-bottom : 5px;">
				</label>
 			</div>
 			<div class="input-prepend">
 			<br>
    			<label>비밀번호&nbsp;&nbsp;&nbsp;
				<input class="input-large" type="password" id="user_password" name="user_password" placeholder="비밀번호">
				</label>
				<div  style="height: 15px;">
				<label id="msg1"> </label>
				</div>
 			</div>
			<div class="input-prepend">
     			<label>비밀번호 확인&nbsp;&nbsp;	
				<input class="input-large"  type="password" id="passOk" placeholder="비밀번호 확인">
				</label>
				<div  style="height: 15px;">
				<label id="msg2"> </label>
				</div>
  			</div>	
  			<div class="input-prepend">
  			<br>
     			<label>성명&nbsp;&nbsp;&nbsp;&nbsp;
     			<input type="text" class="input-large" id="user_name" name="user_name" placeholder="성명">
  				</label>
  			</div>
			<div class="input-prepend" style="display: none;">
     			<label>타입</label>	
				<input class="form-control" type="text" id="user_type" name="user_type" value=${user_type}>
			</div>
			<div class="input-prepend">
			<br>
     			<label>이메일&nbsp;&nbsp;&nbsp;&nbsp;	
   				<input type="text" class="input-large" id="user_email" name="user_email" placeholder="이메일 입력" readonly="readonly">
   				<input class="btn btn-basic" type="button" value="인증하기" onclick="emailChk();" style="margin-bottom : 5px;">
				</label> 
			</div>  
			<div class="input-prepend">
			<br>
     			<label>휴대전화&nbsp;&nbsp;&nbsp;
     			<input type="text" class="input-large" id="user_phone" name="user_phone" placeholder="휴대전화 입력(-포함)" readonly="readonly"> 
     			<input class="btn btn-basic" type="button" value="인증하기" onclick="phoneChk();" style="margin-bottom : 5px;">
				</label> 	
			</div>
			<div class="input-prepend">
			<br>
     			<label>주소&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="input-large span8" type="text" id="user_address" name="user_address" readonly="readonly" placeholder="주소 입력"/> 
				<input class="btn btn-basic" type="button" value="주소 검색" onclick="goPopup();" style="margin-bottom : 5px;">
				</label>	
			</div>
			<div class="input-prepend">	
			<br>
				<label> 생년월일&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="input-large" type="date" id="user_birth" name="user_birth" style="margin-top: 5px;">
				</label>
			</div>
			<div class="input-prepend">
			<br>
				<label>성별 선택&nbsp;&nbsp;&nbsp;&nbsp; 
					<input type="radio" name="user_gender" value="F" checked="checked" style="margin-top: -3px;"> 여
					<input type="radio" name="user_gender" value="M" style="margin-top: -3px;"> 남
				</label>
			</div>
			<div class="input-prepend"> 
			<br>
				<label>이메일 수신동의 선택&nbsp;&nbsp;&nbsp;&nbsp; 
					<input type="radio" name="user_eagree" value="Y" checked="checked" style="margin-top: -3px;"> 예
					<input type="radio" name="user_eagree" value="N" style="margin-top: -3px;"> 아니오
				</label>
			</div>	
			<div class="input-prepend">		
			<br>
				<label>SMS 수신동의 선택&nbsp;&nbsp;&nbsp;&nbsp; 
					<input type="radio" name="user_sagree" value="Y" checked="checked" style="margin-top: -3px;"> 예
					<input type="radio" name="user_sagree" value="N" style="margin-top: -3px;"> 아니오
				</label>
			<br>	
			</div>	
			<div class="input-prepend" id="botton" style="text-align: center">
			<br>
			<label>
				<input id="signUp" type="button" class="btn btn-primary" value="회원가입">&nbsp;&nbsp;
				<input type="button" class="btn btn-basic" value="돌아가기" onclick="javascript:history.back(-1)">
			</label>
			</div>
			<br>
		</form>
		</div>
	</div>
</div>
</body>
</html>