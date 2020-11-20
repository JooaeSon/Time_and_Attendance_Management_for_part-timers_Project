'use strict';

/* eslint-disable */
/* eslint-env jquery */
/* global moment, tui, chance */
/* global findCalendar, CalendarList, ScheduleList, generateSchedule */







(function(window, Calendar) {
    var cal, resizeThrottled;
    var useCreationPopup =true;// 이거 false로 바꾸는 메서드 가 있어야지 그 조회가 되는거네!!?!? 
    var useDetailPopup = true;
    var readOnlyFlag = false;
//    var isReadOnly=true;
    var datePicker, selectedCalendar;

    cal = new tui.Calendar('#calendar', {
        defaultView: 'week',
        isReadOnly : readOnlyFlag,
        useCreationPopup: useCreationPopup,
        useDetailPopup: useDetailPopup,
        calendars: CalendarList,
        template: {
//            milestone: function(model) {
//                return '<span class="calendar-font-icon ic-milestone-b"></span> <span style="background-color: ' + model.bgColor + '">' + model.title + '</span>';
////            	 return getTimeTemplate(schedule, true);
//            },
            allday: function(schedule) {
                return getTimeTemplate(schedule, true);
            },
            time: function(schedule) {
                return getTimeTemplate(schedule, false);
            }
        },
        week : {
//        	hourStart :  6
        }
        
        

    });
    
//  alert(index+" : "+member[index]);
    var nowUser = $("#id").val();//현재 들어온 아이디 
//    alert(nowUser);
    var WORK_START = $("#WORK_START").val();//근무시작날짜
//    alert(WORK_START);
    var WORK_END = $("#WORK_END").val();//근무종료날짜
//    alert(WORK_END);
    var flag = $("#flag").val();//근무종료날짜
//    alert(flag);
    var rank = ($("#rank").val()=='emp'?1:2);//직급
//    alert(rank);
    
    cal.setDate(new Date(WORK_START));

    // event handlers
    cal.on({
        'clickMore': function(e) {
            console.log('clickMore', e);
        },
        'clickSchedule': function(e) {
            console.log('clickSchedule', e);
           $(".tui-full-calendar-schedule-title").mouseover(function(){
        	   let userId = $(this).text();
        	   let id = e.schedule.id;
             let START =new Date(e.schedule.start);
             let start = DateToString(START);
//             let year_month=dateToYYYY_MM(start);
//        	   alert(id);
        	   var calenderId = e.schedule.calendarId;
//        	   alert(calenderId);
//        	   var userId = $(this).html();
//        	   alert(userId);
        	   if(cal.getOptions().isReadOnly){
////        		   alert(cal.getOptions().isReadOnly);
        		   $(this).attr("onclick","exchange('"+userId+"','"+id+"','"+calenderId+"','"+start+"')")
//        		   $(this).attr("onclick","exchange("+userId+")");
        	   }        	  
//        	   
           })
        },
        'clickDayname': function(date) {
            console.log('clickDayname', date);
        },
        'beforeCreateSchedule': function(e) {
            console.log('beforeCreateSchedule', e);
            saveNewSchedule(e);
        },
        'beforeUpdateSchedule': function(e) {
            var schedule = e.schedule;
            var changes = e.changes;
//            let userId='rlghd153';
            console.log('beforeUpdateSchedule', e);
            if (changes && !changes.isAllDay && schedule.category === 'allday') {
                changes.category = 'time';
            }
            let CRITERIA =new Date(e.schedule.start);
            let start_updatebefore = DateToString(CRITERIA);
          cal.updateSchedule(schedule.id, schedule.calendarId, changes);
          refreshScheduleVisibility();
          
          
          let START =new Date(e.changes.start);
          let start = DateToString(START);
          let END =new Date(e.changes.end);
          let end = DateToString(END);
          let year_month=dateToYYYY_MM(CRITERIA);
//          alert(start);
          var updateInfo=JSON.stringify({
      		"id":e.schedule.id,
      		"title":e.changes.title,
      		"isAllDay":e.changes.isAllDay,
      		"start":start,
      		"end":end,
      		"category":e.changes.category,
      		"isReadOnly":e.changes.isReadOnly,
      		"calendarId":e.changes.calendarId,
      		"state": e.changes.state,
      		"year_month": year_month,
      		"start_updatebefore": start_updatebefore,
      	})
      	alert(updateInfo);
          $.ajax({
        	  url:"./updateScheduleData.do",
        	  method:"post",
        	  data:{
        		  "updateInfo":updateInfo 
        	  },
        	  dataType:"json",
        	  success:function(msg){
        		  alert("성공");
        	  },
        	  error:function(){
        		  alert("잘못된 요청입니다");
        	  }
          });
        
        },
        'beforeDeleteSchedule': function(e) {
            console.log('beforeDeleteSchedule', e);
//            var start_update=JSON.stringify(e.schedule.start);
            
            let START =new Date(e.schedule.start.toUTCString());
            let start_update = DateToString(START);
//            alert(start_update);
//            alert(e.schedule.id);
            let id=e.schedule.id;
//            alert(e.schedule.calendarId);
            let calendarId=(e.schedule.calendarId);
            let year_month=dateToYYYY_MM(START);
            

            var deleteInfo=JSON.stringify({
            		"id":id,
            		  "calendarId":calendarId,
            		  "start_update":start_update,
            		  "year_month":year_month
            });   
            alert(JSON.stringify(deleteInfo));
            $.ajax({
          	  url:"./deleteScheduleData.do",
        	  method:"post",
          	  data:{
          		 "deleteInfo":deleteInfo
          	  },
          	  dataType:"json",
          	  success:function(msg){
          		  alert("성공");
          	  },
          	  error:function(){
          		  alert("잘못된 요청입니다");
          	  }
            });
            cal.deleteSchedule(e.schedule.id, e.schedule.calendarId);
        },
        'afterRenderSchedule': function(e) {
            var schedule = e.schedule;
            // var element = cal.getElement(schedule.id, schedule.calendarId);
            // console.log('afterRenderSchedule', element);
//            alert("랜더링스케쥴");
        },
        'clickTimezonesCollapseBtn': function(timezonesCollapsed) {
            console.log('timezonesCollapsed', timezonesCollapsed);

            if (timezonesCollapsed) {
                cal.setTheme({
                    'week.daygridLeft.width': '77px',
                    'week.timegridLeft.width': '77px'
                });
            } else {
                cal.setTheme({
                    'week.daygridLeft.width': '60px',
                    'week.timegridLeft.width': '60px'
                });
            }

            return true;
        }
    });
    



    /**
     * Get time template for time and all-day
     * @param {Schedule} schedule - schedule
     * @param {boolean} isAllDay - isAllDay or hasMultiDates
     * @returns {string}
     */
    function getTimeTemplate(schedule, isAllDay) {
        var html = [];
        var start = moment(schedule.start.toUTCString());/////////////////////////////////이걸 바꿔야할듯!! 
        if (!isAllDay) {
            html.push('<strong>' + start.format('HH:mm') + '</strong> ');
        }
        if (schedule.isPrivate) {
            html.push('<span class="calendar-font-icon ic-lock-b"></span>');
            html.push(' Private');
        } else {
            if (schedule.isReadOnly) {
                html.push('<span class="calendar-font-icon ic-readonly-b"></span>');
            } else if (schedule.recurrenceRule) {
                html.push('<span class="calendar-font-icon ic-repeat-b"></span>');
            } else if (schedule.attendees.length) {
                html.push('<span class="calendar-font-icon ic-user-b"></span>');
            } else if (schedule.location) {
                html.push('<span class="calendar-font-icon ic-location-b"></span>');
            }
            html.push(' ' + schedule.title);
        }

        return html.join('');
    }

    /**
     * A listener for click the menu
     * @param {Event} e - click event
     */
    function onClickMenu(e) {
        var target = $(e.target).closest('a[role="menuitem"]')[0];
        var action = getDataAction(target);
        var options = cal.getOptions();
        var viewName = '';

        console.log(target);
        console.log(action);
        switch (action) {
            case 'toggle-daily':
                viewName = 'day';
                break;
            case 'toggle-weekly':
                viewName = 'week';
                break;
            case 'toggle-monthly':
                options.month.visibleWeeksCount = 0;
                viewName = 'month';
                break;
            case 'toggle-weeks2':
                options.month.visibleWeeksCount = 2;
                viewName = 'month';
                break;
            case 'toggle-weeks3':
                options.month.visibleWeeksCount = 3;
                viewName = 'month';
                break;
            case 'toggle-narrow-weekend':
                options.month.narrowWeekend = !options.month.narrowWeekend;
                options.week.narrowWeekend = !options.week.narrowWeekend;
                viewName = cal.getViewName();

                target.querySelector('input').checked = options.month.narrowWeekend;
                break;
            case 'toggle-start-day-1':
                options.month.startDayOfWeek = options.month.startDayOfWeek ? 0 : 1;
                options.week.startDayOfWeek = options.week.startDayOfWeek ? 0 : 1;
                viewName = cal.getViewName();

                target.querySelector('input').checked = options.month.startDayOfWeek;
                break;
            case 'toggle-workweek':
                options.month.workweek = !options.month.workweek;
                options.week.workweek = !options.week.workweek;
                viewName = cal.getViewName();

                target.querySelector('input').checked = !options.month.workweek;
                break;
            default:
                break;
        }

        cal.setOptions(options, true);
        cal.changeView(viewName, true);

        setDropdownCalendarType();
        setRenderRangeText();
        setSchedules();
    }

    function onClickNavi(e) {
        var action = getDataAction(e.target);
        var startLimit=new Date(String(cal.getDateRangeStart()._date))//
//        alert(startLimit);
        var endLimit=new Date(String(cal.getDateRangeEnd()._date))//
//        alert(endLimit);
	
        var WORK_START_navi;
        var WORK_END_navi;
         		let work_start =new Date(WORK_START);
//         		WORK_START=new Date(work_start.setHours(work_start.getHours()-9));
         		WORK_START_navi=new Date(work_start.setHours(work_start.getHours()-9));
//         		let work_end =new Date(msg.WORK_END);
         		let work_end =new Date(WORK_END);
//         		WORK_END=new Date(work_end.setHours(work_end.getHours()-9));
         		WORK_END_navi=new Date(work_end.setHours(work_end.getHours()-9));
//         		
              switch (action) {
    	        case 'move-prev':
////    	        	if(String(startLimit)<String(WORK_START)){
             		if(startLimit.getTime()>WORK_START_navi.getTime()){
             			alert("앞으로 가도됩니다~");
             			cal.prev();
             			cal.clear();
             			
             			setSchedules();
             			setRenderRangeText();
        	            break;
             		}else{
////             			alert("달력스타트 : "+ startLimit);
////             			alert("근무스타트 : "+ WORK_START);
             			alert("놉 앞으로가면안댓!! ");
             			break;
             		}
    	        case 'move-next':             		
////             		if(String(endLimit)<String(WORK_END)){
             		if(endLimit.getTime()<WORK_END_navi.getTime()){
             			alert("뒤로 가도됩니다~");
             			cal.next();
             			cal.clear();
             			
             			setSchedules();
             			setRenderRangeText();
             			break;
             		}else{
             			alert("놉 뒤로가면안댓!! ");
             			break;
             		}
//
    	        case 'move-today':
    	            cal.today();
    	            cal.clear();
    	            
    	            setSchedules();
    	            setRenderRangeText();
    	            break;
    	        default:
    	            return;
    	    }

    }


    function onChangeNewScheduleCalendar(e) {
        var target = $(e.target).closest('a[role="menuitem"]')[0];
        var calendarId = getDataAction(target);
        changeNewScheduleCalendar(calendarId);
    }

    function changeNewScheduleCalendar(calendarId) {
        var calendarNameElement = document.getElementById('calendarName');
        var calendar = findCalendar(calendarId);
        var html = [];

        html.push('<span class="calendar-bar" style="background-color: ' + calendar.bgColor + '; border-color:' + calendar.borderColor + ';"></span>');
        html.push('<span class="calendar-name">' + calendar.name + '</span>');

        calendarNameElement.innerHTML = html.join('');

        selectedCalendar = calendar;
    }

    function createNewSchedule(event) {
        var start = event.start ? new Date(event.start.getTime()) : new Date();
        var end = event.end ? new Date(event.end.getTime()) : moment().add(1, 'hours').toDate();

        if (useCreationPopup) {
            cal.openCreationPopup({
                start: start,
                end: end
            });
        }
    }
    
    function dateToYYYY_MM(date){
    	function pad(num){
    		num=num+'';
    		return num.length<2?'0'+num:num;
    	}
    	return date.getFullYear()+'-'+pad(date.getMonth()+1);
    }
    
    function DateToString(pDate) {
        var yyyy = pDate.getFullYear();
        var mm = pDate.getMonth() < 9 ? "0" + (pDate.getMonth() + 1) : (pDate.getMonth() + 1); // getMonth() is zero-based
        var dd  = pDate.getDate() < 10 ? "0" + pDate.getDate() : pDate.getDate();
        var hh = pDate.getHours() < 10 ? "0" + pDate.getHours() : pDate.getHours();
        var min = pDate.getMinutes() < 10 ? "0" + pDate.getMinutes() : pDate.getMinutes();
        return "".concat(yyyy).concat("-").concat(mm).concat("-").concat(dd).concat("T").concat(hh).concat(":").concat(min);
    };
    
    function saveNewSchedule(scheduleData) {
//    	var calendar = CalendarList[rank-1];
    	var rank=1;
    	var calendar = CalendarList[rank-1];
//        var calendar = scheduleData.calendar || findCalendar(index); 
      let START =new Date(scheduleData.start._date);
      let start = DateToString(START);
      let END =new Date(scheduleData.end._date);
      let end = DateToString(END);
      let year_month=dateToYYYY_MM(START);
        var schedule = {
            id: String(~~(Math.random() * 1000000000)),
            title: scheduleData.title,
            isAllDay: scheduleData.isAllDay,
            start: start,
            end: end,
            category: scheduleData.isAllDay ? 'allday' : 'time',
            isReadOnly:false,
            state: scheduleData.state
        };
        if (calendar) {
            schedule.calendarId = calendar.id;
            schedule.color = calendar.color;
            schedule.bgColor = calendar.bgColor;
            schedule.borderColor = calendar.borderColor;
        }
        var schedule_string=JSON.stringify({
    		"id":schedule.id,
    		"title":scheduleData.title,
    		"isAllDay":schedule.isAllDay,
    		"start":start,
    		"end":end,
    		"category":schedule.category,
//    		"dueDateClass":dueDateClass,
    		"isReadOnly":schedule.isReadOnly,
    		"calendarId":rank,
    		"state": scheduleData.state,
    		"year_month": year_month
    	})
        alert("schedule"+schedule_string);
        $.ajax({
        	url:"./getScheduleData.do",
        	method : "POST",
        	data:{"schedule_string":schedule_string,// 나중에이거 하나하나 보내야겠다 ,,, 밑에처럼,,,ㅎ
        	},

        	dataType : "json",
        	success:function(msg){
        		alert("성공");
                cal.createSchedules([schedule]);
                refreshScheduleVisibility();
        	},
        	error:function(){
        		alert("잘못된 요청입니다");
        	}
        	
        });

    }

    function onChangeCalendars(e) {
        var calendarId = e.target.value;
        var checked = e.target.checked;
        var viewAll = document.querySelector('.lnb-calendars-item input');
        var calendarElements = Array.prototype.slice.call(document.querySelectorAll('#calendarList input'));
        var allCheckedCalendars = true;

        if (calendarId === 'all') {
            allCheckedCalendars = checked;

            calendarElements.forEach(function(input) {
                var span = input.parentNode;
                input.checked = checked;
                span.style.backgroundColor = checked ? span.style.borderColor : 'transparent';
            });

            CalendarList.forEach(function(calendar) {
                calendar.checked = checked;
            });
        } else {
            findCalendar(calendarId).checked = checked;

            allCheckedCalendars = calendarElements.every(function(input) {
                return input.checked;
            });

            if (allCheckedCalendars) {
                viewAll.checked = true;
            } else {
                viewAll.checked = false;
            }
        }

        refreshScheduleVisibility();
    }

    function refreshScheduleVisibility() {
        var calendarElements = Array.prototype.slice.call(document.querySelectorAll('#calendarList input'));

        CalendarList.forEach(function(calendar) {
            cal.toggleSchedules(calendar.id, !calendar.checked, false);
        });

        cal.render(true);

        calendarElements.forEach(function(input) {
            var span = input.nextElementSibling;
            span.style.backgroundColor = input.checked ? span.style.borderColor : 'transparent';
        });
    }

    function setDropdownCalendarType() {
        var calendarTypeName = document.getElementById('calendarTypeName');
        var calendarTypeIcon = document.getElementById('calendarTypeIcon');
        var options = cal.getOptions();
        var type = cal.getViewName();
        var iconClassName;

        if (type === 'day') {
            type = 'Daily';
            iconClassName = 'calendar-icon ic_view_day';
        } else if (type === 'week') {
            type = 'Weekly';
            iconClassName = 'calendar-icon ic_view_week';
        } else if (options.month.visibleWeeksCount === 2) {
            type = '2 weeks';
            iconClassName = 'calendar-icon ic_view_week';
        } else if (options.month.visibleWeeksCount === 3) {
            type = '3 weeks';
            iconClassName = 'calendar-icon ic_view_week';
        } else {
            type = 'Monthly';
            iconClassName = 'calendar-icon ic_view_month';
        }

        calendarTypeName.innerHTML = type;
        calendarTypeIcon.className = iconClassName;
    }

    function currentCalendarDate(format) {
      var currentDate = moment([cal.getDate().getFullYear(), cal.getDate().getMonth(), cal.getDate().getDate()]);
      alert(currentDate);

      return currentDate.format(format);
    }

    function setRenderRangeText() {
        var renderRange = document.getElementById('renderRange');
        var options = cal.getOptions();
        var viewName = cal.getViewName();

        var html = [];
        if (viewName === 'day') {
            html.push(currentCalendarDate('YYYY.MM.DD'));
        } else if (viewName === 'month' &&
            (!options.month.visibleWeeksCount || options.month.visibleWeeksCount > 4)) {
//            html.push(currentCalendarDate('YYYY.MM'));
            html.push(currentCalendarDate('YYYY.MM.DD'));
        } else {
            html.push(moment(cal.getDateRangeStart().getTime()).format('YYYY.MM.DD'));
            html.push(' ~ ');
//            html.push(moment(cal.getDateRangeEnd().getTime()).format(' MM.DD'));
            html.push(moment(cal.getDateRangeEnd().getTime()).format('YYYY.MM.DD'));
        }
        renderRange.innerHTML = html.join('');
    }

    function setSchedules() {
        cal.clear();
        
        let START =new Date(cal.getDateRangeStart());
        let start = DateToString(START);
//        alert(start);
        let END =new Date(cal.getDateRangeEnd());
        let end = DateToString(END);
//        alert(end);
        let start_month=dateToYYYY_MM(START);
        let end_month=dateToYYYY_MM(END);
        
        var boundary_string=JSON.stringify({
    		"start":start,
    		"end":end,
    		"start_month": start_month,
    		"end_month": end_month,
    		"rank": 1
    	});
    	

        
        $.ajax({
            url:"./generateEmpScueduleData.do",
            method:"post",
            data:{
            	"boundary_string": boundary_string
            },
            dataType:"json",
            success:function(msg){
//            	alert(JSON.stringify(msg));
                $.each(msg,function(element){

                	var calendar = findCalendar(msg[element].calendarId); 
//                	alert(msg[element].calendarId);
                  if (calendar) {
                      var color = calendar.color;
                      var bgColor = calendar.bgColor;
                      var borderColor = calendar.borderColor;
                  }

                	 cal.createSchedules([{
    	        		 id : msg[element].id,
    	                 calendarId :msg[element].calendarId,
    	                 title :msg[element].title,
    	                 isReadOnly :msg[element].isReadOnly,
    	                 isAllday :msg[element].isAllday,
    	                 start :msg[element].start,    
    	                 end :msg[element].end,
    	                 category :msg[element].category,
    	                 state:msg[element].state,
    	                 color : color,
    	                 bgColor : bgColor,
    	                 dragBgColor : borderColor
    	                 }
                	 ]);
                	 refreshScheduleVisibility();
                });
            },
            error:function(){
               alert("잘못된 요청입니다");
            }
            
         });

    }


    function setEventListener() {
        $('#menu-navi').on('click', onClickNavi);
        $('.dropdown-menu a[role="menuitem"]').on('click', onClickMenu);
        $('#lnb-calendars').on('change', onChangeCalendars);//보라색 파란색

//        $('#btn-save-schedule').on('click', onNewSchedule);///???????????
        $('#btn-new-schedule').on('click', createNewSchedule);//옆에 뉴 스케쥴

        $('#dropdownMenu-calendars-list').on('click', onChangeNewScheduleCalendar);////????

        window.addEventListener('resize', resizeThrottled);
    }

    function getDataAction(target) {
        return target.dataset ? target.dataset.action : target.getAttribute('data-action');
    }

    resizeThrottled = tui.util.throttle(function() {
        cal.render();
    }, 50);

    window.cal = cal;

    setDropdownCalendarType();
    setRenderRangeText();
    setSchedules();
    setEventListener();
})(window, tui.Calendar);

