package com.security.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.board.dao.CommentMapper;
import com.security.board.dto.BoardCommentDto;

@Service
public class CommentService {
	
	private CommentMapper commentMapper;
	
	public CommentService(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}
	
	public List<BoardCommentDto> commentList(Long bcno) {
		return commentMapper.findAllByNo(bcno);
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
