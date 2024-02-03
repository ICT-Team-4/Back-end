package com.security.controller;

import java.io.IOException;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.UserDto;
import com.security.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;


@RestController
@CrossOrigin
public class RestApiController {

	private final UserService userService;
	private BCryptPasswordEncoder passwordEncoder;

    public RestApiController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
	
	  @GetMapping("home")
	  public String home() {
	    return "<h1>home</h1>";
	  }
	
	  @PostMapping("token")
	  public String token() {
	    return "<h1>token</h1>";
	  }
	  

	  @PostMapping("/joinMember")
	    public ResponseEntity<String> joinMember(
	            @RequestParam("name") String name,
	            @RequestParam("username") String username,
	            @RequestParam("password") String password,
	            @RequestParam("height") String height,
	            @RequestParam("weight") String weight,
	            @RequestParam("address") String address,
	            @RequestParam("age") String age,
	            @RequestParam("gender") String gender,
	            @RequestParam("hobby") String hobby,
	            HttpServletRequest req
	    ) {
	        try {
	            UserDto dto = new UserDto();
	            dto.setName(name);
	            dto.setUsername(username);
	            dto.setPassword(passwordEncoder.encode(password));
	            dto.setHeight(height);
	            dto.setWeight(weight);
	            dto.setAddress(address);
	            dto.setAge(age);
	            dto.setGender(gender);
	            dto.setHobby(hobby);

	            userService.insertMember(dto);

	            return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);

	        } catch (Exception e) {
	            return new ResponseEntity<>("회원가입 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	  }
	  	
}
