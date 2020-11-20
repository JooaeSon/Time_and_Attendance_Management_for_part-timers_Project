<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 검사</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
	#container{
		 width:320px; 
		 padding:20px; 
		 margin: 60px auto; 
		 text-align: center; 
	}
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
/* 	window.addEventListener("beforeunload", function(event) {
		event.returnVal("정말 나가실건가요?");
	}, capture); */
	
	$(document).ready(function(){
		$("#id").keyup(function(){
			var inputLength = $(this).val().length;
			var id = $(this).val();
			var regExpId = /^[a-zA-Z0-9]{8,12}$/;
			
			if(id.indexOf(" ") != -1){
				$("#result").css("color","red");
				$("#result").html("공백이 포함된 아이디는 입력 하실 수 없습니다.");
			}else if(inputLength<8){
				$("#result").css("color","red");
				$("#result").html("8자리 이상, 12자리 미만 아이디를 입력해 주세요.");
			}else if(!regExpId.test(id)){
				$("#result").css("color","red");
				$("#result").html("영문과 숫자만 사용 가능합니다.");
			}else{
				$("#result").html("");
			}
		});
		
		$("#chk").click(function(){
			var inputLength = $("#id").val().length;
			var id = $("#id").val();
			var regExpId = /^[a-zA-Z0-9]{8,12}$/;
			if(id.indexOf(" ") != -1){
				swal("입력오류","공백이 포함된 아이디는 입력 하실 수 없습니다.");
			}else if(inputLength<8){
				swal("입력오류","8자리 이상, 12자리 미만 아이디를 입력해 주세요.");
			}else if(!regExpId.test(id)){
				swal("입력오류","영문과 숫자만 사용 가능합니다.");
			}else{
				$.ajax({ 
					url: "./idChk.do",
					data: "user_id="+$("#id").val(), 
					type: "post", 
					success: function(msg) { 
						if (msg=="false") { 
							$("#result").css("color","red");
							$("#result").html("사용불가능한 아이디 입니다.");
							$("#id").val("");
						} else { 
							$("#id").attr("readonly","readonly");
							$("#result").css("color","blue");
							$("#result").html("사용가능한 아이디 입니다.");
							$("#useId").css("display","block");
						} 
					},
					error: function(){
						swal("오류","잘못된 요청입니다.");
					}	
				});
			}
		});
		
		$("#useId").click(function(){
			var val = document.getElementById("id").value;
			opener.document.getElementById("user_id").value = val;
			/* opener는 나를 열었던 대상(부모테이블)을 이야기한다. */
			self.close();	
		});
		
	});
	
</script>
<body>
	<div id="container">
	<form>
		<table>
			<tr>
				<td>
					<input type="text" id="id" name="id" maxlength="12" placeholder="사용아이디 입력">
					<button class="btn btn-basic" type="button" id="chk">중복확인</button>
				</td>
			</tr>
			<tr>
				<td><span id="result" style="color:gray;">영문,숫자만 사용한 8~12자리</span></td>
			</tr>
			<tr>
				<td style="text-align: center;"><button class="btn btn-basic" type="button" id="useId" style="display:none;" onclick="useId()">사용</button> </td>
			</tr>
		</table>
	</form>
	</div>
	
</body>
</html>