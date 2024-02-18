package com.security.websocket.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.util.JWTOkens;
import com.security.websocket.chat.dto.ChatCommentListDto;
import com.security.websocket.chat.dto.ChatListDto;
import com.security.websocket.chat.dto.ChatRoomDto;
import com.security.websocket.chat.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ChattingContoller {
	
	private ChatService chatService;
	
	@Autowired
	public ChattingContoller(ChatService chatService) {
		this.chatService = chatService;
	}
	
	@GetMapping("/chat/list/{accountNo}")
	public ResponseEntity<List<ChatListDto>> chatList(@PathVariable int accountNo){
		System.out.println("accountNo:"+accountNo);
		List<ChatListDto> findByNoAll = chatService.findChatListByNo(accountNo);
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(findByNoAll);
	}
	@GetMapping("/chat/list/room/{chattingNo}")
	public ResponseEntity<List<ChatCommentListDto>> chatComments(@PathVariable int chattingNo){
		List<ChatCommentListDto> findCommentByNo = chatService.findChatCommentByNo(chattingNo);
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(findCommentByNo);
	}
	
	@DeleteMapping("/chat/list/room/{chattingNo}")
	public ResponseEntity<String> deleteChatRoom(@PathVariable int chattingNo, HttpServletRequest request){
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String accountNo = payload.get("sub").toString();
		
		ChatRoomDto dto = chatService.findChatByNo(chattingNo);
		if(!accountNo.equalsIgnoreCase(String.valueOf(dto.getAccountNo()))) {
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body("삭제실패(동일회원X):"+accountNo.equalsIgnoreCase(accountNo));
		}
		String msg = "성공적으로 삭제되었습니다";
		int num = chatService.chatDelete(chattingNo);
		if(num == 0) msg ="실패";
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(String.valueOf(num));
		
	}
	

}
