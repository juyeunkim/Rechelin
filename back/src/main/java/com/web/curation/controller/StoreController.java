package com.web.curation.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.DAO.StoreDao;
import com.web.curation.model.DTO.Hashtag;
import com.web.curation.model.DTO.Review;
import com.web.curation.model.DTO.Store;
import com.web.curation.service.ReviewService;
import com.web.curation.service.StoreService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class StoreController {

	@Autowired
	StoreService service;

	@Autowired
	ReviewService reservice;

	@Autowired
	StoreDao storeDao;

	@RequestMapping(value = "/store/review", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "리뷰 식당검색")
	public Object register(@RequestBody Store store) {
		System.out.println(store.getSname());
		System.out.println(store.getAddress());
		System.out.println(store.getLat());
		System.out.println(store.getLng());
		System.out.println(store.getImg());

		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "식당 정보";
		Store nstore = service.register(store);
		result.object = new JSONObject(nstore).toMap();

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/store/delete/{num}")
	@ApiOperation(value = "삭제하기")
	public Object delete(@PathVariable(required = true) final long num) {
		final BasicResponse result = new BasicResponse();
		if (service.delete(num)) {
			result.status = true;
			result.data = "삭제 성공";
		} else {
			result.status = true;
			result.data = "삭제 성공";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/store/list")
	@ApiOperation(value = "식당리스트")
	public Object selectAll() {
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "성공";
		result.object = service.selectAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/store/selectOne")
	@ApiOperation(value = "식당정보")
	public Object selectOne(@RequestParam(required = true) final long num) {
		final BasicResponse result = new BasicResponse();
		Store store = service.selectOne(num);
		if (store != null) {
			result.status = true;
			result.data = "성공";
			result.object = new JSONObject(store).toMap();
		} else {
			result.status = false;
			result.data = "식당이 없습니다.";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/store/tags")
	@ApiOperation(value = "태그정보")
	public Object tags(@RequestParam(required = true) final long num) {
		final BasicResponse result = new BasicResponse();
		List<Hashtag> list = service.storeTags(num);
		if (list != null) {
			result.status = true;
			result.data = "성공";
			result.object = list;
		} else {
			result.status = false;
			result.data = "리뷰가 없습니다.";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/store/random/{keyword}")
	@ApiOperation(value = "랜덤 음식 추천")
	public Object random(@PathVariable(required = true) final String keyword) {
		final BasicResponse result = new BasicResponse();

		result.status = true;
		result.data = "성공";
		result.object = service.random(keyword);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/store/addBook")
	@ApiOperation(value = "북마크 추가")
	public Object addbook(@RequestParam(required = true) final long id,
			@RequestParam(required = true) final long snum) {
		final BasicResponse result = new BasicResponse();

		if (service.addBook(id, snum)) {
			result.status = true;
			result.data = "북마크 추가 성공";
		} else {
			result.status = false;
			result.data = "북마크 추가 실패";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/store/removeBook")
	@ApiOperation(value = "북마크 삭제")
	public Object removebook(@RequestParam(required = true) final long id,
			@RequestParam(required = true) final long snum) {
		final BasicResponse result = new BasicResponse();

		if (service.removeBook(id, snum)) {
			result.status = true;
			result.data = "북마크 삭제 성공";
		} else {
			result.status = false;
			result.data = "북마크  실패";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/store/checkBook")
	@ApiOperation(value = "북마크 체크")
	public Object checkbook(@RequestParam(required = true) final long id,
			@RequestParam(required = true) final long snum) {
		final BasicResponse result = new BasicResponse();

		if (service.selectBook(id, snum) != null) {
			result.status = true;
			result.data = "북마크 존재";
		} else {
			result.status = false;
			result.data = "북마크  없음";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/store/scoreAvg/{snum}")
	@ApiOperation(value = "평균점수 구하기")
	public Object scoreAvg(@PathVariable(required = true) final long snum) {
		final BasicResponse result = new BasicResponse();
		List<Review> list = reservice.storereviews(snum);
		double kind = 0.0;
		double price = 0.0;
		double taste = 0.0;
		double total = 0.0;
		
		for(Review r : list) {
			kind += r.getKindness();
			price += r.getPrice();
			taste += r.getTaste();
			total += r.getTotal();
		}
		
		kind = kind/list.size();
		price = price/list.size();
		taste = taste/list.size();
		total = total/list.size();
		
		kind = Math.round(kind*10)/10.0;
		price = Math.round(price*10)/10.0;
		taste = Math.round(taste*10)/10.0;
		total = Math.round(total*10)/10.0;
		
		JSONObject obj = new JSONObject();
		
		obj.put("kindAvg", kind);
		obj.put("priceAvg", price);
		obj.put("tasteAvg", taste);
		obj.put("totalAvg", total);
		
		
		result.status = true;
		result.data = "북마크  없음";
		result.object = obj.toMap();

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
