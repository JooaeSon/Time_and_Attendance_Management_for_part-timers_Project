<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC 웹 공지 게시판</title>
<c:if test="${auth eq 'user'}">
<style type="text/css">
	.sidebar-nav{
		display: none;
	}
</style>
</c:if>
<style type="text/css">
	.ck-editor__editable_inline {
    min-height: 400px;
}
</style>
</head>
<script src="https://cdn.ckeditor.com/ckeditor5/19.1.1/classic/ckeditor.js"></script>
<script type="text/javascript" src="./js/boardDetail.js"></script>
<body>
<c:if test="${auth eq 'admin'}">
	<%@include file="/WEB-INF/views/topNavA.jsp"%>
</c:if>
<c:if test="${auth eq 'user'}">
	<%@include file="/WEB-INF/views/topNav.jsp"%>
</c:if>
<div class="container-fluid-full">
	<div class="row-fluid">
		<c:if test="${auth eq 'admin'}">
			<%@include file="/WEB-INF/views/sideBarA.jsp" %>
		</c:if>
		<c:if test="${auth eq 'user'}">
			<%@include file="/WEB-INF/views/sideBar.jsp" %>
		</c:if>
		<div id="content" class="span10" style="min-height: 844px; margin: auto;"> 
			<form id="detailform" >
			<input type="hidden" id="auth" value="${auth}">
			<input type="hidden" id="wn_seq" name="wn_seq" value="${wndto.wn_seq}" >
			<table class="table table-bordered">
			<tbody>
				<tr>
					<th>제목</th>
					<td>${wndto.wn_title}</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${wndto.user_id}</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>${wndto.wn_content}</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
					<c:forEach items="${flist}" var="dto" varStatus="vs">
						<a href="bfFileDown.do?bf_seq=${dto.bf_seq}">${vs.count}. ${dto.bf_filename}</a><br>
					</c:forEach>
					</td>
				</tr>
			</tbody>
			</table>
			
			</form>
			<div id="btnarea" style="text-align: center;">
			</div>
			</div>
			
			<div id="modify" class="modal fade" role="dialog" >
			  <div class="modal-dialog">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title">게시글 수정</h4>
			      </div>
			      <div class="modal-body">
			      	<!-- ajax를 통해서 수정하고 넘길 데이터를 표출 해 줌 -->
					<form action="#" method="post" id="frmModify"></form>
			      </div>
			    </div>
			
			  </div>
			</div>

	</div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>	
</body>
</html>