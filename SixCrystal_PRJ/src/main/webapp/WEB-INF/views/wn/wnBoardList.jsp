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
</head>
<script type="text/javascript" src="./js/wnBoardList.js"></script>
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
			<input type="hidden" id="auth" value="${auth}">
			<form id="frm" >
			
			<table class="table table-hover">
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>조회수</th>
						<th>작성자</th>
						<th>작성일</th>
					</tr>
				</thead>
				
				<tbody>
				
				<c:forEach items="${lists}" varStatus="vs" var="dto">
					<tr>
						<td>${dto.wn_seq}</td>
						<td><a onclick="wndetail(${dto.wn_seq})">${dto.wn_title}</a></td>
						<td>${dto.wn_hit}</td>
						<td>관리자</td>
						<td>${dto.wn_regdate}</td>
					</tr>
				</c:forEach>
				
				</tbody>
				
			</table>
				<input type="hidden" name="index" id="index" value="${page.index}">
				<input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">	
				<input type="hidden" name="listNum" id="listNum" value="${page.listNum}">
			</form>
			
			<div id="btnstage" style="text-align: right;">
			</div>
			
			<div class="center" style="text-align: center;">
					<div id="pagestage" >
						<button onclick="pageFirst()">&laquo;</button>
						<button onclick="pagePre(${page.pageNum},${page.pageList})">&lsaquo;</button>
						
						<c:forEach var='i' begin="${page.pageNum}" end="${page.count}" step="1">
							<a href="#" onclick='pageIndex(${i})'>${i}</a>
						</c:forEach>
						
						<button onclick='pageNext(${page.pageNum},${page.total},${page.pageList},${page.listNum})'>&rsaquo;</button>
						<button onclick='pageLast(${page.pageNum},${page.total},${page.pageList},${page.listNum})'>&raquo;</button>
				</div>
				</div>
			
			</div>
	</div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>	
</body>
</html>