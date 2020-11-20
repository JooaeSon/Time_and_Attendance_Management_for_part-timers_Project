/// ---------------------------- 글 수정 ----------------------------
//출근하기
function startModify(val){
  // alert();
   startAjaxModify(val);
   $("#modify").modal();
}

var startAjaxModify = function(val){
   //alert('아작스 동작'+val);
   $.ajax({
      url:"./modifyForm.do",
      type:"post",
      dataType:"json",
      data:{"seq":val},
      success:function(msg){
      html =  "<div class='form-group'>";
      html += "<input type='hidden' name='seq' value='"+msg.seq+"'>";
      html += "<p class='form-control'><strong>"+"출근시간 바꾸기"+"</strong></p>" ;
      html += "<label for='start'>바꿀 출근시간 입력</label>";
      html += "<input type='text' class='form-control' name='startDay' value='"+msg.startDay+"' required='required'>";
      html += "</div>";
      html += "";  
      html +="<div class='modal-footer'>";
      html +="<input class='btn btn-success' type='button' value='출근 수정 완료' onclick='startUpdate()'>";
      html +="<input class='btn btn-info' type='reset' value='내용 초기화'>";
      html +="<button type='button' class='btn btn-default' data-dismiss='modal'>닫기</button>";
      html +="</div>";
      
      $("#frmModify").html(html);
      },
      error:function(){
         alert("잘못된 요청입니다.");
      }
   });
}

//퇴근하기
function endModify(val){
	  // alert();
	   endAjaxModify(val);
	   $("#modify").modal();
	}

	var endAjaxModify = function(val){
	   //alert('아작스 동작'+val);
	   $.ajax({
	      url:"./modifyForm.do",
	      type:"post",
	      dataType:"json",
	      data:{"seq":val},
	      success:function(msg){
	      html =  "<div class='form-group'>";
	      html += "<p class='form-control'><strong>"+"퇴근시간 바꾸기"+"</strong></p>" ;
	      html += "<label for='start'>바꿀 퇴근시간 입력</label>";
	      html += "<input type='text' class='form-control' name='endDay' value='"+msg.endDay+"' required='required'>";
	      html += "</div>";
	      html += "";  
	      html +="<div class='modal-footer'>";
	      html +="<input class='btn btn-danger' type='button' value='퇴근 수정 완료' onclick='endUpdate('"+msg.seq+"')'>";
	      html +="<input class='btn btn-warning' type='reset' value='내용 초기화'>";
	      html +="<button type='button' class='btn btn-default' data-dismiss='modal'>닫기</button>";
	      html +="</div>";
	      
	      $("#frmModify").html(html);
	      },
	      error:function(){
	         alert("잘못된 요청입니다.");
	      }
	   });
	}
//-----------수정완료
//출근
function startUpdate(){
   var frm = document.getElementById("frmModify");
   frm.action = "./modifyfinish.do";
   var startDay = $("#startDay").val();
   if(startDay == ''){
      swal("글 수정 오류", '제목은 필수 값 입니다.');
   }else{
      frm.submit();
   }
}
//퇴근
function endUpdate(seq){
	   var frm = document.getElementById("frmModify");
	   frm.action = "./modifyfinish.do";
	   var endDay = $("#endDay").val();
	   if(endDay == ''){
	      swal("글 수정 오류", '제목은 필수 값 입니다.');
	   }else{
	      frm.submit();
	   }
	}

//---------------------------- 전체 체크 ----------------------------
function checkAll(bool){
   var chkVals = document.getElementsByName("chkVal");
   for (var i = 0; i < chkVals.length; i++) {
      chkVals[i].checked = bool;
   }
}