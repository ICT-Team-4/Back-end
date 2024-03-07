package com.security.util;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import jakarta.servlet.http.HttpSession;

public class NotificationUtil {

	private final SimpMessagingTemplate simpMessagingTemplate;
	
	public NotificationUtil(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate=simpMessagingTemplate;
	}
	
	// 클라이언트가 로그인할 때 호출되는 메소드
    public void handleLogin(HttpSession session) {
        // 세션에 사용자의 ID를 저장
        session.setAttribute("userId", "사용자ID");
    }
    
    // 클라이언트가 로그아웃할 때 호출되는 메소드
    public void handleLogout(HttpSession session) {
        // 세션에서 사용자의 ID를 삭제
        session.removeAttribute("userId");
    }
    
    // 요청이 올 때 해당 사용자의 온라인 상태를 확인하여 알림을 전송하는 메소드
    public void handleRequest(HttpSession session, String topic, String message) {
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            // 사용자가 로그인 상태인 경우에만 알림을 전송
        	simpMessagingTemplate.convertAndSendToUser(userId, topic, message);
        }
    }
	
}
