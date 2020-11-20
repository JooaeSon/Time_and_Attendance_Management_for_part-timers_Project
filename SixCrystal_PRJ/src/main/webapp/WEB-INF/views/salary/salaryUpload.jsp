<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
	
    function doExcelUploadProcess(){
        var file = new FormData(document.getElementById('form1'));
        $.ajax({
            url: "./salaryMonthPayUpLoad.do",
            data: file,
            processData: false,
            contentType: false,
            type: "POST",
            success: function(msg){
//                 alert(msg);
                document.getElementById('result').innerHTML = JSON.stringify(msg);
                },
            error : function(){
            	alert("잘못된 요청입니다");
            }
            
        })
    }
    
//     function doExcelDownloadProcess(){
//         var f = document.form1;
//         f.action = "boardExdown.do";
//         f.submit();
//     }
</script>

<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
    <input type="file" id="file" name="file">
    <button type="button" onclick="doExcelUploadProcess()">엑셀업로드</button>
</form>
<div id="result"></div>
         </div>
      </div>
   </div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>