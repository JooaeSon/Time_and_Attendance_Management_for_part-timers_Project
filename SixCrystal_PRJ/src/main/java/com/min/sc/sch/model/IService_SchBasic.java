package com.min.sc.sch.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.min.sc.sch.dtos.SchBasicDTO;

public interface IService_SchBasic {

	public boolean schBasicInsert(SchBasicDTO sbdto);
	
	public boolean schBasicUpdate(int seq, String schbasic_json);
	
	public SchBasicDTO schBasicSelect(String wscode, Date record);
	
	public List<SchBasicDTO> schBasicListSelect(Map<String,Object> map);
	
	public SchBasicDTO schBasicChkSelect(Map<String,Object> map);
	
	public List<SchBasicDTO> schBasicSelAll();
	
	public boolean schBasicDel(int schbasic_seq);
	
}
