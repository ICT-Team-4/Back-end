package com.security.game.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.security.game.dto.GameRoomDto;
import com.security.game.service.GameRoomService;
import com.security.util.JWTOkens;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin
@SessionAttributes("MapListGame")
@RequestMapping("/api/v1")
public class GameRoomController {
	
//	@Autowired
    private GameRoomService gameRoomService;
    
    @Autowired
    public GameRoomController(GameRoomService gameRoomService) {
    	this.gameRoomService = gameRoomService;
    }

    @PostMapping("/game/createRoom/{mode}")
    public ResponseEntity<GameRoomDto> createGameRoom(@PathVariable String mode, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
        String accountNo = payload.get("sub").toString();

        GameRoomDto dto = new GameRoomDto();
        dto.setAccountNo(accountNo);
        dto.setGameMode(mode);

        System.out.println("accountNo : " + accountNo + " , mode : " + mode);

        // 게임룸 생성 및 리다이렉션 (서비스 호출 전에 세션 로직 제거)
        GameRoomDto roomDetails = gameRoomService.createAndRedirectGameRoom(dto);

        // 세션에 게임룸 정보 저장 (서비스 호출 이후 세션 로직 배치)
        HttpSession session = request.getSession();
        Map<Integer, List<GameRoomDto>> gameRooms = (Map<Integer, List<GameRoomDto>>) session.getAttribute("MapListGame");
        if (gameRooms == null) {
            gameRooms = new HashMap<>();
        }

        int roomId = roomDetails.getGameroomNo();
        if (!gameRooms.containsKey(roomId)) {
            gameRooms.put(roomId, new ArrayList<>());
        }
        gameRooms.get(roomId).add(roomDetails);
        session.setAttribute("MapListGame", gameRooms);

        return ResponseEntity.ok(roomDetails);
    }


    //게임룸 리스트 받아오기
    @GetMapping("/gameRooms")
    public Map<Integer, List<GameRoomDto>> getGameRooms(Map<String, Object> model) {
        Map<Integer, List<GameRoomDto>> gameRooms = (Map<Integer, List<GameRoomDto>>) model.get("MapListGame");

        if (gameRooms == null) {
            gameRooms = new HashMap<>();
        }

        return gameRooms;
    }
}