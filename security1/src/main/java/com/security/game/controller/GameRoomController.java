//package com.security.game.controller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.security.game.service.GameRoomService;
//
//public class GameRoomController {
//	
//	@Autowired
//	private GameRoomService gameroomService;
//	
//	@PostMapping("/game/createRoom")
//	public ResponseEntity<GameRoomCreateResponse> createGameRoom(@RequestBody GameRoomCreateRequest request) {
//        GameRoomCreateResponse response = gameRoomService.createGameRoom(request);
//        return ResponseEntity.ok(response);
//    }
//}
