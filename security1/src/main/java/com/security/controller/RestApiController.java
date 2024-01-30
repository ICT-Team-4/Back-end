package com.security.controller;

import java.io.IOException;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.UserDto;
import com.security.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;



@RestController
public class RestApiController {

	@Autowired
	private UserService service;	
	
  @GetMapping("home")
  public String home() {
    return "<h1>home</h1>";
  }

  @PostMapping("token")
  public String token() {
    return "<h1>token</h1>";
  }
  
  @PostMapping("/joinMember")
  	public String joinMember(
  			@RequestPart("name") String name,
  			@RequestPart("username") String username,
  			@RequestPart("password") String password,
  			@RequestPart("height") String height,
  			@RequestPart("weight") String weight,
  			@RequestPart("address") String address,
  			@RequestPart("age") String age,
  			@RequestPart("gender") String gender,
  			@RequestPart("inter") String inter,
  			HttpServletRequest req
  			) throws IOException, ServletException{
	  Collection<Part> parts = req.getParts();
		UserDto dto = new UserDto();
	
		dto.setName(name);
		dto.setUsername(username);
		dto.setPassword(password);
		dto.setHeight(height); 
		dto.setWeight(weight);
		dto.setAddress(address);
		dto.setAge(age);
		dto.setGender(gender);
		
		service.insertMember(dto);
	  
	  
	  
	  return "회원가입 성공";
  }
  	
}
