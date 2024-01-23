package com.ict.fitme.diet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ict.fitme.account.dao.AccountMapper;

@RestControllerAdvice
@RestController
public class AccountDietController {
	
	@Autowired
	private AccountMapper accountMapper;
	

}
