CREATE TABLE WORKMANAGE (
	SCH_CODE	NUMBER(8)	NOT NULL,
	WS_CODE		VARCHAR2(50)	NOT NULL,
	USER_ID		VARCHAR2(50)	NOT NULL,
	EMPLOYEE_RANK	VARCHAR2(10)	NULL,
	SCH_MONTH	VARCHAR2(10)	NULL,
	WORK_CHECK	VARCHAR2(4000)	NULL
);

--DROP TABLE WORKMANAGE;

ALTER TABLE WORKMANAGE ADD CONSTRAINT PK_WORKMANAGE PRIMARY KEY (
	SCH_CODE,
	WS_CODE,
	USER_ID
);

SELECT * FROM SCHEDULE s  ;

SELECT * FROM WORKMANAGE w ;

UPDATE SCHEDULE SET SCH_SCHEDULE ='[{"day":15,"schedule":[{"isAllDay":false,"category":"time","isReadOnly":false,"calendarId":2,"state":"Busy","id":"396127572","title":"hong","start":"2020-06-15T12:00","end":"2020-06-15T15:00"},{"title":"song","isAllDay":false,"category":"time","isReadOnly":false,"calendarId":1,"state":"Busy","id":"92126195","start":"2020-06-15T15:30","end":"2020-06-15T18:30"}]},{"day":18,"schedule":[{"title":"gang","isAllDay":false,"category":"time","isReadOnly":false,"calendarId":1,"state":"Busy","id":"188805505","start":"2020-06-18T02:20","end":"2020-06-18T20:00"},{"id":"323107419","title":"joojoo1234","isAllDay":false,"start":"2020-06-18T14:00","end":"2020-06-18T20:00","category":"time","isReadOnly":false,"calendarId":2,"state":"Busy"}]},{"day":16,"schedule":[{"isAllDay":false,"category":"time","isReadOnly":false,"calendarId":2,"state":"Busy","id":"179703121","title":"hong","start":"2020-06-16T13:00","end":"2020-06-16T18:00"},{"title":"song","isAllDay":false,"category":"time","isReadOnly":false,"calendarId":1,"state":"Busy","id":"940284618","start":"2020-06-16T16:30","end":"2020-06-16T20:30"}]},{"day":19,"schedule":[{"isAllDay":false,"category":"time","isReadOnly":false,"calendarId":2,"state":"Busy","id":"440136604","title":"hong","start":"2020-06-19T13:00","end":"2020-06-19T18:00"}]},{"day":17,"schedule":[{"title":"gang","isAllDay":false,"category":"time","isReadOnly":false,"calendarId":1,"state":"Busy","id":"470114563","start":"2020-06-17T17:30","end":"2020-06-17T21:30"}]},{"day":14,"schedule":[{"id":"167546536","title":"hyohyo","isAllDay":false,"start":"2020-06-14T22:00","end":"2020-06-15T00:00","category":"time","isReadOnly":false,"calendarId":"2","state":"Busy"}]}]';

UPDATE SCHEDULE SET SCH_SCHEDULE ='[{"day":18,"schedule":[{"id":"34274254","title":"joojoo1234","isAllDay":false,"start":"2020-06-18T20:00","end":"2020-06-18T23:30","category":"time","isReadOnly":false,"calendarId":1,"state":"Busy"},
{"id":"34274254","title":"eunheeyo","isAllDay":false,"start":"2020-06-18T20:00","end":"2020-06-18T23:30","category":"time","isReadOnly":false,"calendarId":2,"state":"Busy"}]},
{"day":24,"schedule":[{"id":"396107141","title":"employee01","isAllDay":false,"start":"2020-06-24T01:00","end":"2020-06-24T06:00","category":"time","isReadOnly":false,"calendarId":1,"state":"Busy"}]},
{"day":25,"schedule":[{"id":"22380414","title":"employee01","isAllDay":false,"start":"2020-06-25T03:00","end":"2020-06-25T08:00","category":"time","isReadOnly":false,"calendarId":1,"state":"Busy"}]}]';

DELETE FROM WORKMANAGE w WHERE USER_ID ='joojoo1234';

SELECT * FROM SCHTEST s ;

SELECT * FROM WORKSPACE w;
SELECT * FROM USERINFO u ;
SELECT * FROM EMPLOYEE;