<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<meta charset="utf-8">
<link id="bootstrap-style" href="./css/bootstrap.min.css" rel="stylesheet">
<link href="./css/bootstrap-responsive.min.css" rel="stylesheet">
<link id="base-style" href="./css/style.css" rel="stylesheet">
<link id="base-style-responsive" href="./css/style-responsive.css" rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="img/favicon.ico">
<style type="text/css">
::-webkit-scrollbar{width: 7px;}
::-webkit-scrollbar-track {background-color:white;}
::-webkit-scrollbar-thumb {background-color:gray;border-radius: 50px;}
::-webkit-scrollbar-thumb:hover {background: black;}
::-webkit-scrollbar-button:start:decrement,::-webkit-scrollbar-button:end:increment {
width:1px;height:1px;background: white;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<div class="navbar">
		<div class="navbar-inner" style="height: 80px;">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse" style="margin-top: 27px;">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="./loginResult.do" style="margin-top: 20px;"><span><strong>SC</strong></span></a>
								
				<!-- start: Header Menu -->
				<div class="nav-no-collapse header-nav" style="margin-top: 20px;">
					<ul class="nav pull-right" style="background-color: black;">
						<li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="icon-bell"></i>
<!-- 								<span class="badge red"> -->
<!-- 								7 </span> -->
							</a>
							<ul class="dropdown-menu notifications">
								<li class="dropdown-menu-title">
 									<span>SC 홈페이지 공지사항 게시판</span>
								</li>	
                                <li class="dropdown-menu-sub-footer">
                            		<a href="./wnBoardList.do">공지사항 게시판으로 이동</a>
								</li>	
							</ul>
						</li>
						<!-- start: Notifications Dropdown -->
						<li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="icon-edit"></i>
<!-- 								<span class="badge red"> -->
<!-- 								5 </span> -->
							</a>
							<ul class="dropdown-menu tasks">
								<li class="dropdown-menu-title">
 									<span>SC 홈페이지 문의 게시판</span>
								</li>
								<li>
                            		<a class="dropdown-menu-sub-footer">문의게시판으로 이동</a>
								</li>	
							</ul>
						</li>
						<!-- end: Notifications Dropdown -->
						
						<!-- start: User Dropdown -->
						<li class="dropdown">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white user"></i> ${user.user_name}
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li class="dropdown-menu-title">
 									<span>환영합니다.</span>
								</li>
								<c:if test="${user.user_type eq 'employee'}">
									<li><a href="./ws.do"><i class="icon-building"></i> 내 사업장 목록</a></li>
								</c:if>
								<li><a class="submenu" href="./myInfo.do"><i class="icon-eye-open"></i> 내 정보 조회</a></li>
								<li><a class="submenu" href="./modifyPwForm.do"><i class="icon-key"></i> 비밀번호 수정</a></li>
								<li><a class="submenu" href="./leaveUs.do"><i class="icon-lock"></i> 회원 탈퇴</a></li>
								<li><a href="./logout.do"><i class="halflings-icon off"></i> 로그아웃</a></li>
							</ul>
						</li>
						<!-- end: User Dropdown -->
					</ul>
				</div>
				<!-- end: Header Menu -->
				
			</div>
		</div>
	</div>
	
<script type="text/javascript">
$(document).ready(function(){
	$('.dropmenu1').click(function(e){

		e.preventDefault();

		$(this).parent().find('ul').slideToggle();
	
	});
	$('.dropmenu2').click(function(e){

		e.preventDefault();

		$(this).parent().find('ul').slideToggle();
	
	});
	$('.dropmenu3').click(function(e){

		e.preventDefault();

		$(this).parent().find('ul').slideToggle();
	
	});
	$('.dropmenu4').click(function(e){

		e.preventDefault();

		$(this).parent().find('ul').slideToggle();
	
	});
	$('.dropmenu5').click(function(e){

		e.preventDefault();

		$(this).parent().find('ul').slideToggle();
	
	});
});
</script>	
