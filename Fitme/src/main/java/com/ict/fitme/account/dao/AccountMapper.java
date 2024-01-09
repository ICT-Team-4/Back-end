package com.ict.fitme.account.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ict.fitme.account.dto.AccountDto;

@Mapper
public interface AccountMapper {
	
	AccountDto findByNo(String no);

}
