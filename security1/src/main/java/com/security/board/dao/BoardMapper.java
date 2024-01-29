package com.security.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.security.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	//전체 게시물 조회(좋아요 및 스크렙 수 까지)
	public List<BoardDto> findByAll();
	
	//특정 게시말 상세 조회
	public BoardDto findByNo(Long bno);
	
	//특정 게시물 조회시 마다 조회수 증가
	public int incrementHitCount(Long bno);
	
	//게시글 등록
	public int save(BoardDto dto);
	
	//게시글 수정
	public int update(BoardDto dto);
	
	//게시글 삭제
	public int delete(BoardDto dto);

}
