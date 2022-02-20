package com.kaneko.helpdesk.services.exceptions;

public class DataIntegrationViolationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DataIntegrationViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegrationViolationException(String message) {
		super(message);
	}

}
