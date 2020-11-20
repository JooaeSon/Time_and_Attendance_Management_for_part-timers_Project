<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://cdn.ckeditor.com/ckeditor5/19.1.1/classic/ckeditor.js"></script>
<script type="text/javascript">
function insertBaord(){
	var form = document.getElementById('insertForm');
	form.action="insertWnBoard.do";
	form.submit();
}
</script>
<style>
.ck-editor__editable_inline {
    min-height: 400px;
}
</style>

<body>
	<%@include file="/WEB-INF/views/topNavA.jsp"%>
<div class="container-fluid-full">
	<div class="row-fluid">
<%@include file="/WEB-INF/views/sideBarA.jsp" %>
<div id="content" class="span10" style="min-height: 844px; margin: auto;"> 
<form id="insertForm" method="post" enctype="multipart/form-data">
	<table class="table">
		<tbody>
			<tr>
				<th>제목</th>
				<td><input type="text" id="wn_title" name="wn_title"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="wn_content" id="editor">내용을 입력하세요.</textarea> <script>
				ClassicEditor
					 .create( document.querySelector( '#editor' ), {
						removePlugins: [ 'ImageUpload' ],
						height : 500
					 } )
				     .catch( error => {
   				console.error( error );
				} );
				</script></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="file" id="board_file" name="board_file" multiple="multiple"></td>
			</tr>
		</tbody>
	</table>
</form>
<button onclick="insertBaord()">입력</button>
<button onclick="resetForm()">초기화</button>
</div>
</div>
</div>
</body>
</html>