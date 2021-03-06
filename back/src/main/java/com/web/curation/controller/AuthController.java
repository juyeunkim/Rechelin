package com.web.curation.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.JwtAuthenticationResponse;
import com.web.curation.model.DAO.RoleDao;
import com.web.curation.model.DAO.UserDao;
import com.web.curation.model.DTO.User;
import com.web.curation.security.JwtTokenProvider;
import com.web.curation.service.AcountService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDao userDao;

	@Autowired
	RoleDao roleDao;

	@Autowired
	AcountService service;


	@Autowired
	JwtTokenProvider tokenProvider;

	@PostMapping("/auth/login")
	public Object authenticateUser(@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String password) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);

		final BasicResponse result = new BasicResponse();
		JSONObject dummyUser = new JSONObject();

		User tmp = service.login(email, password);

		if (tmp == null) {
			System.out.println("X");
			result.status = false;
			result.data = "email이 존재하지 않습니다";

		} else {
			if (tmp.getPw() == "") {
				result.status = false;
				result.data = "비밀번호가 틀렸습니다.";
				result.object = dummyUser.toMap();
			} else {
				result.status = true;
				result.data = "success";
				result.object = new JwtAuthenticationResponse(jwt, tmp.getId(),tmp.getEmail(),tmp.getNickname());
			}
		}
		System.out.println(result.object.toString());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/auth/signup")
	public Object registerUser(@RequestBody User request) {

		final BasicResponse result = new BasicResponse();
		User tmp = service.signup(request);

		if (tmp.getEmail().equals("")) {
			result.status = false;
			result.data = "생성 실패(이메일 중복)";

		} else if (tmp.getNickname().equals("")) {
			result.status = false;
			result.data = "생성 실패(닉네임 중복)";
		} else {
			result.status = true;
			result.data = "생성 성공";
			result.object = new JSONObject(request).toMap();
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/auth/check")
	public Object check(@RequestParam String totken) {


		return tokenProvider.validateToken(totken);
	}
	
	
}