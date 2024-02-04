package com.security.game.dao;

import org.apache.ibatis.annotations.Mapper;

import com.security.game.dto.GameAccountDto;

@Mapper
public interface GameAccountMapper {

	public int update(GameAccountDto dto);
	
}
