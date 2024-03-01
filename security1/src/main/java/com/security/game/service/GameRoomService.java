package com.security.game.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.game.dao.GameAccountMapper;
import com.security.game.dao.GameRoomMapper;
import com.security.game.dto.GameAccountDto;
import com.security.game.dto.GameRoomDto;
import com.security.game.gameinterface.GameRoomImpl;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GameRoomService implements GameRoomImpl {

    @Autowired
    private GameAccountMapper gameAccountMapper;

    @Override
    public Map<String, Object> createAndRedirectGameRoom(String accountNo) {
    	log.info("게임 방 번호 생성 시작 - accountNo: {}", accountNo);
        int roomNumber = new Random().nextInt(9000) + 1000;

        log.info("사용자 정보 조회 - accountNo: {}", accountNo);
        GameAccountDto accountDetails = gameAccountMapper.findByAccountNo(accountNo);
        
//        Map<String, Object> roomDetails = new HashMap<>();
//        roomDetails.put("roomNumber", roomNumber);
//        if (accountDetails != null) {
//            roomDetails.put("account_no", accountDetails.getAccountNo());
//            roomDetails.put("nickname", accountDetails.getNickname());
//            roomDetails.put("game_image", accountDetails.getGameImage());
//        }

        if (accountDetails != null) {
            log.info("사용자 정보 조회 성공 - accountDetails: {}", accountDetails);
        } else {
            log.warn("사용자 정보 조회 실패 - accountNo: {}", accountNo);
        }
        
        Map<String, Object> roomDetails = new HashMap<>();
        roomDetails.put("roomNumber", roomNumber);
        roomDetails.put("account_no", accountDetails.getAccountNo());
        roomDetails.put("nickname", accountDetails.getNickname());
        roomDetails.put("game_image", accountDetails.getGameImage());

        log.info("게임 방 생성 완료 서비스 - roomDetails: {}", roomDetails);
        return roomDetails;
    }
}