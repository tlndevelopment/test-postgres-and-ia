package com.tlndevelopment.test_postgresql_and_ai.service;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 7668466442976172922L;
	
	public ServiceException(String msg) {
		super(msg);
	}

}
