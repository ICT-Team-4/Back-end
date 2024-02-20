package com.security.game.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.game.dto.GameAccountDto;
import com.security.game.service.GameAccountService;
import com.security.util.JWTOkens;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class GameAccountController {
	
	private GameAccountService gameAccountService;
	
	public GameAccountController(GameAccountService gameAccountService) {
		this.gameAccountService = gameAccountService;
	}
	
	@PutMapping("/games/account")
	public ResponseEntity<GameAccountDto> gameAccountUpdate(GameAccountDto dto){
//		System.out.println("확인용"+dto.toString());
		gameAccountService.gameAccountUpdate(dto);
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(dto);
	}
	

}
