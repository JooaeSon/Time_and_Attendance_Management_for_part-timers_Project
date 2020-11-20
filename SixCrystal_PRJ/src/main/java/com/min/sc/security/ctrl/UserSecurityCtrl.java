package com.min.sc.security.ctrl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.user.model.IService_User;

/**
 * 시큐리티 로그인만 담당하는 SecurityController
 * @author hwenc
 *
 */
public class UserSecurityCtrl implements UserDetailsService{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IService_User service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		log.info("UserSecurityCtrl 실행, usrename 확인 : \t {}",username);
		UserLoginDTO dto = service.login(username);
		
		if (dto == null) {
            throw new InternalAuthenticationServiceException(username);
      }
		
		// 유저의 권한을 담는 객체
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		// roles.add(new SimpleGrantedAuthority(유저의 권한 데이터 담기));
		// 유의점 ! 이때 유저의 권한을 담을때는 앞에 반드시 ROLE_를 붙여야만 시큐리티가 인식을 한다. ex> ROLE_Admin
		roles.add(new SimpleGrantedAuthority(dto.getUser_auth()));

		// 아이디, 비밀번호, 권한을 담은 사용자의 객체를 담아 생성
		log.info("UserSecurityCtrl 실행, usrename, password, auth 확인 : \t {} : {} : {}",username,dto.getUser_password(),roles);
		UserDetails user = new User(username,dto.getUser_password(),roles);
		
		return user;
	}
	
}
