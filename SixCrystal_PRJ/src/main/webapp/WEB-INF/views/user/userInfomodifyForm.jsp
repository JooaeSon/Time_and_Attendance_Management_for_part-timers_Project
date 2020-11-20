<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SC</title>
<style type="text/css">
	form{
		padding:12px;
	}
	h2{
		text-align: center;
	}
	#bottom{
		text-align: center;
	}
	.form-group{
		border-bottom: 1px solid LIGHTGRAY;
		margin-top: 10px;
		margin-bottom: 5px;
	}
	.sidebar-nav{
		display: none;
	}
}
</style>
</head>
<script type="text/javascript">

function phoneChk(){
	var pop = window.open("./phoneChkForm.do", "_blank", "toolbar=yes,scrollbars=yes,resizable=no,top=200,left=700,width=450,height=320");
}

function emailChk(){
	var pop = window.open("./emailChkForm.do", "_blank", "toolbar=yes,scrollbars=yes,resizable=no,top=200,left=700,width=450,height=320");
}

function goPopup(){
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
  var pop = window.open("./jusoPopup.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn
						, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
	// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
	document.getElementById('address').innerHTML = roadFullAddr;
	document.form.user_address.value = roadFullAddr;
}

window.onload = function(){
	
	var addr = document.getElementById("spanA");
	document.form.user_address.value = addr.innerHTML;
	
	document.getElementById('saveInfo').onclick = function(){
		 var name = document.getElementById("user_name").value;
		 var email = document.getElementById("user_email").value;
		 var phone = document.getElementById("user_phone").value;
		 var address = document.getElementById("user_address").value;
		 var birth = document.getElementById("user_birth").value;
		 
		 if(name.trim()==""){ 
			document.getElementById("user_name").focus();
			swal("회원정보 수정","이름을 입력해 주세요");
		}else if(email.trim()==""){
			document.getElementById("user_email").focus();
			swal("회원정보 수정", "이메일을 입력해 주세요");
		}else if(phone.trim()==""){
			document.getElementById("user_phone").focus();
			swal("회원정보 수정", "휴대전화를 입력해 주세요");
		}else if(address.trim()==""){
			document.getElementById("user_address").focus();
			swal("회원정보 수정", "주소를 입력해 주세요");
		}else if(birth.trim()==""){
			document.getElementById("user_birth").focus();
			swal("회원정보 수정", "생년월일를 입력해 주세요");
		}else{
			swal("정보 수정","회원정보 수정이 완료되었습니다.");
			document.form.submit();
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
			<h2>회원정보 수정</h2>
			<div class="box">
				<form method="post"id="form" name="form" action="./modifySuccess.do">
					<input type="hidden" id="confmKey" name="confmKey" value="">
					<input type="hidden" name="user_id" value=${user.user_id}>
					<div class="form-group">
				      	<label for="user_name"><strong>성명</strong> &nbsp;&nbsp;
				      		<input type="text" class="form-control" id="user_name" name="user_name" value=${user.user_name}>&nbsp;
				    	</label>
				    </div>
				    <div class="form-group">
				      	<label for="user_email"><strong>이메일</strong>&nbsp; 
				      		<input type="text" class="form-control" id="user_email" name="user_email" value=${user.user_email} readonly="readonly">&nbsp;
				      		<input class="btn btn-basic" type="button" value="수정하기" onclick="emailChk();" style="margin-bottom : 5px;">
				    	</label>
				    </div>
				    <div class="form-group">
				      	<label for="user_phone"><strong>전화번호</strong>&nbsp;	
				      		<input type="text" class="form-control" id="user_phone" name="user_phone" value=${user.user_phone} readonly="readonly">&nbsp;
				    		<input class="btn btn-basic" type="button" value="수정하기" onclick="phoneChk();" style="margin-bottom : 5px;">
				    	</label>
				    </div>
				    <div class="form-group">
				      	<label for="user_address"><strong>주소</strong>&nbsp;
				      		<span id="spanA" class="input-large span7" style="display: none;">${user.user_address}</span>
				      		<input type="text" class="input-large span7" id="user_address" name="user_address" readonly="readonly">
				      		<input class="btn btn-basic" type="button" value="주소 검색" onclick="goPopup();" style="margin-bottom : 3px;">
				    	</label>
				    </div>
				    <div class="form-group">
				      	<label for="user_birth"><strong>생년월일</strong>&nbsp;&nbsp;
				      		<input type="date" class="form-control" id="user_birth" name="user_birth" value=${user.user_birth} >
				    	</label>
				    </div>
				    <div class="form-group">
				    <label for="email"><strong>이메일 수신 동의 여부</strong>&nbsp;&nbsp;
				      <c:if test="${fn:trim(user.user_eagree) eq 'Y'}">
				       <input type="radio" name="user_eagree" value="Y" checked="checked" style="margin-top: -3px;">예
					   <input type="radio" name="user_eagree" value="N" style="margin-top: -3px;">아니오
				      </c:if>
				      <c:if test="${fn:trim(user.user_eagree) eq 'N'}">
				       <input type="radio" name="user_eagree" value="Y" style="margin-top: -3px;">예
					   <input type="radio" name="user_eagree" value="N" checked="checked" style="margin-top: -3px;">아니오
				      </c:if>
				      </label>
				    </div>
				    <div class="form-group" style="border: none;">
				    <label for="user_sagree"><strong>SMS 수신 동의 여부</strong>&nbsp;&nbsp;
				      <c:if test="${fn:trim(user.user_sagree) eq 'Y'}">
				       <input type="radio" name="user_sagree" value="Y" checked="checked" style="margin-top: -3px;">예
					   <input type="radio" name="user_sagree" value="N" style="margin-top: -3px;">아니오
				      </c:if>
				      <c:if test="${fn:trim(user.user_sagree) eq 'N'}">
				       <input type="radio" name="user_sagree" value="Y" style="margin-top: -3px;">예
					   <input type="radio" name="user_sagree" value="N" checked="checked" style="margin-top: -3px;">아니오
				      </c:if>
				      </label>
				      </div>
				 </form>
  			</div>
				    <div id="bottom">
				    	<input type="button" class="btn btn-basic" id="saveInfo" value="저장">
				    	<input type="button" class="btn btn-basic" id="back" value="돌아가기" onclick="javascript:location.replace('./myInfo.do')">
				    </div>
 		</div>
 	</div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>	
</body>
</html>