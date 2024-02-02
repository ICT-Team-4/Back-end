package com.security.mypage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.mypage.dto.MypageAccountDto;
import com.security.mypage.dto.MypageGameRoomDto;
import com.security.mypage.dto.MypageWorkAccuracyDto;
import com.security.mypage.service.MypageService;
import com.security.util.JWTOkens;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class MypageController {
	
	private MypageService mypageService;
	
	public MypageController(MypageService mypageService) {
		this.mypageService = mypageService;
	}
	
	@GetMapping("/mypages/account")
	public ResponseEntity<MypageAccountDto> accountInfo(HttpServletRequest request) {
		  
		String token = request.getHeader("Authorization").split(" ")[1].trim();
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		MypageAccountDto accountInfo = mypageService.findByUsername(username);
		
		System.out.println(accountInfo);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(accountInfo);
		  
	}
	@GetMapping("/mypages/games/{accountNo}") //게임 조회
	public ResponseEntity<List<MypageGameRoomDto>> gameList(@PathVariable int accountNo){
		
		List<MypageGameRoomDto> findAllByNo = mypageService.findAllByNo(accountNo);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(findAllByNo);
	}
	@GetMapping("/mypages/workAccuracy/{accountNo}") //3대 운동 추이
	public ResponseEntity<List<MypageWorkAccuracyDto>> accuracyInfo(@PathVariable int accountNo){
		List<MypageWorkAccuracyDto> findAllByNo = mypageService.findAccuracyAllByNo(accountNo);
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(findAllByNo);
		
	}
	
}
