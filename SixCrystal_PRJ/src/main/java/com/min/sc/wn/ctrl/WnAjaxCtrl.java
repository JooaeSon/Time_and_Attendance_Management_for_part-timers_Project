package com.min.sc.wn.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.min.sc.comm.dtos.BoardFileDTO;
import com.min.sc.comm.dtos.PagingDTO;
import com.min.sc.comm.model.IService_Comm;
import com.min.sc.wn.dtos.WNBoardDTO;
import com.min.sc.wn.model.IService_Wn;

@RestController
public class WnAjaxCtrl {
	
	@Autowired
	public IService_Wn service;
	
	@Autowired
	public IService_Comm cservice;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 

	
	/**
	 * 게시글을 수정할 시 modal에 들어갈 게시글의 내용을 불러온다.
	 * @param wn_seq
	 * @return map
	 */
	@RequestMapping(value = "adminMWnForm.do",method = RequestMethod.POST)
	public Map<String, Object> WnModifyForm(String wn_seq) {
		String bc ="WN";
		List<WNBoardDTO> list = service.wnBoardSelect(wn_seq);
		Map<String, Object> amap = new HashMap<String, Object>();
		amap.put("board_code", bc);
		amap.put("wn_seq", wn_seq);
		List<BoardFileDTO> flist = cservice.boardFileSelect(amap);
//		JSONObject jobj = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", list.get(0));
		if(flist.size()>0) {
		map.put("flist", flist);
		}
		return map;
	}
	
	/**
	 * 페이징 처리를 할때 사용되며 페이지에 관련된 dto를 json형태로 보내준다.
	 * @param session
	 * @param pdto
	 * @return json
	 */
	@RequestMapping(value = "/paging.do", method = RequestMethod.GET)
	public JSONObject page(HttpSession session, PagingDTO pdto) {
		logger.info("-------------------------------dto {}",pdto);
		JSONObject json = null;
	
		pdto.setTotal(service.wnboardListTotal());
		json = makeJSON(service.wnboardListPage(pdto),pdto);
		session.removeAttribute("page");
		session.setAttribute("page", pdto);
		System.out.println(json.toString());
//		System.out.println(json.toJSONString(map));
		
		return json;
	}
	
	
	/**
	 * 화면에 관련된, 즉 게시글에 관련된 내용과 페이징에 관련된 내용을 json으로 만들어주는 메소드
	 * @param lists
	 * @param page
	 * @return jsno
	 */
	private JSONObject makeJSON(List<WNBoardDTO> lists, PagingDTO page) {
		JSONArray jLists = new JSONArray(); // [{"":""},{"":""},{"":""}.....]
		JSONObject jdto = new JSONObject(); // {"":""}
		JSONObject json = new JSONObject(); // {"":[{"":""},{"":""},{"":""}.....]}
		//화면 리스트 관련
		Map<String, Object> map = new HashMap<String, Object>();
		for (WNBoardDTO  dto : lists) {
			jdto = new JSONObject();
			jdto.put("wn_seq", dto.getWn_seq());
			jdto.put("user_id", dto.getUser_id());
			jdto.put("wn_title", dto.getWn_title());
			jdto.put("wn_content", dto.getWn_content());
			jdto.put("wn_hit", dto.getWn_hit());
			jdto.put("wn_enabled", dto.getWn_enabled());
			jdto.put("wn_regdate", dto.getWn_regdate());
			jLists.add(jdto); // [{"":""},{"":""},{"":""}.....]
		}
		//페이지
		jdto = new JSONObject();// {"":""}
		jdto.put("pageList", page.getPageList());
		jdto.put("index", page.getIndex());
		jdto.put("pageNum", page.getPageNum());
		jdto.put("listNum", page.getListNum());
		jdto.put("total", page.getTotal());
		jdto.put("count", page.getCount());
		
		json.put("lists", jLists);
		json.put("page", jdto);
		
//		System.out.println(json.toString());
		
		return json;
	}
	
}
