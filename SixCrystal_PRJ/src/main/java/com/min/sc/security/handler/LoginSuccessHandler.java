package com.min.sc.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.user.model.IService_User;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IService_User service;
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
	   
	private String loginidname;
	private String defaultUrl;
	
	public String getLoginidname() {
		return loginidname;
	}

	public void setLoginidname(String loginidname) {
		this.loginidname = loginidname;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		  System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ : 로그인 성공?");
	      System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ : 기본 mapping url"+defaultUrl);
	      //입력 아이디
	      String user_id = ((UserDetails)authentication.getPrincipal()).getUsername();
	      
	      System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ : 사용자 id >"+user_id);
	      
	      // 로그인 정보 가져오기
	      UserLoginDTO dto = service.getLoginUser(user_id);
	      
	      // 사용자 정보 세션에 담기, 에러세션 지우기
	      clearErrorSession(request, dto);
	      
	      // 로그인 성공날짜 업데이트
	      service.loginUpdate(user_id);
		
	      resultRedirectStrategy(request, response, authentication, dto);
	}
	
	protected void resultRedirectStrategy(HttpServletRequest req, HttpServletResponse resp,
	         Authentication authentication, UserLoginDTO dto) throws IOException, ServletException {

	      SavedRequest savedRequest = requestCache.getRequest(req, resp);
	      
	      System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ : 사용자 권한은 > " + dto.getUser_auth());
	      if(dto.getUser_delflag().equalsIgnoreCase("T")) {
	    	  System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ : 탈퇴한 회원 > " + dto.getUser_id()+" / "+dto.getUser_delflag());
	          redirectStratgy.sendRedirect(req, resp, "/invalidUser.do");
	       }else if(savedRequest!=null) {
	         String targetUrl = savedRequest.getRedirectUrl();
	         System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ : 이동 주소 > "+targetUrl);
	         redirectStratgy.sendRedirect(req, resp, targetUrl);
	      } else {
	         redirectStratgy.sendRedirect(req, resp, defaultUrl);
	      }
	}
	
	@SuppressWarnings("unused")
	   private void clearErrorSession(HttpServletRequest req, UserLoginDTO dto) {
	      HttpSession session = req.getSession();
	      req.removeAttribute("ERRORMSG");
	      
	      if(dto.getUser_auth().equals("ROLE_Admin")) {
	    	  System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ 관리자 정보 세션에 담기 : UserLoginDTO");
	    	  System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ 세션 정보 : "+dto);
	    	  session.setAttribute("user", dto);
	      }else if(dto.getUser_auth().equals("ROLE_User")){
	    	  System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ 사용자 정보 세션에 담기 : UserInfoDTO");
	    	  UserInfoDTO uDto = service.loginInfo(dto.getUser_id());
	    	  System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ 세션 성보 : "+uDto);
	    	  //유저 세션 담기
	    	  session.setAttribute("user", uDto);
	      }
	      
	      String error = (String) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	      System.out.println("▒▒▒▒▒▒▒▒▒▒로그인 성공 핸들러▒▒▒▒▒▒▒▒▒▒ : 에러가 세션에 담겨있는가 ? >"+error);
	      
	      if(session==null) return;
	      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	   }

}
