package com.security.websocket.videogame.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class WebRTCController {
	@MessageMapping("/call")
	@SendTo("/sub/messages")
	public String sendMessage(String message) {
		return message;
	}
}