package com.ict.fitme.account.dto;

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
@ToString //테스트 할 때만 사용하고 나중엔 삭제해야함
@Alias("AccountDto")
public class AccountDto {

	private Long accountNo;
	private String email;
	private String password;
	private String name;
	private String gender;
	private String age;
	private String homeAddress;
	private String hobby;
	private Date enrollDate;
		
	
	/*
	 account_no number NOT NULL,
	email nvarchar2(100) NOT NULL,
	password nvarchar2(50) NOT NULL,
	name nvarchar2(20) NOT NULL,
	gender nvarchar2(1) NOT NULL,
	age number NOT NULL,
	home_address nvarchar2(100) NOT NULL,
	hobby char(1 char) NOT NULL,
	enroll_date date DEFAULT SYSDATE NOT NULL,
	 * */
}
