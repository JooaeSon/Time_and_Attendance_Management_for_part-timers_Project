window.onload=function(){
	var auth = document.getElementById('auth').value;
	if (auth=="admin") {
		var modify = document.createElement('input');
		var del = document.createElement('input');
		var wn_seq = document.getElementById('wn_seq').value;
		modify.type = "button";
		del.type = "button";
		
		modify.value="수정";
		del.value="삭제";
		
		modify.setAttribute('class','btn btn-info');
		del.setAttribute('class','btn btn-danger')
		
		modify.addEventListener('click',function(event){
			wnmodify(wn_seq)
		})
		del.addEventListener('click',function(event){
			del(wn_seq)
		})
		document.getElementById('btnarea').appendChild(modify);
		document.getElementById('btnarea').appendChild(del);
	}
	
	var back = document.createElement('input');
	back.value="목록가기";
	back.setAttribute('class','btn btn-basic');
	back.addEventListener('click',function(event){
		location.replace("./wnBoardList.do");
	})
	document.getElementById('btnarea').appendChild(back);
	
}

function wnmodify(val){
	 ajaxModify(val);
	 $("#modify").modal();
}

var ajaxModify = function(val) {
//	 alert(val);
	$.ajax({
		url : "./adminMWnForm.do",
		type : "post",
		data : {"wn_seq":val},
		dataType :"json",
		success : function(msg) {
//			alert(msg);
			$.each(msg,function(k,v){
//				$.each(k,function(key,value){
//					alert(value);
//				});
				
			
//			alert(k);
			if(k=="dto"){
			var sdiv= $('<div>');
				sdiv.attr('class','form-group');
				
			var wn_seqin = $('<input>');
				wn_seqin.attr('name','wn_seq').attr('type','hidden').attr('value',v.wn_seq);
				
			var slabel = $('<label>')
				slabel.attr('for','id').text('아이디');
			
			var sp = $('<p>');
				sp.attr('class','form-control');
				
			var sstrong = $('<strong>').text(v.user_id);
			
			sp.append(sstrong);
			sdiv.append(wn_seqin).append(slabel).append(sp);

			var rdiv= $('<div>');
				rdiv.attr('class','form-group');
			
			var rlabel = $('<label>')
				rlabel.attr('for','id').text('작성일');
		
			var rp = $('<p>');
				rp.attr('class','form-control');
			
			var rstrong = $('<strong>').text(v.wn_regdate);
			
			rp.append(rstrong);
			rdiv.append(rlabel).append(rp);
			
			/////
			
			var tdiv= $('<div>');
				tdiv.attr('class','form-group');
				
			var tlabel = $('<label>')
				tlabel.attr('for','wn_title').text('제목');
		
			var wn_titlein = $('<input>');
				wn_titlein.attr('class','form-control').attr('name','wn_title').attr('type','text').attr('value',v.wn_title).attr('required','required').attr('id','wn_title');
		
			
			tdiv.append(tlabel).append(wn_titlein);
			
			////////

				
			var cdiv= $('<div>');
				cdiv.attr('class','form-group');
				
			var clabel = $('<label>');
				clabel.attr('for','wn_content').text('내용');
			
			var ctext = $('<textarea>');
				ctext.attr('class','form-control').attr('id','wn_content').attr('name','wn_content').attr('row','5').text(v.wn_content);
		
			var ckedit ="ClassicEditor"
				 +".create( document.querySelector( '#wn_content' ), {"
				+"	removePlugins: [ 'ImageUpload' ]"
				 +"} )"
			     +".catch( error => {"
				+"console.error( error );"
			+"} );";
			
			var sc = $('<script>');
			sc.text(ckedit);
			
			cdiv.append(clabel).append(ctext).append(sc);

			
//			var fdiv = $('<div>');
//				fdiv.attr('class', 'form-group');
//				
//			var flabel =$('<label>');
//				flabel.attr('for','file_list');

			var mfdiv= $('<div>');
				mfdiv.attr('class','modal-footer');
				
			var mdbtn = $('<input>');
				mdbtn.attr('type','button').attr('class','btn btn-warning').attr('value','수정 완료').attr('onclick','update()');
			
				
			var rdbtn = $('<input>');
				rdbtn.attr('type','reset').attr('class','btn btn-info').attr('value','내용 초기화');
				
			var clbtn = $('<button>');
				clbtn.attr('class','btn btn-default').attr('data-dismiss','modal').text('Close');

			mfdiv.append(mdbtn).append(rdbtn).append(clbtn);	
			}
			if(k=="flist"){
				$.each(v,function(key,value){
//					value.bf_filename
					var tdiv= $('<div>');
						tdiv.attr('class','form-group');
					
					var tlabel = $('<label>')
						tlabel.attr('for','wn_title').text('제목');
			
					var wn_titlein = $('<input>');
						wn_titlein.attr('class','form-control').attr('name','wn_title').attr('type','text').attr('value',v.wn_title).attr('required','required').attr('id','wn_title');
					
				});
			}
			var total=$('<div>');
			
			total.append(sdiv).append(tdiv).append(cdiv).append(rdiv).append(mfdiv);
			
			$('#frmModify').html(total);	
			});
		},
		error : function() {
			alert("잘못된 요청입니다.");
		},
	});
}

function update(){
	var frm = document.getElementById("frmModify");
	frm.action = "./modify.do";
	var title = $("#title").val();
	if (title == '') {
		swal("글수정 오류", '제목은 필수 입니다.');
		
	}else{
		frm.submit();
	}
}

function del(seq){
	location.href="adminDelWnBoard.do?wn_seq="+seq;
	
}