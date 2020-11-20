<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">

window.onload=function(){
	var astart =document.getElementById("apply_start");
	var aend = document.getElementById("apply_end");
	var wstart =document.getElementById("work_start");
	var wend =document.getElementById("work_end");
	var tstart =document.getElementById("t_start");
	var tend =document.getElementById("t_end");
// 	if(astart>)
	
	
}



function chkinput(){
	if(){
		
	}
	var astart =document.getElementById("apply_start");
	var aend = document.getElementById("apply_end");
	alert(aend-astart);
	var wstart =document.getElementById("work_start");
	var wend =document.getElementById("work_end");
	var tstart =document.getElementById("t_start");
	var tend =document.getElementById("t_end");
// 	alert(astart.value);
	if (astart.value>aend.value){
		alert("시작일 보다 종료일이 빠릅니다. 다시 입력해주세요");
		return false;
	}
	if(tstart.value>tend.value){
		alert("시작시간보다 종료시간이 빠릅니다. 다시 입력해주세요");
		return false;
	}
	
	
}

function insert(tlen){
// 	alert(tlen);
// 	if(tlen>2){
// 		alert("더이상 입력하실 수 없습니다.");
// 		return false;
	
// 	}else{
	
	
	chkinput();
	var tform = document.getElementById("basicform");
	tform.action="insertSchBasic.do"
	tform.submit();
// 	}
}

function selectSchBasic(){
	
	var list =document.getElementById('selbasic');
	var sel = list.options[list.selectedIndex].value;
	ajaxLoad(sel);
}

//state
function ajaxLoad(val) {
	var url = "./selectBasic.do";
// 	alert(url);	
	var wscode="200604rlghd153";
	var record=val;
	var queryString ="record="+record+"&wscode="+wscode;
	
	httpRequest = new XMLHttpRequest(); // 서버와 통신
	httpRequest.onreadystatechange = callback;// 처리할 함수
	alert(httpRequest.onreadystatechange);	// 상태번호 0 1 2 3 4 5
	httpRequest.open("POST",url,true);// 처리방식,url,비동기(true) 여부 // insert
										// delete update는 동기식을 처리하는것이 좋다 안하면 꼬이게
										// 된다
	httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');//자바스크립트의 객체를 보내려면 사용해야한다.							
	httpRequest.send(queryString); // GET:send() / POST:send(queryString)
	
}

// callback
function callback() {
// 	alert("readyState:"+httpRequest.readyState);
	if(httpRequest.readyState == 4){
// 		alert("status :"+httpRequest.status);
		if (httpRequest.status==200) {
// 			alert("성공적으로 수신 되었습니다.");
// 			alert(httpRequest.responseText);
			var obj = JSON.parse(httpRequest.responseText);
			var seq = obj.bseq;
			alert(obj.json.BASIC.APPLY_START);
			var apply_start = document.getElementById('apply_start');
			apply_start.value=obj.json.BASIC.APPLY_START;
			var apply_end = document.getElementById('apply_end');
			apply_end.value=obj.json.BASIC.APPLY_END;
			var work_start = document.getElementById('work_start');
			work_start.value=obj.json.BASIC.WORK_START;
			var work_end = document.getElementById('work_end');
			work_end.value=obj.json.BASIC.WORK_END;
			var modifyBtn = document.getElementById('modifyBtn');
			modifyBtn.type="button";
			modifyBtn.addEventListener('click',function(event){
				modify(seq);
			});
// 			modifyBtn.onclick="modify("+obj.bseq+")";
			var insertBtn = document.getElementById('insertBtn');
			insertBtn.type="hidden";
			
// 			alert(seq);
// 			httpRequest.setParameter("seq",seq);
		}else{
			alert("데이터를 수신할 수 없습니다.");
		}
	
	}// readyState가 4이면 Complite
}

function modify(seq){
	alert(seq);
	chkinput();
	var tform = document.getElementById("basicform");
	tform.action="modifySchBasic.do"
	tform.submit();
}


</script>
<body>
<form id="basicform" method="post" >
<table>
<tr>
	<th>신청시작일</th>
	<td><input id="apply_start" type="date" name="apply_start" required="required"></td>
</tr>
<tr>
	<th>신청종료일</th>
	<td><input id="apply_end" type="date" name="apply_end" required="required"></td>
</tr>
<tr>
	<th>근무시작일</th>
	<td><input id="work_start" type="date" name="work_start" required="required"></td>
</tr>
<tr>
	<th>근무종료일</th>
	<td><input id="work_end" type="date" name="work_end" required="required"></td>
</tr>
<tr>
	<th>요일(평일)</th>
	<td>
	월<input id="MON" type="checkbox" name="day" value="MON">
	화<input id="TUE" type="checkbox" name="day" value="TUE">
	수<input id="WEN" type="checkbox" name="day" value="WEN">
	목<input id="THU" type="checkbox" name="day" value="THU">
	금<input id="FRI" type="checkbox" name="day" value="FRI">
	</td>
	
</tr>
<tr>
	<th>근무시작시간</th>
	<td><input id="tn_start" type="text" name="tn_start" required="required"></td>
</tr>
<tr>
	<th>근무종료시간</th>
	<td><input id="tn_end" type="text" name="tn_end" required="required"></td>
</tr>
<tr>
	<th>인원수 제한</th>
	<td><input id="n_num_of_member" type="text" name="n_num_of_member" required="required"><br></td>
</tr>
<tr>
	<th>요일(주말)</th>
	<td>
	토<input id="SAT" type="checkbox" name="wday" value="SAT">
	일<input id="SUN" type="checkbox" name="wday" value="SUN">
	</td>
</tr>
<tr>
	<th>근무시작시간</th>
	<td><input id="tw_start" type="text" name="tw_start" required="required"></td>
</tr>
<tr>
	<th>근무종료시간</th>
	<td><input id="tw_end" type="text" name="tw_end" required="required"></td>
</tr>
<tr>
	<th>인원수 제한</th>
	<td><input id="w_num_of_member" type="text" name="w_num_of_member" required="required"></td>
</tr>
</table>
</form>
<input id="insertBtn" type="button" value="입력" onclick="insert(${fn:length(tlist)})">
<input id="modifyBtn" type="hidden" value="수정" >
<input type="button" value="초기화">	
<select id="selbasic">
<c:forEach items="${blist}" var="i" varStatus="is" >
	<option value="${i}">${is.count}:${i}</option>
	</c:forEach>
</select>
<button onclick="selectSchBasic()">불러오기</button>
</body>
</html>