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
@Alias("BoardScrapsDto")
public class BoardScrapsDto {
	
	private Long accountNo;
	private Long bno;

}
