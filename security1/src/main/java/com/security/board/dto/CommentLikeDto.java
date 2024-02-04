package com.security.board.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Alias("CommentLikeDto")
public class CommentLikeDto {
	
	private Long bcno;
	private Long accountNo;
	private Date likeDate;

}
