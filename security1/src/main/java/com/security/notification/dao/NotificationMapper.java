package com.security.notification.dao;

import org.apache.ibatis.annotations.Mapper;

import com.security.notification.dto.OnlineStatusDto;

@Mapper
public interface NotificationMapper {
	
	//로그인 성공 시 온라인으로 상태 변경
	int updateStatus(String accountNo);
	
	//로그아웃 시 오프라인으로 상태 변경
	int updateStatusOff(String accountNo);

}
