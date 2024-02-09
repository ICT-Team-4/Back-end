package com.security.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  int insertMember(UserDto dto);
  
  int insertSocial(UserDto dto);
  
  int insertMemberWithSocial(UserDto dto);
  
  int insertMemberInBody(UserDto dto);
  
  
  UserDto findAccountByUsername(String username);

  UserDto findAccountByProviderId(String providerId);
  
  UserDto findMemberInfoByUsername(String username);
  
  UserDto findAccountByAccountNo(String accountNo);
  
  void leaveMember(long accountNo);
  
}
