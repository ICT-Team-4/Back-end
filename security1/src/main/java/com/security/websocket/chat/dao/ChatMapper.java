package com.security.websocket.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.security.websocket.chat.dto.ChatListDto;
import com.security.websocket.dto.ChatDto;

@Mapper
public interface ChatMapper {

	public List<ChatListDto> findChatListByNo(int accountNo);
	
	public int insertChat(ChatDto dto);
	
}
