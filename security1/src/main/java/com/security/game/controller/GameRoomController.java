package com.security.game.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.game.dto.GameRoomDto;
import com.security.game.service.GameRoomService;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin
@Log4j2
@RequestMapping("/api/v1")
public class GameRoomController {
	
	@Autowired
    private GameRoomService gameRoomService;

    @PostMapping("/game/createRoom")
    public ResponseEntity<?> createGameRoom(@RequestBody Map<String, String> requestBody) {
        String accountNo = requestBody.get("accountNo");
        log.info("게임 방 생성 요청 - accountNo: {}", accountNo);
        Map<String, Object> roomDetails = gameRoomService.createAndRedirectGameRoom(accountNo);        
        
        log.info("게임 방 생성 완료 컨트롤러 - roomDetails: {}", roomDetails);
        return ResponseEntity.ok(roomDetails);
    }
}