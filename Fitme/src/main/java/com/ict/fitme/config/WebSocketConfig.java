package com.ict.fitme.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ict.fitme.WebSocketServer;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	private WebSocketServer webSocketServer;
	
	public WebSocketConfig(WebSocketServer webSocketServer) {
		this.webSocketServer = webSocketServer;
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketServer, "/friend-ws").setAllowedOrigins("*");
	}

}
