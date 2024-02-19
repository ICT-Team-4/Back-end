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
import com.security.board.dto.BoardCommentDto;
import com.security.board.service.CommentService;
import com.security.util.JWTOkens;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CommentController {
	
private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	//특정 게시글에 대한 댓글 목록 조회
	@GetMapping("/comments/{bno}")
	public ResponseEntity<List<BoardCommentDto>> commentAllList(@PathVariable String bno){
		
		List<BoardCommentDto> commentList = commentService.commentList(bno);
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(commentList);
	}
	
	//댓글 등록
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
	
	//댓글 삭제
	@DeleteMapping("/comments/{bcno}")
	public ResponseEntity<String> commentDelete(@PathVariable String bcno, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		String message = "";
		int flag = 0;
		
		AccountDto accountDto = commentService.findByUsername(username);
		BoardCommentDto commentDto = commentService.commentOne(bcno);
		
		if(!(accountDto.getAccountNo() == commentDto.getAccountNo())) {
			message = "댓글을 작성한 사용자가 아닙니다";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
		
		flag = commentService.commentDelete(bcno);
		
		if(flag == 0) {
			message = "삭제에 실패했습니다";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
		
		message = "삭제에 성공했습니다";
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
	}
	
	
	//댓글 수정
	@PutMapping("/comments/{bcno}")
	public ResponseEntity<String> commentUpdate(@PathVariable String bcno, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		String message = "";
		int flag = 0;
		
		AccountDto accountDto = commentService.findByUsername(username);
		BoardCommentDto commentOne = commentService.commentOne(bcno);
		
		if(!(accountDto.getAccountNo() == commentOne.getAccountNo())) {
			message = "댓글을 등록한 회원이 아닙니다.";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
		
		flag = commentService.commentUpdate(commentOne);
		
		if(flag == 0) {
			message = "수정에 실패했습니다.";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
		
		message = bcno + "번 수정에 성공하였습니다";
		
		return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
	}
	
	//댓글 좋아요
	@PostMapping("/comments/like/{bcno}")
	public ResponseEntity<String> commentLike(@PathVariable String bcno, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		Map<String, Object> payload = JWTOkens.getTokenPayloads(token);
		String username = payload.get("sub").toString();
		
		String message = "";
		int count = 0;
		
		count = commentService.commentLike(bcno, username);
		
		if(count == 1) {
			message = "댓글 좋아요";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		} else {
			message = "댓글 좋아요 취소";
			return ResponseEntity.ok().header("Content-Type", "application/json; charset=UTF-8").body(message);
		}
		
	}
	
	
}
