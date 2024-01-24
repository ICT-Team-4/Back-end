package com.ict.fitme.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.fitme.board.dto.BoardDto;
import com.ict.fitme.board.service.BoardService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class BoardController {
	
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/boards")
	public ResponseEntity<List<BoardDto>> boardAllList() {
		
		List<BoardDto> allList = boardService.findByAll();
		System.out.println(allList);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(allList);
	}
	
	@GetMapping("/boards/{bno}")
	public ResponseEntity<BoardDto> boardOneList(@PathVariable Long bno){
		
		BoardDto oneList = boardService.findByNo(bno);
		
		System.out.println(oneList);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; chrset=UTF-8").body(oneList);
		
	}
	
	@PostMapping("/boards")
	public ResponseEntity<BoardDto> boardSave(BoardDto dto) {
		
		String message = "";
		
		int flag=0;
		
		flag = boardService.boardSave(dto);
		
		System.out.println(flag);
		
		if(flag==0) {
			message="게시글 등록 실패";
			System.out.println(message);
			return ResponseEntity.ok().header("Content-Type", "application/json; chrset=UTF-8").body(dto);
		}
		
		return ResponseEntity.ok().header("Content-Type", "application/json; chrset=UTF-8").body(dto);
	}
	
	@PutMapping("/boards/{bno}")
	public ResponseEntity<BoardDto> boardUpdate(BoardDto dto) {
		
		
		
		boardService.boardUpdate(dto);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; chrset=UTF-8").body(dto);
	}
	

}
