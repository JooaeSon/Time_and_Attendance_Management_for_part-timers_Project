var cnt = 0;




/**
 * 입력시 유효성 처리를 한다 (시작일보다 종료일이 빠르면 안된다.)
 * @returns false or X
 */
function chkinput(){

	var astart =document.getElementById("apply_start");
	var aend = document.getElementById("apply_end");
//	alert(aend-astart);
//	alert(astart.value);
	var wstart =document.getElementById("work_start");
	var wend =document.getElementById("work_end");
// 	alert(astart.value);
	if (astart.value>aend.value||wstart.value>wend.value){
		alert("시작일 보다 종료일이 빠릅니다. 다시 입력해주세요");
		return false;
	}
	
	
	
}

/**
 * 기초사항을 총 3개까지 입력할 수 있게 제한하며 입력하는 기능
 * @param tlen
 * @returns
 */
function insert(tlen){
// 	alert(tlen);
	var remtd = document.getElementsByName('basictd');
 	if(tlen>2){
 		alert("더이상 입력하실 수 없습니다. 기존 기초사항을 수정해주세요.");
 		return false;
	
 	}else if(remtd.length>0){
	
	alert("성공");
//	return false;
	chkinput();
	var tform = document.getElementById("basicform");
	tform.action="insertSchBasic.do"
	tform.submit();
 	}else{
 		alert("기초사항을 추가하세요.");
 		return false;
 	}
}

/**
 * 
 * @returns
 */
function selectSchBasic(){
	if(document.getElementById('fix_date').value=="N"){
		alert("기간을 설정해주세요.");
		return false;
	}
	var list =document.getElementById('selbasic');
	var sel = list.options[list.selectedIndex].value;
	ajaxinsert(sel);
}

//state
/**
 * 입력한 값을 테이블 태그에 넣어주기 위한 ajax
 * @param val
 * @returns
 */
function ajaxinsert(val) {
	var url = "selectBasic.do";
// 	alert(url);	
//	var wscode="200610rlghd153";
	var record=val;
	var queryString ="record="+record;
	
	httpRequest = new XMLHttpRequest(); // 서버와 통신
	httpRequest.onreadystatechange = callbackinsert;// 처리할 함수
	alert(httpRequest.onreadystatechange);	// 상태번호 0 1 2 3 4 5
	httpRequest.open("POST",url,true);// 처리방식,url,비동기(true) 여부 // insert
										// delete update는 동기식을 처리하는것이 좋다 안하면 꼬이게
										// 된다
	httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');//자바스크립트의 객체를 보내려면 사용해야한다.							
	httpRequest.send(queryString); // GET:send() / POST:send(queryString)
	
}


