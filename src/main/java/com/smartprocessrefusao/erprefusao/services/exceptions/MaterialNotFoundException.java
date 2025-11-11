package com.smartprocessrefusao.erprefusao.services.exceptions;

public class MaterialNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MaterialNotFoundException(String msg) {
		super(msg);
	}
}