package com.ict.fitme.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ict.fitme.board.dao.BoardMapper;
import com.ict.fitme.board.dto.BoardDto;

@Service
public class BoardService {
	
	private BoardMapper boardMapper;
	
	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	public List<BoardDto> findByAll() {
		return boardMapper.findByAll();
	}

	public BoardDto findByNo(Long bno) {
		return boardMapper.findByNo(bno);
	}
	
	public int boardSave(BoardDto dto) {
		return boardMapper.save(dto);
	}
	
	public int boardUpdate(BoardDto dto) {
		return boardMapper.update(dto);
	}

}
