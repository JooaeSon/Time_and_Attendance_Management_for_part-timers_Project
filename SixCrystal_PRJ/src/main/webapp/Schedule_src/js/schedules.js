'use strict';

/*eslint-disable*/
var SCHEDULE_ID=1;// 이걸 사용할 수 있을까?!? 
var ScheduleList = [];

var SCHEDULE_CATEGORY = [
    'milestone',
    'task'
];

function ScheduleInfo() {
    this.id = null;
    this.calendarId = null;

    this.title = null;
    this.body = null;
    this.isAllday = false;
    this.start = null;
    this.end = null;
    this.category = '';
//    this.userId = '';

    this.color = null;
    this.bgColor = null;
    this.dragBgColor = null;
    this.borderColor = null;
//    this.customStyle = '';

//    this.isFocused = false;
//    this.isPending = false;
//    this.isVisible = true;
    this.isReadOnly = false;
//    this.goingDuration = 0;
//    this.comingDuration = 0;
//    this.recurrenceRule = '';
    this.state = '';
//    this.location='';
//    this.raw = {
//        memo: '',
//        hasToOrCc: false,
//        hasRecurrenceRule: false,
////        location: null,
//        class: 'public', // or 'private'
//        creator: {
//            name: '',
//            avatar: '',
//            company: '',
//            email: '',
//            phone: ''
//        }
//    };
}

function generateTime(schedule, renderStart, renderEnd) {
    var startDate = moment(renderStart.getTime())
    var endDate = moment(renderEnd.getTime());
    var diffDate = endDate.diff(startDate, 'days');

    schedule.isAllday = chance.bool({likelihood: 30});
    if (schedule.isAllday) {
        schedule.category = 'allday';
    } else if (chance.bool({likelihood: 30})) {
        schedule.category = SCHEDULE_CATEGORY[chance.integer({min: 0, max: 1})];
        if (schedule.category === SCHEDULE_CATEGORY[1]) {
            schedule.dueDateClass = 'morning';
        }
    } else {
        schedule.category = 'time';
    }

    startDate.add(chance.integer({min: 0, max: diffDate}), 'days');
    startDate.hours(chance.integer({min: 0, max: 23}))
    startDate.minutes(chance.bool() ? 0 : 30);
    schedule.start = startDate.toDate();

    endDate = moment(startDate);
    if (schedule.isAllday) {
        endDate.add(chance.integer({min: 0, max: 3}), 'days');
    }

    schedule.end = endDate
        .add(chance.integer({min: 1, max: 4}), 'hour')
        .toDate();

    if (!schedule.isAllday && chance.bool({likelihood: 20})) {
        schedule.goingDuration = chance.integer({min: 30, max: 120});
        schedule.comingDuration = chance.integer({min: 30, max: 120});;

        if (chance.bool({likelihood: 50})) {
            schedule.end = schedule.start;
        }
    }
}

function generateNames() {
    var names = [];
    var i = 0;
    var length = chance.integer({min: 1, max: 10});

    for (; i < length; i += 1) {
        names.push(chance.name());
    }

    return names;
}
//요쯤 어딘가에서 아작스로 스케줄을 갖고와서 해야하지않을까,,,!???!? 
function generateRandomSchedule(calendar, renderStart, renderEnd) {

    var schedule = new ScheduleInfo();
  
    
    
    
    ScheduleList.push(schedule);
}

//viewName
//renderStart
//renderEnd
var count =1;
function generateSchedule(viewName, renderStart, renderEnd) {
    ScheduleList = [];
    alert("들어왔얼!!");
    CalendarList.forEach(function(calendar) {
        var i = 0, length = 3;
        if (viewName === 'month') {
            length = 3;
        } else if (viewName === 'day') {
            length = 4;
        }

//        for (; i < length; i += 1) {
            generateRandomSchedule(calendar, renderStart, renderEnd);
//        }
    });
}
