package com.security.websocket.videogame.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Log4j2
@Controller
public class WebRTCController {
	
	public WebRTCController() {
		System.out.println("WebRTCController 호출");
	}
	
	// 방 안에서의 Peer 간 offer 정보 교환
    @MessageMapping("/room/{roomNo}/offer")
    @SendTo("/sub/room/{roomNo}/offer")
    public String handleOffer(@Payload String offer, @DestinationVariable("roomNo") String roomNo) {
        log.info("[Offer] Room No????: {}, Offer????: {}", roomNo, offer);
        System.out.println("번호 1");
        return offer;
    }

    // 방 안에서의 Peer 간 ICE Candidate 정보 교환
    @MessageMapping("/room/{roomNo}/ice-candidate")
    @SendTo("/sub/room/{roomNo}/ice-candidate")
    public String handleIceCandidate(@Payload String candidate, @DestinationVariable("roomNo") String roomNo) {
        log.info("[ICE Candidate] Room No::::: {}, Candidate::::: {}", roomNo, candidate);
        System.out.println("번호 2");
        return candidate;
    }

    // 방 안에서의 Peer 간 Answer 정보 교환
    @MessageMapping("/room/{roomNo}/answer")
    @SendTo("/sub/room/{roomNo}/answer")
    public String handleAnswer(@Payload String answer, @DestinationVariable("roomNo") String roomNo) {
        log.info("[Answer] Room No++++: {}, Answer++++: {}", roomNo, answer);
        System.out.println("번호 3");
        return answer;
    }

    // 방에 참여하기 위한 키 발송
    @MessageMapping("/call/key")
    @SendTo("/sub/call/key")
    public String callKey(@Payload String message) {
        log.info("[Key]==: {}", message);
        System.out.println("번호 4");
        return message;
    }

    // 모든 연결된 세션에 자신의 키를 보내기
    @MessageMapping("/send/key")
    @SendTo("/sub/send/key")
    public String sendKey(@Payload String message) {
        log.info("[Send Key]!!!!: {}", message);
        System.out.println("번호 5");
        return message;
    }
}