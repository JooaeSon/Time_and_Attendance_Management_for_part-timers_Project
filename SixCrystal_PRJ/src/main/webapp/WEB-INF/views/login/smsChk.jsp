<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SMS인증</title>
<style type="text/css">
	#container{
		 width:340px; 
		 padding:20px; 
		 margin: 60px auto; 
		 text-align: center; 
	}
	input[type=text]{
		width: 200px;
		height: 30px;
	}
</style>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<body>
<div id="container"> 
	<!-- 문자보내는 폼 -->
	<form id="smsForm" style="padding: 5px; margin: 5px;">
		<div class="form-group">
			<!-- 전화번호 입력시 반드시 - 포함 -->
			<input type="text" name="phone" id="phone" placeholder="전화번호 입력 ( - 포함 )" />
      		<input type="button" class="btn btn-basic" onclick="sendSms();" id="send" value="전송"/>
		</div>
		<div class="form-group">
			<input type="text" name="sms" id="sms" placeholder="인증 번호 6자리 입력" /> 
			<input type="button" class="btn btn-basic" id="chk" onclick="numberCheck();" value="인증"/> 
		</div>
		<div class="form-group" style="text-align: center;">
			<input type="button" class="btn btn-basic" id="usePhone" value="인증완료" style="display:none;"/> 
		</div>
	</form>
</div> 
  <script>
    function sendSms() { // 인증번호 전송 ajax 처리
    	var regPhone = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
    	
    	var phone = $("#phone").val();
		if(!regPhone.test(phone)){
			swal("전화번호 형식 오류","( - )포함 13자리를 입력해 주세요.\n\t ex) 010-0000-0000");
		}else{
			$.ajax({ 
	    		url: "./sendSms.do",
	    		data: "user_phone="+$("#phone").val(), 
	    		type: "post", 
	    		success: function(msg) {
	    			if(msg.available == "false"){
	    				swal("SMS인증","이미 가입된 번호입니다."); 
	    			}else{
		    			if (msg.isc == "true") { 
		    				console.log(msg);
		    				swal("SMS인증","인증번호 전송 성공"); 
		    				$("#phone").attr("readonly","readonly");
		    				$("#send").val("재전송");
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
    				$("#usePhone").css("display","");
    			} else { 
    				swal("SMS인증 오류","번호 인증 실패"); 
    				$("#usePhone").css("display","none");
    			} 
    		},
    		error: function(){
    			swal("잘못된 요청입니다.");
    		}
    	}); 
    }
    $(document).ready(function(){
   	 	$("#usePhone").click(function(){
			var val = document.getElementById("phone").value;
			opener.document.getElementById("user_phone").value = val;
			self.close();	
		});
    });
    
  </script>
</body>
</html>