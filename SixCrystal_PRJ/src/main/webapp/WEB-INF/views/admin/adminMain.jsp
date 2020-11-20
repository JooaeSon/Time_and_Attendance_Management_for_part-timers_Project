<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC(Admin)</title>
</head>
<body>
<%@include file="/WEB-INF/views/topNavA.jsp"%>
<div class="container-fluid-full">
	<div class="row-fluid">
		<%@include file="/WEB-INF/views/sideBarA.jsp" %>
		<div id="content" class="span10" style="min-height: 800px; margin-left: 20px;"> 
		<div class="row-fluid">
			<!-- 해야 할 일 -->
			<div class="box black span5 noMargin" onTablet="span12" onDesktop="span5">
					<div class="box-header">
						<h2><i class="halflings-icon white check"></i><span class="break"></span>예정된 일정</h2>
						<div class="box-icon">
							<a href="#" class="btn-setting"><i class="halflings-icon white wrench"></i></a>
							<a href="#" class="btn-close"><i class="halflings-icon white remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<div class="todo metro">
							<ul class="todo-list">
								<li class="red">
									<input type="checkbox" style="margin-top: -5px;">	
									일하기 
									<strong>today</strong>
								</li>
								<li class="red">
									<input type="checkbox" style="margin-top: -5px;">
									일하기
									<strong>today</strong>
								</li>
								<li class="yellow">
									<input type="checkbox" style="margin-top: -5px;">
									나는
									<strong>tommorow</strong>
								</li>
								<li class="yellow">
									<input type="checkbox" style="margin-top: -5px;">
									일개미
									<strong>tommorow</strong>
								</li>
								<li class="green">
									<input type="checkbox" style="margin-top: -5px;">
									개미는 뚠뚠
									<strong>this week</strong>
								</li>
								<li class="green">
									<input type="checkbox" style="margin-top: -5px;">
									오늘도 뚠뚠
									<strong>this week</strong>
								</li>
								<li class="blue">
									<input type="checkbox" style="margin-top: -5px;">
									열심히
									<strong>this month</strong>
								</li>
								<li class="blue">
									<input type="checkbox" style="margin-top: -5px;">
									일을 하네
									<strong>this month</strong>
								</li>
							</ul>
						</div>	
					</div>
				</div>
				<!-- 해야할 일 끝 -->
				<!-- 공지사항 시작 -->
				<div class="box black span5" onTablet="span6" onDesktop="span5">
					<div class="box-header">
						<h2><i class="halflings-icon white list"></i><span class="break"></span>공지사항</h2>
						<div class="box-icon">
							<a href="#" class="btn-close"><i class="halflings-icon white remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<ul class="dashboard-list metro">
							<li>
								<a href="#">
									<i class="icon-arrow-up green"></i>                               
									<strong>92</strong>
									New Comments                                    
								</a>
							</li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-down red"></i>
							  <strong>15</strong>
							  New Registrations
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-minus blue"></i>
							  <strong>36</strong>
							  New Articles                                    
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-comment yellow"></i>
							  <strong>45</strong>
							  User reviews                                    
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-up green"></i>                               
							  <strong>112</strong>
							  New Comments                                    
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-down red"></i>
							  <strong>31</strong>
							  New Registrations
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-minus blue"></i>
							  <strong>93</strong>
							  New Articles                                    
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-comment yellow"></i>
							  <strong>256</strong>
							  User reviews                                    
							</a>
						  </li>
						</ul>
					</div>
				</div>
				<!-- 끝 -->
			</div>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>