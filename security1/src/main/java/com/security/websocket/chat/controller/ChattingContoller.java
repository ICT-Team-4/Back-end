package com.security.websocket.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.websocket.chat.dto.ChatListDto;
import com.security.websocket.chat.service.ChatService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ChattingContoller {
	
	private ChatService chatService;
	
	public ChattingContoller(ChatService chatService) {
		this.chatService = chatService;
	}
	
	@GetMapping("/chat/list/{accountNo}")
	public ResponseEntity<List<ChatListDto>> chatList(@PathVariable int accountNo){
//		System.out.println(accountNo);
		List<ChatListDto> findByNoAll = chatService.findChatListByNo(accountNo);
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(findByNoAll);
	}
	
	

}
