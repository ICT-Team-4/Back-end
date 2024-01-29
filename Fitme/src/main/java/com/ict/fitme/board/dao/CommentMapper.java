package com.ict.fitme.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.fitme.board.dto.BoardCommentDto;

@Mapper
public interface CommentMapper {

	List<BoardCommentDto> findAllByNo(Long bno);
	
	BoardCommentDto findByOne(Long bcno);
	
	int insert(BoardCommentDto dto);
	
	int delete(Long bcno);
	
	int update(BoardCommentDto dto);
	
}