function exchange(userId,id,calenderId){
	ajaxExchange(userId,id,calenderId);
	$("#exchangeData").modal();
//    location.reload(true);
}

var seletedId="";

var ajaxExchange = function(userId,id,calenderId,start){

	$.ajax({
		url:"./exchange.do",
		type:"post",
		dataType:"json",
//		dataType:"text",
		data:{
			"userId":userId,
			"id":id,
			"calenderId":calenderId
		},
		success: function(msg){
//			alert("성공!")
//			alert(msg);
//			var seletedId;
			var html="";
			  html = "<div class='form-group'>";
			  html += "<p >아아 컨트롤러에서 세션담아서 내 아이디일 경우에만 모달을 띄울 수 있도록 해야겠다!! </p>";
			  html += "<p >여기에 어떤 형태로 제이슨이 와야 내가 원하는대로 데이터를 뿌릴 수 있을까아 </p>";
			  html += "<input type='hidden' value='"+userId+"' name='my_id' />";
			  html += "<input type='hidden' value='"+id+"' name='my_scheid' />";
			  html += "<input type='hidden' value='"+start+"' name='start' />";
	          html += "<label for='employee_id' >내가 바꿀 특정 직원을 선택한다</label>";
	          html += "<select name='emp_id' id='employee_id' class='form-control'>";
	          html += "<option value='' disabled selected hidden>--선택--</option>";
	          $.each(msg,function(key,value){
	        	  html += "<option value="+key+">"+key+"</option>";//여기서 for each를 써야할거같은데? 
	  		  });
	          //$("#employee_id option:selected").val()
	          html += "</select>";
	          html += "</div>";
//	        	  
	          $("#exchangeSchedule").html(html);
	          ///
	          html += "<div class='form-group'>";///지워야될 수도,,!?
	          html += "<label for='schedule_time' >선택한 직원의 스케쥴만 고고!! </label>";
	          html += "<select name='emp_schedule' id='employee_schedule' class='form-control'>";
	          html += "<option value='' disabled selected hidden>--선택--</option>";
	          html += "<option value='-1'>나의 스케쥴 던지기</option>";
//	          var seletedId=$("#employee_id option:selected").val();
	          $("#employee_id").change(function(){
	        	  seletedId=$("#employee_id option:selected").val();
		          $.each(msg,function(key,value){
	        		  alert("선택한거임한거임 난 했음 : " + seletedId);
	        		 
		        	  if(key.includes(seletedId)){
	        		  alert(key);
//		        		  if(key==seletedId){
			        	  $.each(value,function(k,v){
			        		  html += "<option value="+v.id+" "+v.start+" "+v.end+">"+"START: "+v.start+" ~ END: "+v.end+"</option>";//여기서 for each를 써야할거같은데?
//			        		  alert(v.id+">"+"start: "+v.start+"~ end: "+v.end);
			        	  });  
//			        	  html += "<option value="+v.id+" "+v.start+" "+v.end+">"+"START: "+v.start+" ~ END: "+v.end+"</option>";//여기서 for each를 써야할거같은데?
		        	  }
		  		  });
		          html += "</select>";
		          html += "</div>";
		          html += "<div class='modal-footer'>";
		          html += "<input class='btn btn-success' type='button' value='완료' onclick='exchangeData()'>";
		          html += "<button type='button' class='btn btn-default' data-dismiss='modal'>닫기</button>";
		          html +="</div>";
		          $("#exchangeSchedule").html(html);
	          });

		},
		error: function(){
			alert("잘못된 요청입니다.");
		}
	});

	
}


function exchangeData(){
	var frm = document.getElementById("exchangeSchedule");
	frm.action = "./exchangeData.do";
	var title = $("#title").val();
	if(title== ''){
		swal("글 수정 오류", "제목은 필수 값 입니다.");
	}else{
		frm.submit();
	}
}


