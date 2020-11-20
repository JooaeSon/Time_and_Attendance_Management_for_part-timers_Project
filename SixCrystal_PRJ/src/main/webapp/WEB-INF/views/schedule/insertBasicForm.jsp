<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>근무일정 기초사항 입력 페이지</title>
</head>
<script type="text/javascript" src="./js/schBasic.js"></script>

<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
<table class="table ">
		<tbody >
			<tr>
				<th>신청시작일</th>
				<td><input id="apply_start" type="date" name="apply_start"
					required="required"></td>
			</tr>
			<tr>
				<th>신청종료일</th>
				<td><input id="apply_end" type="date" name="apply_end"
					required="required"></td>
			</tr>
			<tr>
				<th>근무시작일</th>
				<td><input id="work_start" type="date" name="work_start"
					required="required"></td>
			</tr>
			<tr>
				<th>근무종료일</th>
				<td><input id="work_end" type="date" name="work_end" required="required"></td>
			</tr>
			<tr>
				<th>야간선택</th>
				<td>주간<input type="radio" name="selterm" value="S">
					야간<input type="radio" name="selterm" value="M">
				</td>
			</tr>
				<c:if test="${user.user_type eq 'employer' }">
			<tr >
				<th>등급선택</th>
				<td>매니저<input type="radio" name="ranksel" value="M">
					일반직원<input type="radio" name="ranksel" value="G">
				</td>
			</tr>
				</c:if>
				<c:if test="${user.user_type eq 'employee' }">
				<tr >
				<th>등급선택</th>
				<td>
					일반직원<input type="radio" name="ranksel" value="G" checked="checked" readonly="readonly">
				</td>
			</tr>
				</c:if>
		</tbody>
			</table>
			<input type="button" value="설정" onclick="fixdate()">
			<input type="button" value="초기화" onclick="resetdate()">
			<input type="hidden" id="fix_date" value="N">
			<input type="hidden" id="user_type" value="${user.user_type}">
			<table class="table">
			<tbody>
			<tr>
				<th>요일(평일)</th>
				<td>월<input id="MON" type="checkbox" name="day" value="Mon">
					화<input id="TUE" type="checkbox" name="day" value="Tue"> 수<input
					id="WEN" type="checkbox" name="day" value="Wen"> 목<input
					id="THU" type="checkbox" name="day" value="Thu"> 금<input
					id="FRI" type="checkbox" name="day" value="Fri"> 토<input
					id="SAT" type="checkbox" name="day" value="Sat"> 일<input
					id="SUN" type="checkbox" name="day" value="Sun">
				</td>

			</tr>
			<tr>
				<th>근무시작시간</th>
				<td>
				<select id="t_start_hour" >
					<option value="00">00</option>
					<option value="01">01</option>
					<option value="02">02</option>
					<option value="03">03</option>
					<option value="04">04</option>
					<option value="05">05</option>
					<option value="06">06</option>
					<option value="07">07</option>
					<option value="08">08</option>
					<option value="09">09</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					<option value="16">16</option>
					<option value="17">17</option>
					<option value="18">18</option>
					<option value="19">19</option>
					<option value="20">20</option>
					<option value="21">21</option>
					<option value="22">22</option>
					<option value="23">23</option>
				</select>
				시
				<select id="t_start_min">
					<option value="00">00</option>
					<option value="30">30</option>
				</select>
				분
				</td>
			</tr>
			<tr>
				<th>근무종료시간</th>
				<td>
					<select id="t_end_hour" >
					<option value="00">00</option>
					<option value="01">01</option>
					<option value="02">02</option>
					<option value="03">03</option>
					<option value="04">04</option>
					<option value="05">05</option>
					<option value="06">06</option>
					<option value="07">07</option>
					<option value="08">08</option>
					<option value="09">09</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					<option value="16">16</option>
					<option value="17">17</option>
					<option value="18">18</option>
					<option value="19">19</option>
					<option value="20">20</option>
					<option value="21">21</option>
					<option value="22">22</option>
					<option value="23">23</option>
				</select>
				시
				<select id="t_end_min">
					<option value="00">00</option>
					<option value="30">30</option>
				</select>
				분
				</td>
			</tr>
			<tr>
				<th>인원</th>
				<td><input id="num_of_member" type="text" name="num_of_member"
					required="required"> 명<br></td>
			</tr>
		</tbody>
	</table>
	<input type="button" value="기초사항추가하기" onclick="addform()">
	<form id="basicform" method="post">
		<table id="basictable">
			<thead>
				<tr>
					<th>요일</th>
					<th>근무시작시간</th>
					<th>근무종료시간</th>
					<th>인원</th>
				</tr>
			</thead>
			<tbody id="basictbody">
			<tr>
				<td>
					<input type="hidden" name="schdate" id="schdate">
					<input type="hidden" name="employee_rank" id="emprank">
				</td>
			</tr>
			</tbody>
		</table>
	</form>
	<input id="insertBtn" type="button" value="입력"
		onclick="insert(${fn:length(tlist)})">
	<input id="modifyBtn" type="hidden" value="수정">
	<input type="button" value="초기화" onclick="resetbasic()">
	<select id="selbasic">
		<c:forEach items="${blist}" var="i" varStatus="is">
			<option value="${i}">${is.count}:${i}</option>
		</c:forEach>
	</select>
	<button onclick="selectSchBasic()">불러오기</button>
         </div>
      </div>
   </div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>