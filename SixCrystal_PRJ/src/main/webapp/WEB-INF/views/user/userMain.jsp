<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />
    
    <title>SC 홈페이지</title>
    
    <link href="./css/bootstrap.css" rel="stylesheet" />
	<link href="./css/coming-sssoon.css" rel="stylesheet" />    
    
    <!--     Fonts     -->
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.css" rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Grand+Hotel' rel='stylesheet' type='text/css'>
<style type="text/css">
::-webkit-scrollbar{width: 7px;}
::-webkit-scrollbar-track {background-color:white;}
::-webkit-scrollbar-thumb {background-color:gray;border-radius: 50px;}
::-webkit-scrollbar-thumb:hover {background: black;}
::-webkit-scrollbar-button:start:decrement,::-webkit-scrollbar-button:end:increment {
width:1px;height:1px;background: white;}
</style>  
</head>
<body>
<nav class="navbar navbar-transparent navbar-fixed-top" role="navigation">  
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
      </ul>
      <ul class="nav navbar-nav navbar-right">
      		<li>
                <a href="./workSearch.do"> 사업장 검색</a>
            </li>
             <li>
                <a href="./wnBoardList.do"> 공지게시판</a>
            </li>
             <li>
                <a href="#"> 문의게시판 </a>
            </li>
            <!-- start: User Dropdown -->
			<li class="dropdown">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="halflings-icon white user"></i>마이페이지
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu" style="background-color: black; color: white; width: 200px;">
					<li> </li>
					<li style="font-size: 15px;"><strong>&nbsp;&nbsp;${user.user_name}님 환영합니다!</strong></li>
					<li><a href="./myInfo.do"><i class="halflings-icon user"></i> 내 정보조회</a></li>
					<li><a href="./ws.do"><i class="icon-building"></i> 내 사업장목록</a></li>
					<li><a href="./logout.do"><i class="halflings-icon off"></i> 로그아웃</a></li>
				</ul>
			</li>
			<!-- end: User Dropdown -->
       </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container -->
</nav>
<div class="main" style="background-image: url('./img/default.jpg')">
<!--    Change the image source '/images/default.jpg' with your favourite image.     -->
    
    <div class="cover black" data-color="black"></div>
     
<!--   You can change the black color for the filter with those colors: blue, green, red, orange       -->

    <div class="container">
        <h1 class="logo cursive">
            SC
        </h1>
<!--  H1 can have 2 designs: "logo" and "logo cursive"           -->
        
        <div class="content">
            <div class="subscribe">
                <h5 class="info-text">
                    SC 홈페이지에 오신걸 환영합니다!
                </h5>
            </div>
        </div>
    </div>
    <div class="footer">
      <div class="container">
             본 사이트의 저작권은 SC에게 있습니다.
      </div>
    </div>
 </div>
 </body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</html>