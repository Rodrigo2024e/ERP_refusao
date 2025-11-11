package com.smartprocessrefusao.erprefusao.services.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusiness(BusinessException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
