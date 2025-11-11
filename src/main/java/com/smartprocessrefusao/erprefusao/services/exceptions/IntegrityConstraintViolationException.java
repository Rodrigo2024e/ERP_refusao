

package com.smartprocessrefusao.erprefusao.services.exceptions;

public class IntegrityConstraintViolationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	
	public IntegrityConstraintViolationException(String msg) {
		super(msg);
	}
}
