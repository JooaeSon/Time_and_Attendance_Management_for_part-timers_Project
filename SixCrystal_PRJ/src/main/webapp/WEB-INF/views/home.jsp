<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<script type="text/javascript">
function insertBasic(){
	location.href = "insertBasicForm.do";
}
function wnBoard(){
	location.href="uesrWnBoard.do";
}
function adminwnBoard(){
	location.href="adminWnBoard.do";
}

</script>
<button onclick="insertBasic()" >근무일정 기초사항 입력</button>
<button onclick="wnBoard()" >웹 공지 게시판으로 이동</button>
<button onclick="adminwnBoard()" >웹 공지 게시판으로 이동</button>
</body>
</html>
