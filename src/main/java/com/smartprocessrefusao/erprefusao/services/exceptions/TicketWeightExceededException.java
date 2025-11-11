package com.smartprocessrefusao.erprefusao.services.exceptions;

public class TicketWeightExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TicketWeightExceededException(String msg) {
		super(msg);
	}
}