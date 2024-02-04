package com.security.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.game.dao.GameAccountMapper;
import com.security.game.dto.GameAccountDto;

@Service
public class GameAccountService {
	
	private GameAccountMapper gameAccountMapper;
	
	public GameAccountService(GameAccountMapper gameAccountMapper) {
		this.gameAccountMapper = gameAccountMapper;
	}
	
	@Transactional
	public int gameAccountUpdate(GameAccountDto dto) {
		return gameAccountMapper.update(dto);
	}
	
	
}
