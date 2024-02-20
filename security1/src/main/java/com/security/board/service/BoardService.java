package com.security.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.board.dao.BoardMapper;
import com.security.board.dto.AccountDto;
import com.security.board.dto.BoardDto;
import com.security.board.dto.BoardImageDto;
import com.security.board.dto.BoardLikesDto;
import com.security.board.dto.FriendDto;

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
	
	//회원 번호로 해당 회원 게시글 목록 조회
	public List<BoardDto> findAllByNo(String acconutNo) {
		return boardMapper.findAllByNo(acconutNo);
	}
	
	//사용자 정보 조회
	public AccountDto findByAccountNo(String accountNo) {
		return boardMapper.findByAccountNo(accountNo);
	}
	
	//사용자 친구 정보 조회
	public List<FriendDto> findFriendByAccountNo(String accountNo) {
		return boardMapper.findFriendByAccountNo(accountNo);
	}
	
	//게시글 등록
	@Transactional
	public int boardSave(BoardDto BoardDto) {
		
		int boardFlag = 0;
		
		boardFlag = boardMapper.save(BoardDto);
		return boardFlag;
	}
	
	//좋아요 버튼 클릭
	@Transactional
	public int like(Long bno, String accountNo) {

		BoardLikesDto like = new BoardLikesDto();		
		like.setBno(bno);
		like.setAccountNo(accountNo);
		
		//1. 해당 회원이 해당 게시글에 좋아요 누른 여부 확인
		int count = boardMapper.findByLike(like);
		
		//2. 좋아요를 누른적이 없다면 ? insert : delete
		//좋아요 등록 1, 취소 2
		if(count == 0) {
	        boardMapper.insertLike(like);
	        return 1;
	    } else {
	        boardMapper.deleteLike(like);
	        return 2;
	    }
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
