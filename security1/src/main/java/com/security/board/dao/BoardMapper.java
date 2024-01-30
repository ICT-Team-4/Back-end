package com.security.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.security.board.dto.AccountDto;
import com.security.board.dto.BoardDto;
import com.security.board.dto.BoardLikesDto;
import com.security.board.dto.FriendDto;

@Mapper
public interface BoardMapper {
	
	//전체 게시물 조회(좋아요 및 스크렙 수 까지)
	public List<BoardDto> findByAll();
	
	//특정 게시말 상세 조회
	public BoardDto findByNo(Long bno);

	//특정 사용자가 등록한 게시글 전체 조회
	public List<BoardDto> findAllByNo(Long acconutNo);
	
	//로그인한 사용자 정보 조회
	public AccountDto findByUsername(String username);
	
	//로그인한 사용자 친구 정보 조회(친구 정보 + 친구 게시글 정보)
	public List<FriendDto> findFriendByUsername(String username);
	
	//특정 게시물 조회시 마다 조회수 증가
	public int incrementHitCount(Long bno);
	
	//좋아요
	public int incrementLikeCount(BoardLikesDto dto);
	
	//게시글 등록
	public int save(BoardDto dto);
	
	//게시글 수정
	public int update(BoardDto dto);
	
	//게시글 삭제
	public int delete(BoardDto dto);

	

}
