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
	@ApiOperation(value = "팔로우 승낙")
	public Object addFollow(@RequestParam(required = true) final long fan,
			@RequestParam(required = true) final long star) {

		final BasicResponse result = new BasicResponse();

		if (service.addFollow(fan, star)) {
			result.status = true;
			result.data = "팔로워/팔로잉 리스트 추가";
		} else {
			result.status = false;
			result.data = "추가 실패";
		}
		if (service.declineFollow(fan, star)) {
			result.status = true;
			result.data = "알림 삭제 성공";
		} else {
			result.status = false;
			result.data = "알림 삭제 실패";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	@PostMapping("/follow/decline")
	@ApiOperation(value = "팔로우 거절")
	public Object declineFollow(@RequestParam(required = true) final long fan,
			@RequestParam(required = true) final long star) {

		final BasicResponse result = new BasicResponse();
		if (service.declineFollow(fan, star)) {
			result.status = true;
			result.data = "알림 삭제 성공";
		} else {
			result.status = false;
			result.data = "알림 삭제 실패";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/request")
	@ApiOperation(value = "팔로우 요청")
	public Object reqFollow(@RequestParam(required = true) final long fan,
			@RequestParam(required = true) final long star) {

		final BasicResponse result = new BasicResponse();
		if (service.requestFollow(fan, star)) {
			result.status = true;
			result.data = "요청 성공";
		} else {
			result.status = false;
			result.data = "이미 요청중입니다.";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/starList")
	@ApiOperation(value = "팔로잉 리스트")
	public Object starList(@RequestParam(required = true) final long id) {
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		List<User> list = service.starList(id);
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/fanList")
	@ApiOperation(value = "팔로워 리스트")
	public Object fanList(@RequestParam(required = true) final long id) {
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		List<User> list = service.fanList(id);
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/alarmList")
	@ApiOperation(value = "알림 리스트")
	public Object alarmList(@RequestParam(required = true) final long id) {
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		result.object = service.alarmList(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/alarmCheck")
	@ApiOperation(value = "알람 읽음표시로 변경")
	public Object alarmCheck(@RequestParam(required = true) final long id) {
		
		final BasicResponse result = new BasicResponse();
		if(service.alarmCheck(id)) {
			result.status = true;
			result.data = "알림 읽음";
		}else {
			result.status = false;
			result.data = "알림이 없습니다.";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/follow/newAlarm")
	@ApiOperation(value = "새 알림 확인")
	public Object newAlarm(@RequestParam(required = true) final long id) {
		final BasicResponse result = new BasicResponse();
		if(service.newAlarm(id)) {
			result.status = true;
			result.data = "새 알림이 있습니다.";
		}else {
			result.status = false;
			result.data = "새 알림이 없습니다.";
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
	@PostMapping("/follow/unfollow")
	@ApiOperation(value = "팔로우 취소")
	public Object unfollow(@RequestParam(required = true) final long fan,
			@RequestParam(required = true) final long star) {
		final BasicResponse result = new BasicResponse();
		
		if(service.unFollow(fan, star)) {
			result.status = true;
			result.data = "팔로우가 취소 되었습니다.";			
		}else {
			result.status = false;
			result.data = "팔로우가 존재하지 않습니다.";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
