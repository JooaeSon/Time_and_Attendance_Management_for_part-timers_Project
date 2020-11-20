package com.min.sc.sch.dtos;

public class SchDTO {
   private int sch_code       ;
   private String ws_code       ;
   private String sch_month     ;
   private String sch_schedule  ;
   private String sch_reviseable;
   public int getSch_code() {
      return sch_code;
   }
   public void setSch_code(int sch_code) {
      this.sch_code = sch_code;
   }
   public String getWs_code() {
      return ws_code;
   }
   public void setWs_code(String ws_code) {
      this.ws_code = ws_code;
   }
   public String getSch_month() {
      return sch_month;
   }
   public void setSch_month(String sch_month) {
      this.sch_month = sch_month;
   }
   public String getSch_schedule() {
      return sch_schedule;
   }
   public void setSch_schedule(String sch_schedule) {
      this.sch_schedule = sch_schedule;
   }
   public String getSch_reviseable() {
      return sch_reviseable;
   }
   public void setSch_reviseable(String sch_reviseable) {
      this.sch_reviseable = sch_reviseable;
   }
   @Override
   public String toString() {
      return "SchDTO [sch_code=" + sch_code + ", ws_code=" + ws_code + ", sch_month=" + sch_month + ", sch_schedule="
            + sch_schedule + ", sch_reviseable=" + sch_reviseable + "]";
   }
   
   public SchDTO() {
      // TODO Auto-generated constructor stub
   }
   public SchDTO(int sch_code, String ws_code, String sch_month, String sch_schedule, String sch_reviseable) {
      super();
      this.sch_code = sch_code;
      this.ws_code = ws_code;
      this.sch_month = sch_month;
      this.sch_schedule = sch_schedule;
      this.sch_reviseable = sch_reviseable;
   }
   
   
   

}