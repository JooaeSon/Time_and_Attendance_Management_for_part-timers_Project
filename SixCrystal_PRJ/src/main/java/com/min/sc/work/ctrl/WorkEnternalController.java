 package com.min.sc.work.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.sc.emp.dtos.EmpUserDTO;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.ws.dtos.WorkSpaceDTO;


@Controller
public class WorkEnternalController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	ServletContext context;
	
	@RequestMapping(value="/init.do", method=RequestMethod.GET)
	public String firstPage(Model model) {
		
		return "work/init";
	}
	
	
	public static Map<String, Object> DayMap = new HashMap<String, Object>();//날짜:
	public static Map<String, Object> schmap=new HashMap<String, Object>();
	

	@RequestMapping(value="/workcheck.do", method=RequestMethod.GET)
	public String workCheckPage(Model model, HttpSession session) throws ParseException, java.text.ParseException {
		
		
		return "work/workcheck";
	}
	

	

	
}
	
