package com.ict.fitme.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ict.fitme.board.dao.CommentMapper;
import com.ict.fitme.board.dto.BoardCommentDto;

@Service
public class CommentService {
	
	private CommentMapper commentMapper;
	
	public CommentService(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}
	
	//특정 게시물에 대한 모든 댓글 조회
	public List<BoardCommentDto> commentList(Long bno) {
		return commentMapper.findAllByNo(bno);
	}
	
	public BoardCommentDto commentOne(Long bcno) {
		return commentMapper.findByOne(bcno);
	}

	@Transactional
	public int commentPost(BoardCommentDto dto) {
		return commentMapper.insert(dto);
	}
	
	@Transactional
	public int commentDelete(Long bcno) {
		return commentMapper.delete(bcno);
	}
	
	@Transactional
	public int commentUpdate(BoardCommentDto dto) {
		return commentUpdate(dto);
	}
	
}