// callback
function callbackinsert() {
	
// 	alert("readyState:"+httpRequest.readyState);
	if(httpRequest.readyState == 4){
// 		alert("status :"+httpRequest.status);
		if (httpRequest.status==200) {
			resetbasic();
// 			alert("성공적으로 수신 되었습니다.");
// 			alert(httpRequest.responseText);
			var obj = JSON.parse(httpRequest.responseText);
//			alert(JSON.stringify(obj.json.SCHEDUEL));
			var jobj = JSON.parse(JSON.stringify(obj.json));
//			alert(JSON.stringify(jobj.SCHEDULE));
			
			var jarr = JSON.parse(JSON.stringify(obj.json.SCHEDULE));
//			var jlength = obj.json.SCHEDUEL.length;
//			alert(jarr.size);
//			var start = jarr[0].START;
//			var end = jarr[0].END;
//			var day = "";
//			alert(jarr.length);
//			alert(jlength);
//			alert(start);
//			alert(end);
			var getday = document.getElementsByName("day");
			for (var i = 0; i < getday.length; i++) {
				for (var j = 0; j < jarr.length; j++) {
					getday[i].checked=false;
					if(getday[i].value==jarr[j].DAY){
						getday[i].disabled='true';
					}
				}
			}
			for (var i = 0; i < jarr.length; i++) {
				let ccnt = 0;
				
				var ctrform = document.createElement('tr');
				var ctdday =document.createElement('td');
				var ctstart =document.createElement('td');
				var ctend =document.createElement('td');
				var ctperson =document.createElement('td');
				var ctdel=document.createElement('td');
				var cinday = document.createElement('input');
				var cinstart = document.createElement('input');
				var cinend = document.createElement('input');
				var cinper = document.createElement('input');
				var cindel = document.createElement('input');
				
				ctrform.id="basictd"+cnt;
				ctrform.setAttribute('name','basictd');
				cinday.id="inday"+cnt;
				
				cinday.type="text";
				cinstart.type="text";
				cinend.type="text";
				cinper.type="text";
				cindel.type="button";
				
				cinday.setAttribute('name','inday');
				cinstart.setAttribute('name','instart');
				cinend.setAttribute('name','inend');
				cinper.setAttribute('name','inper');

				cinstart.value=jarr[i].START;
				cinend.value=jarr[i].END;
				cinper.value=jarr[i].NUM_OF_MEMBER;
				cinday.value=jarr[i].DAY;
				
				cindel.value="삭제";
				ccnt=cnt;
				cindel.addEventListener('click',function(event){
					
					tddel(ccnt);
				})
				
				cinday.readOnly="true";
				cinstart.readOnly="true";
				cinend.readOnly="true";
				cinper.readOnly="true";
				ctdday.appendChild(cinday);
				ctstart.appendChild(cinstart);
				ctend.appendChild(cinend);
				ctperson.appendChild(cinper);
				ctdel.appendChild(cindel);
				ctrform.appendChild(ctdday);
				ctrform.appendChild(ctstart);
				ctrform.appendChild(ctend);
				ctrform.appendChild(ctperson);
				ctrform.appendChild(ctdel);
				document.getElementById('basictbody').appendChild(ctrform);
				cnt++;
				
			}
			
			var seq = obj.bseq;
			alert(obj.json.BASIC.APPLY_START);
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

/**
 * 불러온 일정기초사항을 수정하는 기능
 * @param cseq
 * @returns
 */
function modify(cseq){
	alert(cseq);
	chkinput();
	
	var tform = document.getElementById("basicform");
	var inseq = document.createElement('input');
	inseq.setAttribute('name','seq');
	inseq.value=cseq;
	inseq.type="hidden";
	tform.appendChild(inseq);
	tform.action="modifySchBasic.do"
	tform.submit();
}

/**
 * 테이블의 td태그로 입력했던 값을 넣는다.
 * @returns
 */
function addform(){
	if(document.getElementById('fix_date').value=="N"){
		alert("기간을 설정해주세요.");
		return false;
	}
	
//	if(cnt>7){
//		alert("더이상 추가하실 수 없습니다.");
//		return false;
//	}
	cnt++;
	var getday = document.getElementsByName("day");
	
	var getshour = document.getElementById('t_start_hour').options[document.getElementById('t_start_hour').selectedIndex].value;
	var getsmin = document.getElementById('t_start_min').options[document.getElementById('t_start_min').selectedIndex].value;
	var getehour = document.getElementById('t_end_hour').options[document.getElementById('t_end_hour').selectedIndex].value;
	var getemin = document.getElementById('t_end_min').options[document.getElementById('t_end_min').selectedIndex].value;
	alert(document.getElementById('schdate').value);
	if(JSON.parse(document.getElementById('schdate').value).SEL_TERM=="S"){
		if(getshour>=getehour&&getehour!="00"){
			alert("주간근무입니다. 근무시간을 다시 설정해주세요.");
			cnt--;
			return false;
		}
	}
	var getperson = document.getElementById('num_of_member').value;
	if(!is_number(getperson)){
		return false;
	}
	var trform = document.createElement('tr');
	var tdday =document.createElement('td');
	var tstart =document.createElement('td');
	var tend =document.createElement('td');
	var tperson =document.createElement('td');
	var tdel=document.createElement('td');
	var inday = document.createElement('input');
	var instart = document.createElement('input');
	var inend = document.createElement('input');
	var inper = document.createElement('input');
	var indel = document.createElement('input');
	
	trform.id="basictd"+cnt;
	trform.setAttribute('name','basictd');
	inday.id="inday"+cnt;
	
	inday.type="text";
	instart.type="text";
	inend.type="text";
	inper.type="text";
	indel.type="button";
	
	inday.setAttribute('name','inday');
	instart.setAttribute('name','instart');
	inend.setAttribute('name','inend');
	inper.setAttribute('name','inper');
	
	var chkcnt = 0; 
	chkcnt = cnt;
	indel.value="삭제";
	indel.addEventListener('click',function(event){
		tddel(chkcnt);
	})
	
	inday.readOnly="true";
	instart.readOnly="true";
	inend.readOnly="true";
	inper.readOnly="true";
	
	instart.value= getshour+":"+getsmin;
	inend.value = getehour+":"+getemin;
	inper.value = getperson;
	var txtday ="";
	for (var i = 0; i < getday.length; i++) {
		if(getday[i].checked){
			
		if(txtday!=""){
			txtday +="/";
		}
		
		txtday+=getday[i].value;
			
		}
		
	}
	if (txtday=="") {
		alert("요일을 입력하세요");
		cnt--;
		return false;
	}else if(getperson==""||getperson==null){
		alert("인원을 입력하세요");
		cnt--;
		return false;
	}
	for (var i = 0; i < getday.length; i++) {
		if(getday[i].checked){
				getday[i].checked=false;
				getday[i].disabled="true";
		}
		
	}
	inday.value=txtday;
	tdday.appendChild(inday);
	tstart.appendChild(instart);
	tend.appendChild(inend);
	tperson.appendChild(inper);
	tdel.appendChild(indel);
	trform.appendChild(tdday);
	trform.appendChild(tstart);
	trform.appendChild(tend);
	trform.appendChild(tperson);
	trform.appendChild(tdel);
	document.getElementById('basictbody').appendChild(trform);
	
}

/**
 * 근무신청기간 및 근무기간을 고정시킨다
 * @returns
 */
function fixdate(){
	var apply_start = document.getElementById('apply_start');
	var apply_end = document.getElementById('apply_end');
	var work_start = document.getElementById('work_start');
	var work_end = document.getElementById('work_end');
	var term = document.getElementsByName('selterm');
	var ranks = document.getElementsByName('ranksel');
	
	var selterm = "";
	var nowDate = new Date();
	var selrank ="";
	chkinput();
//	alert(nowDate.toISOString());

	for (var i = 0; i < term.length; i++) {
		if (term[i].checked) {
			selterm = term[i].value;
		}
	}
	for (var j = 0; j<ranks.length; j++){
		if (ranks[j].checked) {
			selrank = ranks[j].value;
		}
	}
	
//	alert(apply_start);
	if(apply_start.value==""||apply_end.value==""||work_start.value==""||work_end.value==""||selterm ==""){
		alert("값을 전부 입력하여 주세요.");
		return false;
	}else if(nowDate.toISOString()>=apply_start.value||nowDate.toISOString()>apply_end.value||nowDate.toISOString()>work_start.value||nowDate.toISOString()>work_end.value){
		alert("오늘 이전에 날짜는 입력할 수 없습니다.");
		return false;
	}else{
		apply_start.disabled="true";
		apply_end.disabled="true";
		work_start.disabled="true";
		work_end.disabled="true";
		for (var i = 0; i < term.length; i++) {
			term[i].disabled="true";
		}
		for (var j = 0; j<ranks.length; j++){
			ranks[j].disabled="true";
		}
	}
	document.getElementById('fix_date').value="Y";
	var djson = {
			"APPLY_START":apply_start.value,
			"APPLY_END":apply_end.value,
			"WORK_START":work_start.value,
			"WORK_END":work_end.value,
			"SEL_TERM":selterm
			
	}
//	alert(djson);
	document.getElementById('schdate').value=JSON.stringify(djson);
	document.getElementById('emprank').value=selrank;
	
		
	
}

function resetdate(){
	var apply_start = document.getElementById('apply_start');
	var apply_end = document.getElementById('apply_end');
	var work_start = document.getElementById('work_start');
	var work_end = document.getElementById('work_end');
	var term = document.getElementsByName('selterm');
	apply_start.removeAttribute('disabled');
	apply_end.removeAttribute('disabled');
	work_start.removeAttribute('disabled');
	work_end.removeAttribute('disabled');
	for (var i = 0; i < term.length; i++) {
		term[i].removeAttribute('disabled');
	}
	apply_start.value="";
	apply_end.value="";
	work_start.value="";
	work_end.value="";
	document.getElementById('fix_date').value="N";
	document.getElementById('schdate').value="";


}

/**
 * 테이블에 td태그로 들어간 글을 지우는 기능
 * @param tdcnt
 * @returns
 */
function tddel(tdcnt){
	alert(tdcnt);
	var resetday= document.getElementById('inday'+tdcnt);
	var days = resetday.value.split('/');
	var getday = document.getElementsByName("day");
	for (var i = 0; i < getday.length; i++) {
		for (var j = 0; j < days.length; j++) {
			
			if(getday[i].value==days[j]){
				getday[i].removeAttribute('disabled');
			}
		}
	}
	var child = document.getElementById('basictd'+tdcnt);
	child.parentNode.removeChild(child);
}

/**
 * 입력했던 값들을 초기화 시키는 기능
 * @returns
 */
function resetbasic(){
	alert("동작");	
	var remtd = document.getElementsByName('basictd');
	var retbody = document.getElementById('basictbody');
	var firstlength = remtd.length;
	var getday = document.getElementsByName("day");
	for (var i = 0; i < firstlength; i++) {
		retbody.removeChild(remtd[0]);
	}
	for (var j = 0; j < getday.length; j++) {
		getday[j].removeAttribute('disabled');
	}
	var insertBtn = document.getElementById('insertBtn');
	insertBtn.type="button";
	var modifyBtn = document.getElementById('modifyBtn');
	modifyBtn.type="hidden";
	
	
	
}

/**
 * 인원수 숫자만 입력할 수 있도록
 * @param x
 * @returns true/ false
 */
function is_number(x){

    var reg = /^\d+$/;
    alert(reg.test(x));
    return reg.test(x);

}




