<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 조회</title>
</head>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	function submit(n) {
		if (n == 1) { // 수정
			$("form[name=form]").attr("action", "updateForm.do").submit();

		} else if (n == 2) { // 삭제
			$("form[name=form]").attr("action", "delReq.do").submit();
		} else {
			$("form[name=form]").attr("action", "wsn.do").submit();
		}
	}
</script>
<body>
	<%@include file="/WEB-INF/views/topNav.jsp"%>
	<div class="container-fluid-full">
		<div class="row-fluid">
			<%@include file="/WEB-INF/views/sideBar.jsp"%>
			<div id="content" class="span10" style="min-height: 844px; margin: auto;">

				<%-- <%@include file="/WEB-INF/views/topMenu.jsp" %> --%>
				<div class="box">
					<h2>사업장 정보 조회</h2>
					<table class="table table-bordered">
						<tr>
							<th>사업장 이름</th>
							<td><input type="text" id="ws_name" name="ws_name"
								value="${WSDto.ws_name}" readonly="readonly"></td>
						</tr>
						<tr>
							<th>사업장 주소</th>
							<td><input type="text" id="ws_loc" name="ws_loc"
								value="${WSDto.ws_loc}" readonly="readonly"></td>
						</tr>
						<tr>
							<th>대표 전화번호</th>
							<td><input type="text" id="ws_num" name="ws_num"
								value="${WSDto.ws_num}" readonly="readonly"></td>
						</tr>
						<tr>
							<th>대표 이메일</th>
							<td><input type="text" id="ws_email" name="ws_email"
								value="${WSDto.ws_email}" readonly="readonly"></td>
						</tr>
						<tr>
							<th>WI-FI 이름</th>
							<td><input type="text" id="ws_ssid" name="ws_ssid"
								value="${WSDto.ws_ssid}" readonly="readonly"></td>
						</tr>
						<tr>
							<th>WI-FI IP</th>
							<td><input type="text" id="ws_ip" name="ws_ip"
								value="${WSDto.ws_ip}" readonly="readonly"></td>
						</tr>
						<tr>
							<th>사업장 규모</th>
							<td><input type="text" id="ws_vol" name="ws_vol"
								value="${WSDto.ws_vol}" readonly="readonly"></td>
						</tr>
					</table>
				</div>
				<form name="form" method="get">
					<input type="hidden" name="ws_code" value="${WSDto.ws_code}">
				</form>

				<div style="text-align: center">
				<c:if test="${user.user_type eq 'employer'}">
					<input class="btn btn-basic" type="button" value="수정" onclick="submit(1)"> 
					<input class="btn btn-basic" type="button" value="삭제 요청" onclick="submit(2)"> 
				</c:if>
				<input class="btn btn-basic" type="button" value="사업장 공지" onclick="submit(3)">
				</div>

			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>