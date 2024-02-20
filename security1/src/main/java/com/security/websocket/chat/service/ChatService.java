package com.security.websocket.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.security.websocket.chat.dao.ChatMapper;
import com.security.websocket.chat.dto.ChatListDto;
import com.security.websocket.dto.ChatDto;

@Service
public class ChatService {
	
	private ChatMapper chatMapper;
	public ChatService(ChatMapper chatMapper) {
		this.chatMapper = chatMapper;
	}
	public List<ChatListDto> findChatListByNo(int accountNo) {
		return chatMapper.findChatListByNo(accountNo);
	}
	//채팅 dlqfur
	public int insert(ChatDto dto) {
		return chatMapper.insertChat(dto);
	}
	
}
