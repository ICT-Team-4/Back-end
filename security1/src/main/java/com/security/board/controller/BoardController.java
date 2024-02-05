package com.security.board.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.security.board.dto.AccountDto;
import com.security.board.dto.BoardDto;
import com.security.board.dto.BoardImageDto;
import com.security.board.dto.BoardLikesDto;
import com.security.board.dto.FriendDto;
import com.security.board.service.BoardService;
import com.security.util.JWTOkens;

import jakarta.servlet.ServletException;
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
		  
		String token = request.getHeader("Authorization");
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
		
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		List<FriendDto> friendsInfo = boardService.findFriendByUsername(username);
		
		System.out.println(friendsInfo);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(friendsInfo);	
	}
	
	//게시글 등록
	@PostMapping("/boards")
	public ResponseEntity<BoardDto> boardSave(
			@RequestPart Long accountNo,
			@RequestPart String title,
			@RequestPart String boardCategory,
			@RequestPart String boardComment,
			@RequestPart String address,
			@RequestPart MultipartFile image,
			HttpServletRequest request
		) throws IOException, ServletException {
		
		System.out.println("리퀘스트 겟 파츠"+request.getParts());
		System.out.println("이미지 경로"+image);
		System.out.println("dddd");
		
		BoardDto boardDto = new BoardDto();
		boardDto.setAccountNo(accountNo);
		boardDto.setTitle(title);
		boardDto.setBoardCategory(boardCategory);
		boardDto.setBoardComment(boardComment);
		boardDto.setAddress(address);
		BoardImageDto imageDto = new BoardImageDto();
		
		
		boardService.boardSave(boardDto, imageDto);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(boardDto);
	}
	
	//좋아요
	@PostMapping("/boards/like/{bno}")
	public ResponseEntity<String> boardLike(@PathVariable Long bno, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		String message = "";
		int count = 0;
		
		count= boardService.like(bno, username);
    
		//프론트에서 좋아요 버튼을 어떤 값으로 온/오프 할거인지 말하고 문자열을 보낼지 숫자를 보낼지 정할 예정 일단 문자열로 응답.
		if(count == 1) {
			message = "활성화";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		} else {
			message = "비활성화";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
	}
	
	//좋아요
	@PostMapping("/boards/like/{bno}")
	public ResponseEntity<String> boardLike(@PathVariable Long bno, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		String message = "";
		int count = 0;
		
		count= boardService.like(bno, username);
		
		//프론트에서 좋아요 버튼을 어떤 값으로 온/오프 할거인지 말하고 문자열을 보낼지 숫자를 보낼지 정할 예정 일단 문자열로 응답.
		if(count == 1) {
			message = "활성화";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		} else {
			message = "비활성화";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
	}
	
	//게시글 수정
	@PutMapping("/boards/{bno}")
	public ResponseEntity<String> boardUpdate(@PathVariable Long bno ,@RequestBody BoardDto dto, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		String message = "";
		int flag = 0;
		
		AccountDto accountDto = boardService.findByUsername(username);
		BoardDto boardDto = boardService.findByOne(bno);
		
		if(!(accountDto.getAccountNo() == boardDto.getAccountNo())) {
			message = "등록한 사용자가 아닙니다.";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
	
		flag = boardService.boardUpdate(dto);
		
		if(flag == 0) {
			message = "수정에 실패 했습니다";
		}
		
		message = dto.getBno() +"번 게시글 수정 성공";
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
	}
	
	//게시글 삭제
	@DeleteMapping("/boards/{bno}")
	public ResponseEntity<String> boardDelete(@PathVariable Long bno, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
			
		String message = "";
		int flag = 0;
		
		BoardDto boardDto = boardService.findByOne(bno);
		AccountDto accountDto = boardService.findByUsername(username);
		
		System.out.println(String.format("게시글 작성자 번호 : %s, 로그인한 사람 번호 : %s", boardDto.getAccountNo(), accountDto.getAccountNo()));

		if(!(boardDto.getAccountNo() == accountDto.getAccountNo())) {
			message = "동일한 회원이 아닙니다";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}

		flag = boardService.boardDelete(boardDto);
		
		if(flag == 0) message = "삭제에 실패했습니다";
		
		message = "삭제 성공";
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
	}
	
}
