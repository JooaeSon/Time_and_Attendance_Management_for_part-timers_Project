package com.min.sc.sch.util;


import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.min.sc.salary.dtos.SalaryMonthPayDto;

@Component
public class ExcelProcess {

//	@Autowired
//	public IServiceBoard query;
	/**
     * 게시글 리스트를 간단한 엑셀 워크북 생성
     * @param  list
     * @return workbook
     */
	public Workbook makeExcelWorkbook(JsonArray scheduleArray) {
        
		//이걸 갖고오려면 일단 수정해야할게
		//디비프로퍼티 sc로 바꾸고 dd
		//디티오 내꺼 갖고오고ㅇㅇ
		//컨피그레이션 알리아스 다시잡고ㅇㅇ
		//스테이트먼트 필요한 쿼리 갖고오고ㅇㅇ
		//등록한 쿼리 실행할 수 있게 모델 바꾸고  dd
		//그리고 여기 워크북에다가 셀렉트고 갖고와서 각 행마다 어떤 데이터 끌고 올건지 해야겠다
//		JsonObject json= new JsonObject();
		
		//workbook생성 (엑셀 파일 생성)
		Workbook workbook = new XSSFWorkbook();
        
        // 시트 생성 (엑셀 sheet 생성)
        Sheet sheet = workbook.createSheet("일정 목록");

        // 헤더 행 생 createRow(선택된 시트 안에 로우를 생성 안에 들어가는 값은 Row의 인덱스)
        Row headerRow = sheet.createRow(0);
        // 해당 행의 첫번째 열 셀 생성 createCell(선택된 로우에서의 셀 생성 인덱스)
        Cell headerCell = headerRow.createCell(0);
        // 선택된 셀에 값을 입력한다
        headerCell.setCellValue("날짜");
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("아이디");
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("직급");
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("근무시작시각");
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("근무종료시각");
        
      
      
        //이따가 수정해야겠다!! 1번이면 직원 2번이면 매니저로하려면 이따가 런에즈해서 
        //색 업데이트 싸악 바꾸고
        
        // 게시글 행 및 셀 생성
        Row bodyRow = null;
        Cell bodyCell = null;
//        for(int i=0; i<list.size(); i++) {
//            BoardDTO dto = list.get(i);
//            
//            // 행 생성
//            bodyRow = sheet.createRow(i+1);
//            // 데이터 번호 표시
//            bodyCell = bodyRow.createCell(0);
//            bodyCell.setCellValue(i + 1);
//            // 데이터 제목 표시
//            bodyCell = bodyRow.createCell(1);
//            bodyCell.setCellValue(dto.getTitle());
//            // 데이터 아이디 표시
//            bodyCell = bodyRow.createCell(2);
//            bodyCell.setCellValue(dto.getId());
//            // 데이터 작성일 표시
//            bodyCell = bodyRow.createCell(3);
//            bodyCell.setCellValue(dto.getRegdate());

        
//        }
        System.out.println(scheduleArray);
        for (int i = 0; i < scheduleArray.size(); i++) {
        	 // 행 생성
          bodyRow = sheet.createRow(i+1);
//           날짜 scheduleArray.get(i).getAsJsonObject().get("start");
          bodyCell = bodyRow.createCell(0);
          bodyCell.setCellValue(scheduleArray.get(i).getAsJsonObject().get("start").getAsString().substring(0, 10));
        	
//          // 직원 아이디 scheduleArray.get(i).getAsJsonObject().get("title");
          bodyCell = bodyRow.createCell(1);
          bodyCell.setCellValue(scheduleArray.get(i).getAsJsonObject().get("title").toString());
        	
//          //직급  scheduleArray.get(i).getAsJsonObject().get("calendarId");
          bodyCell = bodyRow.createCell(2);
          bodyCell.setCellValue((scheduleArray.get(i).getAsJsonObject().get("calendarId").toString()).equals("2")?"매니저":"직원");
        	
//          //근무시작시각 scheduleArray.get(i).getAsJsonObject().get("start");
          bodyCell = bodyRow.createCell(3);
          bodyCell.setCellValue(scheduleArray.get(i).getAsJsonObject().get("start").toString().substring(12,17));
        	
//			  //근무종료시각 scheduleArray.get(i).getAsJsonObject().get("end");
			  bodyCell = bodyRow.createCell(4);
			  bodyCell.setCellValue(scheduleArray.get(i).getAsJsonObject().get("end").toString().substring(12,17));        
		}
        
        //시트 열 너비 자동 설정 (시트 열 너비는 값이 들어간 다음에 자동으로 설정해주어야한다.)
        //SXXSF 객체의 workbook을 사용할 시 자동을 사이즈를 지정할 수 없다.
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        
        //정렬하고싶은데,,, 그거 어떻게 해야하지,,? ? ,,,?  
      
        return workbook;
    }
	
	
	// 난 이거 안하니까 상콤하게 재껴볼까^^
	/**
	 * 업로드 엑셀파일 읽어오기
	 * @param excelFile
	 * @return list
	 */
	public List<SalaryMonthPayDto> uploadExcelFile(MultipartFile excelFile){
		List<SalaryMonthPayDto> list = new ArrayList<SalaryMonthPayDto>();
        try {
        	/*
        	* 파일 읽을 시 3가지 방식 
        	* 1. FileInputStream으로 읽을 시 
        	*Excel 2007 이상인 경우
        	*
        	*FileInputStream file = new FileInputStream(new File("C:/temp/test.xlsx"));
        	*XSSFWorkbook workbook = new XSSFWorkbook(file);
			*
        	*Excel 97 ~ 2003 버전인 경우
        	*FileInputStream file = new FileInputStream(new File("C:/temp/test.xls"));
        	*HSSFWorkbook workbook_old = new HSSFWorkbook(file);
        	*
        	* 2. File객체를 생성할 시
			*Workbook workbook = WorkbookFactory.create(new File("C:/temp/test.xlsx")); //Excel 97 ~ 2007 모두 가능
			*
			* 3.OPCPackage를 사용해서 읽어들이는 방법 (뭔지 잘 몰라서 써봤음)
			* 
			*OPCPackage opcPackage = OPCPackage.open(new File("C:/temp/test.xlsx"));
			*XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
        	*
        	*/
            OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            
            // 첫번째 시트 불러오기
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            for(int i=4; i<sheet.getLastRowNum() - 3; i++) {
            	SalaryMonthPayDto dto = new SalaryMonthPayDto();
                XSSFRow row = sheet.getRow(i);
                
                // 행이 존재하기 않으면 패스
                if(null == row) {
                    continue;
                }
                
                // 행의 두번째 열(이름부터 받아오기) //dayShift;//주간근무시간
                XSSFCell cell = row.getCell(0);
                if(null != cell) {
                	dto.setDayshift(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 monthlyPay;//월급
                cell = row.getCell(1);
                if(null != cell) {
                	dto.setMonthlypay(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 weeklyPay;//시급
                cell = row.getCell(2);
                if(null != cell) {
                	dto.setWeeklypay(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 weeklyPay;//시급
                cell = row.getCell(3);
                if(null != cell) {
                	dto.setWeeklypay(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 basePay;//기본급
                cell = row.getCell(4);
                if(null != cell) {
                	dto.setBasepay(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 weeklyHolidayPay;//주휴수당
                cell = row.getCell(5);
                if(null != cell) {
                	dto.setWeeklyholidaypay(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 insuranceDeducation;//4대보험공제액
                cell = row.getCell(6);
                if(null != cell) {
                	dto.setInsurancededucation(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 withholdingTax;//원천세
                cell = row.getCell(7);
                if(null != cell) {
                	dto.setWithholdingtax(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 afterTaxIncome;//실수령액(근로자)
                cell = row.getCell(8);
                if(null != cell) {
                	dto.setAftertaxincome(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 afterTaxAmount;//실지급액(사업자)
                cell = row.getCell(9);
                if(null != cell) {
                	dto.setAftertaxamount(String.valueOf((int)(cell.getNumericCellValue())));
                }
                
                // 행의 세번째 열 받아오기 tax;//세금(보험료포함)
                cell = row.getCell(10);
                if(null != cell) {
                	dto.setTax(String.valueOf((int)(cell.getNumericCellValue())));
                }
               
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

	
}
