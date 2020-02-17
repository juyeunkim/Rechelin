package com.web.curation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.DTO.User;
import com.web.curation.service.FollowService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class FollowController {

	@Autowired
	FollowService service;

	@PostMapping("/follow/accept")
	@ApiOperation(value = "�뙏濡쒖슦 �듅�굺")
	public Object addFollow(@RequestParam(required = true) final long fan,
			@RequestParam(required = true) final long star) {

		final BasicResponse result = new BasicResponse();

		if (service.addFollow(fan, star)) {
			result.status = true;
			result.data = "�뙏濡쒖썙/�뙏濡쒖엵 由ъ뒪�듃 異붽�";
		} else {
			result.status = false;
			result.data = "異붽� �떎�뙣";
		}
		if (service.declineFollow(fan, star)) {
			result.status = true;
			result.data = "�븣由� �궘�젣 �꽦怨�";
		} else {
			result.status = false;
			result.data = "�븣由� �궘�젣 �떎�뙣";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@DeleteMapping("/follow/decline")
	@ApiOperation(value = "�뙏濡쒖슦 嫄곗젅")
	public Object declineFollow(@RequestParam(required = true) final long fan,
			@RequestParam(required = true) final long star) {

		final BasicResponse result = new BasicResponse();
		if (service.declineFollow(fan, star)) {
			result.status = true;
			result.data = "�븣由� �궘�젣 �꽦怨�";
		} else {
			result.status = false;
			result.data = "�븣由� �궘�젣 �떎�뙣";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/request")
	@ApiOperation(value = "�뙏濡쒖슦 �슂泥�")
	public Object reqFollow(@RequestParam(required = true) final long fan,
			@RequestParam(required = true) final long star) {

		final BasicResponse result = new BasicResponse();
		if (service.requestFollow(fan, star)) {
			result.status = true;
			result.data = "�슂泥� �꽦怨�";
		} else {
			result.status = false;
			result.data = "�씠誘� �슂泥�以묒엯�땲�떎.";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/starList")
	@ApiOperation(value = "�뙏濡쒖엵 由ъ뒪�듃")
	public Object starList(@RequestParam(required = true) final long id) {
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		List<User> list = service.starList(id);
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/fanList")
	@ApiOperation(value = "�뙏濡쒖썙 由ъ뒪�듃")
	public Object fanList(@RequestParam(required = true) final long id) {
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		List<User> list = service.fanList(id);
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/alarmList")
	@ApiOperation(value = "�븣由� 由ъ뒪�듃")
	public Object alarmList(@RequestParam(required = true) final long id) {
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		result.object = service.alarmList(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/alarmCheck")
	@ApiOperation(value = "�븣�엺 �씫�쓬�몴�떆濡� 蹂�寃�")
	public Object alarmCheck(@RequestParam(required = true) final long id) {
		
		final BasicResponse result = new BasicResponse();
		if(service.alarmCheck(id)) {
			result.status = true;
			result.data = "�븣由� �씫�쓬";
		}else {
			result.status = false;
			result.data = "�븣由쇱씠 �뾾�뒿�땲�떎.";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/newAlarm")
	@ApiOperation(value = "�깉 �븣由� �솗�씤")
	public Object newAlarm(@RequestParam(required = true) final long id) {
		final BasicResponse result = new BasicResponse();
		if(service.newAlarm(id)) {
			result.status = true;
			result.data = "�깉 �븣由쇱씠 �엳�뒿�땲�떎.";
		}else {
			result.status = false;
			result.data = "�깉 �븣由쇱씠 �뾾�뒿�땲�떎.";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/follow/status")
	@ApiOperation(value = "팔로우 상태 확인")
	public Object followStatus(@RequestParam(required = true) final long fan,
			@RequestParam(required = true) final long star) {
		final BasicResponse result = new BasicResponse();
		result.status = true;
		int status = service.status(fan, star);
		switch (status) {
		case 0:
			result.data = "팔로우 안되어있는 상태 - FOLLOW";
			break;
		case 1:	
			result.data = "이미 요청한 상태 - 요청중";			
			break;
		case 2:	
			result.data = "이미 팔로우 되어있는 상태 - UNFOLLOW";			
			break;
		}
		result.object = status;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
