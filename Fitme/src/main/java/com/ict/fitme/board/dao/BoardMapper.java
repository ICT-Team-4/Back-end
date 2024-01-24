package com.ict.fitme.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.fitme.board.dto.BoardDto;

@Mapper
public interface BoardMapper {

	public List<BoardDto> findByAll();

	public BoardDto findByNo(Long bno);
	
	public int save(BoardDto dto);
	
	public int update(BoardDto dto);
	
}
