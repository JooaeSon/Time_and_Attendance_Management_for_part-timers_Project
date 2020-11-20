<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사업장 등록(정보 입력)</title>
<style type="text/css">
.sidebar-nav{
		display: none;
	}
</style>
</head>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
$(function() {
	// input 태그 안에 값을 입력할 때 유효성 검사
	$("#ws_name").keyup(function() {
		// 		alert("ws_name");
		if ($("#ws_name").val() == "") {
			$(this).next().text('사업장 명을 입력하세요').css({"color":"red"});
		} else {
			$(this).next().text('');
		}
	});

	$("#ws_loc").keyup(function() {
		// 		alert("ws_loc");
		if ($("#ws_loc").val() == "") {
			$(this).next().text('사업장 위치를 입력하세요').css({"color":"red"});
		} else {
			$(this).next().text('');
		}
	});

	$("#ws_num").keyup(function() {
		// 		alert("ws_num");
		var regexp = /^[0-9]*$/;
		if ($("#ws_num").val() == "") {
			$(this).next().text('사업장 대표번호를 입력하세요').css({"color":"red"});
		} else if(!regexp.test($("#ws_num").val())) {
			$(this).next().text('숫자만 입력하세요').css({"color":"red"});
			$(this).val('');
		} else if($(this).val().length>8) {
			$(this).next().text('최대 8자리').css({"color":"red"});
			$(this).val('');
		} else {
			$(this).next().text('');
		}
		
	});

	$("#ws_email").keyup(function() {
		// 		alert("ws_email");
		if ($("#ws_email").val() == "") {
			$(this).next().text('사업장 대표이메일을 입력하세요').css({"color":"red"});
		} else {
			$(this).next().text('');
		}
	});

	$("#ws_ssid").keyup(function() {
		// 		alert("ws_ssid");
		if ($("#ws_ssid").val() == "") {
			$(this).next().text('사업장 와이파이 이름을 입력하세요').css({"color":"red"});
		} else {
			$(this).next().text('');
		}
	});

	$("#ws_ip").keyup(function() {
		// 		alert("ws_ip");
		var regexp = /^[0-9]*$/;
		if ($("#ws_ip").val() == "") {
			$(this).next().text('사업장 와이파이 ip를 입력하세요').css({"color":"red"});
		} else if(!regexp.test($("#ws_ip").val())) {
			$(this).next().text('숫자만 입력하세요').css({"color":"red"});
			$(this).val('');
		} else if($(this).val().length != 10) {
			$(this).next().text('숫자 10자리를 입력하세요').css({"color":"red"});
		} else {
			$(this).next().text('');
		}

	});

	$("#ws_vol").keyup(function() {
		// 		alert("ws_vol");
		var regexp = /^[0-9]*$/;
		if ($("#ws_vol").val() == "") {
			$(this).next().text('사업장 상시근로자수를 입력하세요').css({"color":"red"});
		} else if(!regexp.test($("#ws_vol").val())) {
			$(this).next().text('숫자만 입력하세요').css({"color":"red"});
			$(this).val('');
		} else {
			$(this).next().text('');
		}

		
		if (!regexp.test($("#ws_vol").val())) {
			alert("숫자만 입력하세요");
		}
	});

	//submit 할 때 유효성 검사: 공백, 각각 유효성
	$("#submit").click(function() {
		$("#insertForm").submit();
	});
	
});

function goPopup() {
	var pop = window.open("./jusoPopup.do", "pop",
			"width=570,height=420, scrollbars=yes, resizable=yes");
}

/** API 서비스 제공항목 확대 (2017.02) **/
function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail,
		roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
		detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn,
		buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
	// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
	// document.form.너가주소입력할input의아이디.value = roadFullAddr;
	$("#ws_loc").val(roadFullAddr);
}
</script>
<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
	<div class="row-fluid">
		<%@include file="/WEB-INF/views/sideBar.jsp" %>
		<div id="content" class="span10" style="min-height: 844px; margin: auto;"> 
			<form action="./insert.do" method="post" id="insertForm">
				<input type="hidden" name="user_id" value="${WSDto.user_id}">
				<input type="hidden" id="confmKey" name="confmKey" value="">
				<input type="hidden" id="numnum" name="ws_num" value="">
				<table class="table table-bordered">
					<tr>
						<th>사업장 이름</th>
						<td>
						<input type="text" name="ws_name" id="ws_name">
						<span></span>
						</td>
					</tr>
					<tr>
						<th>사업장 주소</th>
						<td>
						<input type="text" name="ws_loc" id="ws_loc"> 
						<span></span>
						<br>
						<input class="btn btn-basic" type="button" value="주소 검색" onclick="goPopup();" style="margin-bottom: 3px;">
						</td>
					</tr>
					<tr>
						<th>대표 전화번호</th>
						<td>
							<select name="ws_num1">
								<option value="02">02</option>
								<option value="031">031</option>
								<option value="032">032</option>
								<option value="033">033</option>
								<option value="041">041</option>
								<option value="042">042</option>
								<option value="043">043</option>
								<option value="044">044</option>
								<option value="051">051</option>
								<option value="052">052</option>
								<option value="053">053</option>
								<option value="054">054</option>
								<option value="055">055</option>
								<option value="061">061</option>
								<option value="062">062</option>
								<option value="063">063</option>
								<option value="064">064</option>
							</select> 
							<input type="text" name="ws_num2" id="ws_num" placeholder="- 를 제외한 값을 입력하세요">
							<span></span>
						</td>
					</tr>
					<tr>
						<th>대표 이메일</th>
						<td>
							<input type="email" name="ws_email" id="ws_email">
							<span></span>
						</td>
					</tr>
					<tr>
						<th>WI-FI 이름</th>
						<td>
							<input type="text" name="ws_ssid" id="ws_ssid">
							<span></span>
						</td>
					</tr>
					<tr>
						<th>WI-FI IP</th>
						<td>
							<input type="text" name="ws_ip" id="ws_ip" placeholder=". 을 제외한 값을 입력하세요">
							<span></span>
						</td>
					</tr>
					<tr>
						<th>사업장 규모</th>
						<td>
							<input type="text" name="ws_vol" id="ws_vol">
							<span></span>
						</td>
					</tr>
				</table>
			</form>
			<input type="button" value="사업장 등록" id="submit">
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>		

</body>
</html>