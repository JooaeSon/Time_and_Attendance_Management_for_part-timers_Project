<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 인증</title>
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
<script type="text/javascript">
	function sendEmail(){
		var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
    	
    	var email = $("#email").val();
    	
		if(!regEmail.test(email)){
			swal("이메일 형식 오류","입력 예 > scproject@gmail.com");
		}else{
			$.ajax({ 
	    		url: "./sendEmail.do",
	    		data: "user_email="+$("#email").val(), 
	    		type: "post", 
	    		success: function(msg) { 
	    			if(msg.available == "false"){
	    				swal("Email인증","이미 가입된 이메일입니다."); 
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
    				$("#useEmail").css("display","");
    			} else { 
    				swal("Email인증 오류","인증 실패"); 
    				$("#useEmail").css("display","none");
    			} 
    		},
    		error: function(){
    			swal("잘못된 요청입니다.");
    		}
    	}); 
	}
	
	$(document).ready(function(){
   	 	$("#useEmail").click(function(){
			var val = document.getElementById("email").value;
			opener.document.getElementById("user_email").value = val;
			self.close();	
		});
    });
</script>
<body>
<div id="container"> 
	<form action="">
		<div id="chkEmail">
			<div class="form-group">
				<input type="text" name="email" id="email" placeholder="이메일 입력 ( xxxxx@xxx.xxx )" /> 
				<input type="button" class="btn btn-basic" onclick="sendEmail();" id="sendE" value="전송" />
			</div>
			<div class="form-group">
				<input type="text" name="emKey" id="emKey" placeholder="인증 키 입력" /> 
				<input type="button" class="btn btn-basic" id="chkE" onclick="keyCheck();" value="인증" />
			</div>
			<div class="form-group" style="text-align: center;">
				<input type="button" class="btn btn-basic" id=useEmail value="인증완료" style="display: none;" />
			</div>
		</div>
	</form>
</div>
</body>
</html>