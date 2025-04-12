package com.smartprocessrefusao.erprefusao.exceptions.services;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
