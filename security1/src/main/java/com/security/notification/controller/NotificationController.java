package com.security.notification.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.notification.dto.NotificationDto;
import com.security.notification.service.NotificationService;
import com.security.util.JWTOkens;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@Log4j2
public class NotificationController {
    
    private NotificationService notificationService;
    
    private final SimpMessagingTemplate messagingTemplate;
    
    public NotificationController(NotificationService notificationService, SimpMessagingTemplate messagingTemplate) {
        this.notificationService = notificationService;
        this.messagingTemplate = messagingTemplate;
    }
    
    @MessageMapping("/sendNotification")
    public void sendNotification(NotificationDto notification) {
        System.out.println(notification.getMessage());
        messagingTemplate.convertAndSend("/sub/topic/notification", notification);
    }
    
    @PutMapping("/notifications/online")
    public ResponseEntity<String> onLine(HttpServletRequest request) {
    	
    	String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String accountNo = payload.get("sub").toString();
    	
    	int flag = 0;
    	String message = "";
    	
    	flag = notificationService.updateStatus(accountNo);
    	
    	if(flag == 1) {
    		log.info("온라인 상태로 변환 성공");
    		message="성공";
    	} else {
    		log.warn("온라인 상태로 변환 실패");
    		message="실패";
    	}
    	
    	return ResponseEntity.ok().header("Content-Type", "application/json").body(message);
    }
    
    @PutMapping("/notification/offline")
    public ResponseEntity<String> offLine(HttpServletRequest request) {
    	
    	String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String accountNo = payload.get("sub").toString();
    	
    	int flag = 0;
    	String message = "";
    	
    	flag = notificationService.updateStatusOff(accountNo);
    	
    	if(flag == 1) {
    		log.info("오프라인 상태로 변환 성공");
    		message="성공";
    	} else {
    		log.warn("오프라인 상태로 변환 실패");
    		message="실패";
    	}
    	
    	return ResponseEntity.ok().header("Content-Type", "application/json").body(message);
    }
}
