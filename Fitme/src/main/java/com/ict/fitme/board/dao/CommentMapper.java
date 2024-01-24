package com.ict.fitme.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.fitme.board.dto.BoardCommentDto;

@Mapper
public interface CommentMapper {

	List<BoardCommentDto> findAllByNo(Long bno);
	
}
