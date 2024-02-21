package com.security.websocket.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.websocket.chat.dao.ChatMapper;
import com.security.websocket.chat.dto.ChatCommentListDto;
import com.security.websocket.chat.dto.ChatFriendsDto;
import com.security.websocket.chat.dto.ChatListDto;
import com.security.websocket.chat.dto.ChatMemberDto;
import com.security.websocket.chat.dto.ChatRoomDto;
import com.security.websocket.chat.dto.ChatRoomFriendsDto;
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
	public List<ChatCommentListDto> findChatCommentByNo(int chattingNo){
		return chatMapper.findChatCommentByNo(chattingNo);
	}
	public List<ChatMemberDto> findChatRoomMemberByChatNo(int chattingNo){
		return chatMapper.findChatRoomMemberByChatNo(chattingNo);
	}
	
	//채팅 dlqfur
	public int insert(ChatDto dto) {
		return chatMapper.insertChat(dto);
	}
	//채팅 초대시 친구 확인
	public List<ChatFriendsDto> findChatFriendsByChatNo(ChatDto dto){
		return chatMapper.findChatFriendsByChatNo(dto);
	}
	
	public ChatRoomDto findChatByNo(int chattingNo) {
		return chatMapper.findChatByNo(chattingNo);
	}
	
	@Transactional
	public int chatRoomSave(ChatRoomFriendsDto dto) {
		int flag = 0;
		if(dto.getAccountNo() == null) {
			String[] friends = dto.getFriends();
			for(String accountNo: friends) {
				System.out.println(">>>"+accountNo);
				dto.setAccountNo(accountNo);
				chatMapper.chatRoomSave(dto);
				flag++;
			}
		}else {
			String accountNo = dto.getAccountNo();
			System.out.println(">>>"+accountNo);
			return chatMapper.chatRoomSave(dto);
		}
		
		return flag;
	}
	
	@Transactional
	public int chatDelete(int chattingNo) {
		return chatMapper.delete(chattingNo);
	}
	
}
