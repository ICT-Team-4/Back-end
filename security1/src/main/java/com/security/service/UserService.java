package com.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.security.model.UserDto;
import com.security.model.UserMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {
	
	  private UserMapper usermapper;
	  private PasswordEncoder passwordEncoder;
	
	
	  public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
	    this.usermapper = userMapper;
	    this.passwordEncoder = passwordEncoder;
	
	  }
	
	  @Transactional
	  public int insertMember(UserDto dto) {
		  int member = usermapper.insertMember(dto);
		  
		  UserDto bringNo = usermapper.findAccountByUsername(dto.getUsername());
		  long accountNo = bringNo.getAccountNo();

		  dto.setAccountNo(accountNo);
		  int bodysize = usermapper.insertMemberInBody(dto);
		  
	    return member + bodysize ;
	  }
	 
	  @Transactional
	  public int insertSocial(UserDto dto) {
		  log.info(dto);
		  usermapper.insertMember(dto);
		  
		  return usermapper.insertSocial(dto);
	  }
		
	
	  public UserDto findAccountByUsername(String username) {
		  UserDto userDto = usermapper.findAccountByUsername(username);
		  System.out.println("userDto: " + userDto);
	    return userDto;
	  }
	  
	  public UserDto findMemberInfoByUsername(String username) {
		  UserDto userDto = usermapper.findAccountByUsername(username);
		  System.out.println("userDto: " + userDto);
	    return userDto;
	  }
	  
	  @Transactional
	  public void deactivateAccountByAccountNo(long accountNo) {
	    usermapper.leaveMember(accountNo);
	  }

}
