 package com.min.sc.work.bean;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.min.sc.work.dtos.WorkRecordDto;


public class InputList {

	private List<WorkRecordDto> lists;

	
	public void setLists(List<WorkRecordDto> lists) {
		this.lists = lists;
	}

	// 출력 리스트 폼
	private String listForm(WorkRecordDto dto) {
		StringBuffer sb = new StringBuffer();
		// user의 기본 colspan 값                                                                                                                                                                      
		sb.append("   <tr>" );
		sb.append("   <td><input type='checkbox' name='chkVal' value='"+dto.getDay()+"'> </td>" );
		sb.append("   <td>"+dto.getSeq()+"</td>" );
		sb.append("   <td>"+dto.getDay()+"</td>" );
		sb.append("   <td>"+dto.getUser_id()+"</td>" );
		sb.append("   <td>"+dto.getEmployee_rank()+"</td>" );
		sb.append("   <td><a onclick='startModify(\""+dto.getSeq()+"\")'>"+dto.getStartDay()+"</a></td>" );
		sb.append("   <td>"+dto.getStartState()+"</td>" );
		sb.append("   <td><a onclick='endModify(\""+dto.getSeq()+"\")'>"+dto.getEndDay()+"</a></td>" );
		sb.append("   <td>"+dto.getEndState()+"</td>" );
		sb.append(" </tr>" );
		sb.append("");

		return sb.toString();
	}

	public String getListForm() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lists.size(); i++) {
			sb.append(listForm(lists.get(i)));
		}
		return sb.toString();
	}

}