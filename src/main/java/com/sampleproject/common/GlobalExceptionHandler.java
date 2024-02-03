//package com.sampleproject.common;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//	@ExceptionHandler
//	public ResponseEntity<APIResponse> handleException(Exception e) {
//
//		APIResponse apiResponse = new APIResponse();
//		apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		apiResponse.setError("Oops...Somthings went worng!");
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
//	}
//}
