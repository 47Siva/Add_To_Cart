package com.sampleproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sampleproject.common.APIResponse;
import com.sampleproject.dto.LoginRequestDto;
import com.sampleproject.dto.SignUpRequestDto;
import com.sampleproject.service.LoginService;
import com.sampleproject.util.JwtUtil;



@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	LoginService loginService;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/signUp")
	public ResponseEntity<APIResponse> singUp(@RequestBody SignUpRequestDto signUpRequestDto) {
		// hwbk
		APIResponse apiResponse = loginService.signUp(signUpRequestDto);

		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> singUp(@RequestBody LoginRequestDto loginRequestDto) {

		APIResponse apiResponse = loginService.login(loginRequestDto);

		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}

	@GetMapping("/private")
	public ResponseEntity<APIResponse> privateApi(@RequestHeader(value = "Authorization") String auth)
			throws Exception {
		APIResponse apiResponse = new APIResponse();

//		String authorization = "auth";
		jwtUtil.verify(auth);

		apiResponse.setStatus(HttpStatus.ACCEPTED.value());
		apiResponse.setData("its worke");
		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
	}

	
}
