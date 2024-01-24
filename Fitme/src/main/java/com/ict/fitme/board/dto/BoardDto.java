package com.ict.fitme.board.dto;

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
@Alias("BoardDto")
public class BoardDto {
	private Long bno;
	private Long accountNo;
	private Date postDate;
	private String title;
	private String hitCount;
	private String boardCategory;
	private String boardComment;
	private String address;
	private Date editDate;
}
