package com.security.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.board.dao.BoardMapper;
import com.security.board.dto.BoardDto;

@Service
public class BoardService {

	private BoardMapper boardMapper;
	
	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	//모든 게시물 조회
	public List<BoardDto> findByAll(){
		return boardMapper.findByAll();
	}
	
	//특정 게시물 상세 조회
	@Transactional
	public BoardDto findByOne(Long bno) {
		
		//조회 시 조회수 증가
		boardMapper.incrementHitCount(bno);
		
		return boardMapper.findByNo(bno);
		
	}
	
	//게시글 등록
	@Transactional
	public int boardSave(BoardDto dto) {
		
		return boardMapper.save(dto);
		
	}
	
	//게시글 수정
	@Transactional
	public int boardUpdate(BoardDto dto) {
		
		return boardMapper.update(dto);
	}
	
	//게시글 삭제
	@Transactional
	public int boardDelete(BoardDto dto) {
		
		return boardMapper.delete(dto);
	}
	
}
