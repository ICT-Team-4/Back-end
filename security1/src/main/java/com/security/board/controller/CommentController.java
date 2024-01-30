package com.security.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.board.dto.BoardCommentDto;
import com.security.board.service.CommentService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CommentController {
	
private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping("/comments/{bno}")
	public ResponseEntity<List<BoardCommentDto>> commentAllList(@PathVariable Long bno){
		
		List<BoardCommentDto> commentList = commentService.commentList(bno);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(commentList);
	}
	
	@PostMapping("/comments")
	public ResponseEntity<String> commentSave(@RequestBody BoardCommentDto dto){
		
		System.out.println(dto);
		
		String message = "";
		
		int flag = 0;
		
		flag = commentService.commentPost(dto);
		
		if(flag == 0) message = "댓글 작성에 실패했습니다.";
		
		message="댓글 작성에 성공하였습니다.";
		
		System.out.println(message);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
	}
	
	@DeleteMapping("/comments/{bcno}")
	public ResponseEntity<String> commentDelete(@PathVariable Long bcno) {
		
		String message = "";
		
		int flag = 0;
		
		flag = commentService.commentDelete(bcno);
		
		if(flag == 0) {
			message = "삭제에 실패했습니다";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
		
		message = "삭제에 성공했습니다";
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
	}
	
	/*
	@PutMapping("/comments/{bcno}")
	public ResponseEntity<String> commentUpdate(@PathVariable Long bcno, HttpServletRequest request) {
		
		String message = "";
		
		int flag = 0;
		
		BoardCommentDto commentOne = commentService.commentOne(bcno);
		
		System.out.println(commentOne);
		
		Map map = new HashMap<String, String>();
		
		map.put("bcComment", commentOne.getBcComment());
		map.put("bcno", commentOne.getBcno());
		
		flag = commentService.commentUpdate(commentOne);
		
		System.out.println(flag);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
	}
	*/
	
}
