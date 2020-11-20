<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="kr">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>TOAST UI Calendar App DEMO</title>
<!--     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"> -->
    <link rel="stylesheet" type="text/css" href="https://uicdn.toast.com/tui.time-picker/latest/tui-time-picker.css">
    <link rel="stylesheet" type="text/css" href="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.css">
    <link rel="stylesheet" type="text/css" href="././Schedule_src/dist/tui-calendar.css">
    <link rel="stylesheet" type="text/css" href="././Schedule_src/css/default.css">
    <link rel="stylesheet" type="text/css" href="././Schedule_src/css/icons.css">
</head>
<script type="text/javascript">
    
    function doExcelDownloadProcess(){
        var f = document.form1;
        f.action = "boardExdown.do";
        f.submit();
    }
</script>







<body>
<%@include file="/WEB-INF/views/topNav.jsp"%>
<div class="container-fluid-full">
   <div class="row-fluid">
      <%@include file="/WEB-INF/views/sideBar.jsp" %>
      <div id="content" class="span10" style="min-height: 800px; margin: auto;"> 
        <div id="menu">
            <span class="dropdown">
                <button id="dropdownMenu-calendarType" class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="true">
                    <i id="calendarTypeIcon" class="calendar-icon ic_view_month" style="margin-right: 4px;"></i>
                    <span id="calendarTypeName">Dropdown</span>&nbsp;
                    <i class="calendar-icon tui-full-calendar-dropdown-arrow"></i>
                </button>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu-calendarType">
                </ul>
            </span>
            <span id="menu-navi">
<!--                 <button type="button" class="btn btn-default btn-sm move-today" data-action="move-today">Today</button> -->
                <button type="button" class="btn btn-default btn-sm move-day" data-action="move-prev">
                    <i class="calendar-icon ic-arrow-line-left" data-action="move-prev"></i>
                </button>
                <button type="button" class="btn btn-default btn-sm move-day" data-action="move-next">
                    <i class="calendar-icon ic-arrow-line-right" data-action="move-next"></i>
                </button>
            </span>
            <span id="renderRange" class="render-range"></span>
			${rank}
			${flag}
           <input type="hidden" value= ${WORK_START} id='WORK_START'>
           <input type="hidden" value= ${WORK_END} id='WORK_END'>
           <input type="hidden" value= ${id} id='id'>
           <input type="hidden" value= ${rank} id='rank'>
           <input type="hidden" value= ${flag} id='flag'>
        </div>
        

         <div id="calendar"></div>
        </div>

<!-- 모달을 통해서 교환을 할꺼야!!  -->
  <div class="modal fade" id="exchangeData" role="dialog">
    <div class="modal-dialog">
      Modal content
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">일정 교환</h4>
        </div>
        <div class="modal-body">
          <form action="#" method="post" id="exchangeSchedule"></form>
          
        </div>
      </div>
    </div>
  </div>

<!--   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<script src="https://code.jquery.com/jquery-3.5.1.js"></script> 
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://uicdn.toast.com/tui.code-snippet/v1.5.2/tui-code-snippet.min.js"></script>
    <script src="https://uicdn.toast.com/tui.time-picker/v2.0.3/tui-time-picker.min.js"></script>
    <script src="https://uicdn.toast.com/tui.date-picker/v4.0.3/tui-date-picker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/chance/1.0.13/chance.min.js"></script>
    <script src="././Schedule_src/dist/tui-calendar.js"></script>
    <script src="././Schedule_src/js/calendars.js"></script>
    <script src="././Schedule_src/js/schedules.js"></script>
    <script src="././Schedule_src/js/manager_calendar.js"></script>
         </div>
      </div>
<!--    </div> -->
<!-- </div> -->
<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>
