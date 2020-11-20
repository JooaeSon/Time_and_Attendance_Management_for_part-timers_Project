<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<div id="sidebar-left" class="span2" style="background-color: black; height: 100%;">
				<div class="nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li><a href="#"><i class="icon-eye-open"></i><span class="hidden-tablet"> 사업장 공지사항</span></a></li>
						<li>
							<a class="dropmenu1" href="#"><i class="icon-calendar"></i><span class="hidden-tablet"> 근무일정 </span><i class="icon-chevron-down"></i></a>
							<ul>
							<c:if test="${user.user_type eq 'employee'}">
								<li><a class="submenu" href="./goCalendar.do">&nbsp;&nbsp;<span class="hidden-tablet"> 근무일정 신청 및 조회</span></a></li>
<!-- 								<li><a class="submenu" href="./goCalendarView.do">&nbsp;&nbsp;<span class="hidden-tablet"> 근무일정 조회</span></a></li> -->
							</c:if>
							
							<c:if test="${rank eq 'man'}">
<!-- 								<li><a class="submenu" href="./goCalendar.do">&nbsp;&nbsp;<span class="hidden-tablet"> 근무일정 신청 및 조회</span></a></li> -->
								<li><a class="submenu" href="./goCalendarConfirm.do">&nbsp;&nbsp;<span class="hidden-tablet"> 근무일정 관리</span></a></li>
							</c:if>
							
							<c:if test="${user.user_type eq 'employer'}">
								<li><a class="submenu" href="./goCalendarConfirm.do">&nbsp;&nbsp;<span class="hidden-tablet"> 근무일정 관리</span></a></li>
								<li><a class="submenu" href="./insertBasicForm.do">&nbsp;&nbsp;<span class="hidden-tablet"> 근무 기초사항 설정</span></a></li>
							</c:if >
								
							</ul>	
						</li>
						
						<li>
							<a class="dropmenu1" href="#"><i class="icon-calendar"></i><span class="hidden-tablet"> 근태관리 </span><i class="icon-chevron-down"></i></a>
							<ul>
							
							<c:if test="${user.user_type eq 'employer'}">
								<li><a class="submenu" href="./MultiWork.do">&nbsp;&nbsp;<span class="hidden-tablet">관할직원 근태기록</span></a></li>
					
							</c:if >
							<c:if test="${rank eq 'man'}">
								<li><a class="submenu" href="./workcheck.do">&nbsp;&nbsp;<span class="hidden-tablet">출퇴근체크</span></a></li>
								<li><a class="submenu" href="./personalWork.do">&nbsp;&nbsp;<span class="hidden-tablet">나의 근태기록</span></a></li>
								<li><a class="submenu" href="./MultiWork.do">&nbsp;&nbsp;<span class="hidden-tablet">관활직원 근태기록</span></a></li>
							</c:if>
							<c:if test="${rank eq 'emp'}">
								<li><a class="submenu" href="./workcheck.do">&nbsp;&nbsp;<span class="hidden-tablet">출퇴근체크</span></a></li>
								<li><a class="submenu" href="./personalWork.do">&nbsp;&nbsp;<span class="hidden-tablet">나의 근태기록</span></a></li>
							</c:if>
								
							</ul>	
						</li>
						
						
						<li>
							<a class="dropmenu2" href="#"><i class="icon-folder-close-alt"></i><span class="hidden-tablet"> 다운로드 </span><i class="icon-chevron-down"></i></a>
							<ul>
								<li><a class="submenu" href="#">&nbsp;&nbsp;<span class="hidden-tablet"> 근무일정</span></a></li>
								<li><a class="submenu" href="#">&nbsp;&nbsp;<span class="hidden-tablet"> 근태기록</span></a></li>
								<li><a class="submenu" href="#">&nbsp;&nbsp;<span class="hidden-tablet"> 급여기록</span></a></li>
							</ul>	
						</li>
						<c:if test="${user.user_type eq 'employer'}">
							<li>
								<a class="dropmenu3" href="#"><i class="icon-list-alt"></i><span class="hidden-tablet"> 급여정보조회 </span><i class="icon-chevron-down"></i></a>
								<ul>
									<li><a class="submenu" href="./goSalaryUpload.do">&nbsp;&nbsp;<span class="hidden-tablet">근무시간 별 급여정보</span></a></li>
								</ul>	
							</li>
						</c:if>

						<c:if test="${user.user_type eq 'employer'}">
							<li>
								<a class="dropmenu4" href="#"><i class="icon-suitcase"></i><span class="hidden-tablet"> 사업장관리 </span><i class="icon-chevron-down"></i></a>
								<ul>
									<li><a class="submenu" href="./ws.do">&nbsp;&nbsp;<span class="hidden-tablet"> 사업장 정보 조회</span></a></li>
									<li><a class="submenu" href="./delReq.do">&nbsp;&nbsp;<span class="hidden-tablet"> 사업장 삭제 신청</span></a></li>

								</ul>	
							</li>
							<li>
								<a class="dropmenu4" href="#"><i class="icon-suitcase"></i><span class="hidden-tablet"> 직원관리 </span><i class="icon-chevron-down"></i></a>
								<ul>
									<li><a class="submenu" href="./empApply.do">&nbsp;&nbsp;<span class="hidden-tablet"> 입사승인</span></a></li>
									<li><a class="submenu" href="./empSearch.do">&nbsp;&nbsp;<span class="hidden-tablet"> 직원조회</span></a></li>
								</ul>	
							</li>
						</c:if>

						<li>
							<a class="dropmenu5" href="#"><i class="icon-heart"></i><span class="hidden-tablet"> 마이페이지 </span><i class="icon-chevron-down"></i></a>
							<ul>
								<li><a class="submenu" href="./myInfo.do">&nbsp;&nbsp;<span class="hidden-tablet"> 내 정보 조회</span></a></li>
								<li><a class="submenu" href="./modifyPwForm.do">&nbsp;&nbsp;<span class="hidden-tablet"> 비밀번호 수정</span></a></li>
								<li><a class="submenu" href="./leaveUs.do">&nbsp;&nbsp;<span class="hidden-tablet"> 회원 탈퇴</span></a></li>
							</ul>	
						</li>
					</ul>
				</div>
			</div>