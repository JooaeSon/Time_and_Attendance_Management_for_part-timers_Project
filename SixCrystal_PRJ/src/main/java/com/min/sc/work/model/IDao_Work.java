package com.min.sc.work.model;

import java.util.List;
import java.util.Map;

import com.min.sc.work.dtos.WMDto;

public interface IDao_Work {

	public String workInfoSelect(Map<String, Object> map);
	
	public Map<String, Object> workInfoSet(Map<String, Object> map);
	
	public boolean workInfobasic_Insert(Map<String, Object> map);
	
	public String selectWorkcheck(Map<String, Object> map);
	
	public boolean updateDaily(Map<String, Object> map);
	
	public String selectPersonal(Map<String, Object> map);
	
	public List<WMDto> selectLowRank(Map<String, Object> map);
	
	public String wifiInfoSelect(String work_code);
	
	public boolean deleteWorkRecord(String deldate);
	
}
