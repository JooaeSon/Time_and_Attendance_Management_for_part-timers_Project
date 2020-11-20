window.onload=function(){
	var adiv = document.getElementsByClassName('anchk');
// 	var lists ='${lists}';
	let auth = document.getElementById('auth');
	//웹 관리자일시 글작성 버튼을 생성한다.
	if(auth.value=="admin"){
		var btnin = document.createElement('input');
		btnin.type='button';
		btnin.value='글작성';
		btnin.addEventListener('click',function(event){
			wninsert()
		})
		btnin.setAttribute('class','btn btn-info');
		var btnstage = document.getElementById('btnstage');
		btnstage.appendChild(btnin);
	}
}
//글작성 버튼을 누르면 이동
function wninsert(){
	location.href="adminWnWrite.do";
} 

//상세글보기로 들어갈 시 웹 관리자와 일반유저를 분기시킨다.
function wndetail(seq){
	let auth = document.getElementById('auth');
	if (auth.value=="admin") {
		location.href="adminWnBoardDetail.do?wn_seq="+seq;
	}else{
		location.href="userWnBoardDetail.do?wn_seq="+seq;
	}
}

//페이징 -처음페이지로 이동
function pageFirst(){
//	alert("작동");
	var index = document.getElementById("index");
	var pageNum = document.getElementById("pageNum");
	index.value=0;
	pageNum=1;
	pageAjax();
}

//페이징 - 이전페이지로 이동
function pagePre(num, pageList){
//	alert(pageNum+":"+pageList);
	if (0<num-pageList) {
		num-=pageList;
		var index = document.getElementById("index");
		var pageNum = document.getElementById("pageNum");
		pageNum.value = num;
		index.value = num-1;
	}
	pageAjax();
	
}

//페이징 - 다음페이지로 이동
function pageNext(num, total, listNum, pageList){
//	alert(num);
//	alert(total);
//	alert(listNum);
//	alert(pageList);
	var index = Math.ceil(total/listNum);
	var max = Math.ceil(index/pageList);
	
	if (max*pageList > num+pageList) {
		num += pageList;
		
		var index = document.getElementById("index");
		var pageNum = document.getElementById("pageNum");
		
		pageNum.value = num;
		index.value = num+1;
		
	}
	
	pageAjax();
	
}

//페이징 - 5페이지 이동
function pageLast(num, total, listNum, pageList){
	var idx = Math.ceil(total/listNum);
	var max = Math.ceil(idx/pageList);
	
	while(max*pageList > num+pageList){
		num += pageList;
	}
	
	var index = document.getElementById("index");
	var pageNum = document.getElementById("pageNum");
	
	pageNum.value = num;
	index.value = idx-1;
	
	pageAjax();
	
	
}

//인덱스를 통한 해당 페이지 이동
function pageIndex(idx){
	alert(idx);
	var index = document.getElementById('index');
	index.value = idx-1;
	pageAjax();
}

//아작스로 페이징 처리 
var pageAjax = function() {
//	alert("아작아작");

	$.ajax({
		url:"./paging.do",
		type : "get",
		async : true,
		data : $("#frm").serialize(), //key=value
		dataType : "json",
		contentType : "application/json",
		success:function(msg){
//			alert(msg.lists[1].title);
//			alert(msg.page.listNum);
			$('.table').empty();
			$.each(msg,function(key,value){ //lists,{"",[]} row,{}

				
				
				if(key=="lists")	{
					var thead = $('<thead>');
					var thtr = $('<tr>');
					var thth_num = $('<th>');
					var thth_title = $('<th>');
					var thth_hit = $('<th>');
					var thth_id = $('<th>');
					var thth_reg= $('<th>');
					thth_num.text('번호');
					thth_title.text('제목');
					thth_hit.text('조회수');
					thth_id.text('작성자');
					thth_reg.text('작성일');
					thtr.append(thth_num).append(thth_title).append(thth_hit).append(thth_id).append(thth_reg);
					thead.append(thtr);
					
					var tbody = $('<tbody>');
					//[{dto},{dto}]
					$.each(value,function(k,v){
//						alert(k);
//						alert(v.wn_seq);
//						alert(v.wn_title);
//						alert(v.wn_regdate);
						
//						console(v.wn_seq);
						var tbtr = $('<tr>');
						var tbtd_num = $('<td>');
						var tbtd_title= $('<td>');
						var tbtd_hit=$('<td>');
						var tbtd_id =$('<td>');
						var tbtd_reg =$('<td>');
						
						var an = $('<a>');
						an.attr('onclick','wndetail('+v.wn_seq+')');
						an.text(v.wn_title);
						
						tbtd_num.text(v.wn_seq);
						tbtd_hit.text(v.wn_hit);
						tbtd_id.text('관리자');
						tbtd_reg.text(v.wn_regdate);
						
						tbtd_title.append(an);
						
						tbtr.append(tbtd_num).append(tbtd_title).append(tbtd_hit).append(tbtd_id).append(tbtd_reg);
//						alert('붙었다');
					
						tbody.append(tbtr);
						$('.table').append(tbody);
					});
				}
				else{//페이징 값 변경
					
					var pagestage = $('#pagestage');
					pagestage.empty();
					var pagefirst = $('<button>');
					pagefirst.attr('onclick','pageFirst()');
					pagefirst.html('&laquo;');
					pagestage.append(pagefirst);
					var pagepre = $('<button>');
					pagepre.attr('onclick','pagePre('+value.pageNum+','+value.pageList+')');
					pagepre.html('&lsaquo;');
					pagestage.append(pagepre);
					
					for(var i = value.pageNum; i<= value.count; i++){				
						var an = $('<a>');
						an.attr('onclick','pageIndex('+i+')');
						an.html(i);
						pagestage.append(an);
					}
					
					var pagenext = $('<button>');
					pagenext.attr('onclick','pageNext('+value.pageNum+','+value.total+','+value.listNum+','+value.pageList+')');
					pagenext.html('&rsaquo;');
					pagestage.append(pagenext);
					var pagelast = $('<button>');
					pagelast.attr('onclick','pageLast('+value.pageNum+','+value.total+','+value.listNum+','+value.pageList+')');
					pagelast.html('&raquo;');
					pagestage.append(pagelast);
					
					
				
				}
				
				
				$('.table').append(thead)
			});
			
			
		},
		error : function(){
			alert("데이터 처리를 하지 못했습니다");
		}
	});
	
}

