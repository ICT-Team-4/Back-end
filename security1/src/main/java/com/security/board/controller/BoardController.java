package com.security.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.board.dto.AccountDto;
import com.security.board.dto.BoardDto;
import com.security.board.dto.FriendDto;
import com.security.board.service.BoardService;
import com.security.util.JWTOkens;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class BoardController {
	
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	//현재 로그인 중인 사용자 정보 조회
	@GetMapping("/boards/account")
	public ResponseEntity<AccountDto> accountInfo(HttpServletRequest request) {
		  
		String token = request.getHeader("Authorization").split(" ")[1].trim();
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		AccountDto accountInfo = boardService.findByUsername(username);
		
		System.out.println(accountInfo);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(accountInfo);
		  
	}
	
	//게시글 전체 조회
	@GetMapping("/boards")
	public ResponseEntity<List<BoardDto>> boardAllList() {
		
		List<BoardDto> allList = boardService.findByAll();
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(allList);
	}
	
	//특정 회원의 게시글 전체 조회
	@GetMapping("/boards/friends/{accountNo}") 
	public ResponseEntity<List<BoardDto>> boardAllListByNo(@PathVariable Long acconutNo) {
		
		List<BoardDto> allListNo = boardService.findAllByNo(acconutNo);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(allListNo);
		
	}
	
	//특정 게시글 상세 조회
	@GetMapping("/boards/{bno}")
	public ResponseEntity<BoardDto> boardOneList(@PathVariable Long bno){
		
		BoardDto oneList = boardService.findByOne(bno);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(oneList);
	}
	
	//친구 목록 출력
	@GetMapping("/boards/friend")
	public ResponseEntity<List<FriendDto>> boardFriendList(AccountDto dto, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization").split(" ")[1].trim();
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		List<FriendDto> friendsInfo = boardService.findFriendByUsername(username);
		
		System.out.println(friendsInfo);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(friendsInfo);	
	}
	
	//게시글 등록
	@PostMapping("/boards")
	public ResponseEntity<BoardDto> boardSave(@RequestBody BoardDto dto) {
		
		//추가 디테일 잡는 부분
		//String message = "";
		//int flag=0;
		//flag = boardService.boardSave(dto);
		/*
		if(flag==0) {
			message="게시글 등록 실패";
			System.out.println(message);
			return ResponseEntity.ok().header("Content-Type", "application/json; chrset=UTF-8").body(dto);
		}
		*/
		
		boardService.boardSave(dto);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(dto);
	}
	
	//게시글 수정
	@PutMapping("/boards")
	public ResponseEntity<BoardDto> boardUpdate(BoardDto dto) {
	
		boardService.boardUpdate(dto);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(dto);
	}
	
	//게시글 삭제
	@DeleteMapping("/boards/{bno}")
	public ResponseEntity<String> boardDelete(@PathVariable Long bno) {
		
		String message = "";
		int flag = 0;
		
		BoardDto dto = boardService.findByOne(bno);
		
		//현재 접속중인 사용자에 대한 정보를 받아오는 부분이 필요함
		//일단은 임시 방편으로 지워지게만 만듬
		flag = boardService.boardDelete(dto);
		
		if(flag == 0) message = "삭제에 실패했습니다";
		
		message = "삭제 성공";
		
		
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
	}
	
}
