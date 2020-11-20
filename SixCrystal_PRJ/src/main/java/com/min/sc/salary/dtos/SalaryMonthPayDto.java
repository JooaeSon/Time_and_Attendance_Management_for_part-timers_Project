package com.min.sc.salary.dtos;

public class SalaryMonthPayDto {
	
	private String dayshift;//주간근무시간
	private String monthlypay;//월급
	private String weeklypay;//시급
	private String basepay;//기본급
	private String weeklyholidaypay;//주휴수당
	private String insurancededucation;//4대보험공제액
	private String withholdingtax;//원천세
	private String aftertaxincome;//실수령액(근로자)
	private String aftertaxamount;//실지급액(사업자)
	private String tax;//세금(보험료포함)
	public String getDayshift() {
		return dayshift;
	}
	public void setDayshift(String dayshift) {
		this.dayshift = dayshift;
	}
	public String getMonthlypay() {
		return monthlypay;
	}
	public void setMonthlypay(String monthlypay) {
		this.monthlypay = monthlypay;
	}
	public String getWeeklypay() {
		return weeklypay;
	}
	public void setWeeklypay(String weeklypay) {
		this.weeklypay = weeklypay;
	}
	public String getBasepay() {
		return basepay;
	}
	public void setBasepay(String basepay) {
		this.basepay = basepay;
	}
	public String getWeeklyholidaypay() {
		return weeklyholidaypay;
	}
	public void setWeeklyholidaypay(String weeklyholidaypay) {
		this.weeklyholidaypay = weeklyholidaypay;
	}
	public String getInsurancededucation() {
		return insurancededucation;
	}
	public void setInsurancededucation(String insurancededucation) {
		this.insurancededucation = insurancededucation;
	}
	public String getWithholdingtax() {
		return withholdingtax;
	}
	public void setWithholdingtax(String withholdingtax) {
		this.withholdingtax = withholdingtax;
	}
	public String getAftertaxincome() {
		return aftertaxincome;
	}
	public void setAftertaxincome(String aftertaxincome) {
		this.aftertaxincome = aftertaxincome;
	}
	public String getAftertaxamount() {
		return aftertaxamount;
	}
	public void setAftertaxamount(String aftertaxamount) {
		this.aftertaxamount = aftertaxamount;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	
	
	//하 이 이후는 필요할 지모르게땅,,, 
	//보험료포함세율(%)
	//4대보험사업자부담액
	//4대보험 총 고지액
	
	public SalaryMonthPayDto() {
		// TODO Auto-generated constructor stub
	}
	public SalaryMonthPayDto(String dayshift, String monthlypay, String weeklypay, String basepay,
			String weeklyholidaypay, String insurancededucation, String withholdingtax, String aftertaxincome,
			String aftertaxamount, String tax) {
		super();
		this.dayshift = dayshift;
		this.monthlypay = monthlypay;
		this.weeklypay = weeklypay;
		this.basepay = basepay;
		this.weeklyholidaypay = weeklyholidaypay;
		this.insurancededucation = insurancededucation;
		this.withholdingtax = withholdingtax;
		this.aftertaxincome = aftertaxincome;
		this.aftertaxamount = aftertaxamount;
		this.tax = tax;
	}
	@Override
	public String toString() {
		return "SalaryMonthPayDto [dayshift=" + dayshift + ", monthlypay=" + monthlypay + ", weeklypay=" + weeklypay
				+ ", basepay=" + basepay + ", weeklyholidaypay=" + weeklyholidaypay + ", insurancededucation="
				+ insurancededucation + ", withholdingtax=" + withholdingtax + ", aftertaxincome=" + aftertaxincome
				+ ", aftertaxamount=" + aftertaxamount + ", tax=" + tax + "]";
	}
	
	
}