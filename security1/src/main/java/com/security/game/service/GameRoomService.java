//package com.security.game.service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.security.game.dao.GameRoomMapper;
//
//@Service
//public class GameRoomService {
//	 
//	private final AccountRepository accountRepository;
//
//    // DI를 위한 생성자
//    public GameRoomService(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }
//
//    public GameRoomCreateResponse createGameRoom(GameRoomCreateRequest request) {
//        String username = accountRepository.findAccountByUsername(request.getAccountNo()).getUsername();
//        String gameRoomNo = generateGameRoomNo(); // 게임방 번호 생성 로직 구현 필요
//
//        GameRoomCreateResponse response = new GameRoomCreateResponse();
//        response.setAccountNo(request.getAccountNo());
//        response.setGameRoomNo(gameRoomNo);
//        response.setUsername(username);
//
//        return response;
//    }
//
//    private String generateGameRoomNo() {
//        // 게임방 번호 생성 로직 구현
//        return "GAME_ROOM_" + System.currentTimeMillis(); // 예시 구현
//    }
//}