package com.security.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.notification.dao.NotificationMapper;
import com.security.notification.dto.OnlineStatusDto;

@Service
public class NotificationService {
	
	private NotificationMapper notificationMapper;
	
	public NotificationService(NotificationMapper notificationMapper) {
		this.notificationMapper = notificationMapper;
	}
	
	//로그인 시 온라인 상태로 변경
	@Transactional
	public int updateStatus(String accountNo) {
		return notificationMapper.updateStatus(accountNo);
	}
	
	//로그아웃 시 오프라인 상태로 변경
	@Transactional
	public int updateStatusOff(String accountNo) {
		return notificationMapper.updateStatusOff(accountNo);
	}

}
