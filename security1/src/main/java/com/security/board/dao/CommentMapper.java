package com.security.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.security.board.dto.BoardCommentDto;

@Mapper
public interface CommentMapper {

	List<BoardCommentDto> findAllByNo(Long bno);
	
	BoardCommentDto findByOne(Long bcno);
	
	int insert(BoardCommentDto dto);
	
	int delete(Long bcno);
	
	int update(BoardCommentDto dto);
	
}
