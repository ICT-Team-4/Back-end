package com.ict.fitme;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketServer extends TextWebSocketHandler {
	
	private Map<String, WebSocketSession> clients = new HashMap<>();

	// 클라이언트와 연결이 되었을 때 호출되는 콜백 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//-컬렉션에 연결된 클라이언트 추가
		clients.put(session.getId(), session);
		System.out.println(session.getId()+"연결 되었습니다");
	}
	
	// 통신 장애 시 발생
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println(session.getId()+"와 통신장애 발생:"+exception.getMessage());
	}
	
	// 클라이언트와 연결 종료 되었을 때 호출되는 콜백 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//-컬렉션에 저장된 클라이언트 삭제
		clients.remove(session.getId());
		System.out.println(session.getId()+"연결이 끊어졌어요");
	}

}
