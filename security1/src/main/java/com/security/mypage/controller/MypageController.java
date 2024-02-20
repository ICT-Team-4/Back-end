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
import com.security.mypage.dto.MypageWorkBigThreeDto;
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
	
	@GetMapping("/mypages/account") //유저정보 가져오기
	public ResponseEntity<MypageAccountDto> accountInfo(HttpServletRequest request) {
		  
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		MypageAccountDto accountInfo = mypageService.findByNo(username);
		
		System.out.println(accountInfo.toString());
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(accountInfo);
		  
	}
	@GetMapping("/mypages/games/{accountNo}") //게임 조회
	public ResponseEntity<List<MypageGameRoomDto>> gameList(@PathVariable int accountNo){
		
		List<MypageGameRoomDto> findAllByNo = mypageService.findAllByNo(accountNo);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(findAllByNo);
	}
	@GetMapping("/mypages/workAccuracy/{accountNo}") //정확도
	public ResponseEntity<List<MypageWorkAccuracyDto>> accuracyInfo(@PathVariable int accountNo){
		List<MypageWorkAccuracyDto> findAllByNo = mypageService.findAccuracyAllByNo(accountNo);
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(findAllByNo);
		
	}
	@GetMapping("/mypages/workBigThree/{accountNo}")
	public ResponseEntity<List<MypageWorkBigThreeDto>> bigThreeInfo(@PathVariable int accountNo){
		List<MypageWorkBigThreeDto> findBigThreeAllByNo = mypageService.findBigThreeAllByNo(accountNo);
		return  ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(findBigThreeAllByNo);
		
	}
}
