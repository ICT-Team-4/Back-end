package com.security.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.board.dao.CommentMapper;
import com.security.board.dto.AccountDto;
import com.security.board.dto.BoardCommentDto;
import com.security.board.dto.CommentLikeDto;

@Service
public class CommentService {
	
	private CommentMapper commentMapper;
	
	public CommentService(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}
	
	//특정 게시글에 대한 전체 댓글 목록 조회
	public List<BoardCommentDto> commentList(Long bno) {
		return commentMapper.findAllByNo(bno);
	}
	
	//특정 게시글에 대한 특정 댓글 정보 조회
	public BoardCommentDto commentOne(Long bcno) {
		return commentMapper.findByOne(bcno);
	}
	
	//사용자 정보 조회
	public AccountDto findByUsername(String username) {
		return commentMapper.findByUsername(username);
	}
	
	//댓글 좋아요
	@Transactional
	public int commentLike(Long bcno, String username) {
		
		Long accountNo = commentMapper.findByUsername(username).getAccountNo();
		
		CommentLikeDto clike = new CommentLikeDto();
		clike.setBcno(bcno);
		clike.setAccountNo(accountNo);
		
		int count = commentMapper.findByCommentLike(clike);
		
		//2. 좋아요를 누른적이 없다면 ? insert : delete
		//좋아요 등록 1, 취소 2
		if(count == 0) {
			commentMapper.insertCommentLike(clike);
	        return 1;
	    } else {
	    	commentMapper.deleteCommentLike(clike);
	        return 2;
	    }
	}

	//댓글 등록
	@Transactional
	public int commentPost(BoardCommentDto dto) {
		return commentMapper.insert(dto);
	}
	
	//댓글 삭제
	@Transactional
	public int commentDelete(Long bcno) {
		return commentMapper.delete(bcno);
	}
	
	//댓글 수정
	@Transactional
	public int commentUpdate(BoardCommentDto dto) {
		return commentUpdate(dto);
	}

}
